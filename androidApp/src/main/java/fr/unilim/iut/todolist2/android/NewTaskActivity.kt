package fr.unilim.iut.todolist2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.unilim.iut.todolist2.android.db.DBHelper
import fr.unilim.iut.todolist2.android.db.TaskDAO
import fr.unilim.iut.todolist2.android.model.State
import fr.unilim.iut.todolist2.android.model.Task
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskDAO = TaskDAO(this)

        setContent {
            // UI de votre application ici
            TaskCreationScreen { name, limit, state, repositoryId ->
                val newTask = Task(
                    id = 0, // SQLite génère l'ID
                    name = name,
                    limit = limit,
                    state = state,
                    repository = repositoryId
                )
                taskDAO.put(newTask)
                finish() // Ferme l'Activity après la création
            }
        }
    }
}

@Composable
fun TaskCreationScreen(onTaskCreated: (String, LocalDateTime?, State, Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var dateString by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<LocalDateTime?>(null) }
    var state by remember { mutableStateOf(State.PENDING) }
    var repositoryId by remember { mutableIntStateOf(1) } // Simulez le choix d'un dépôt

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom de la tâche") }
        )

        OutlinedTextField(
            value = dateString,
            onValueChange = {
                dateString = it
                // Vous devez ajouter la logique de validation et de conversion ici
                selectedDate = LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
            },
            label = { Text("Date limite (AAAA-MM-JJ)") }
        )

        // Exemple de sélection de l'état avec des boutons (ceci pourrait être amélioré avec un menu déroulant ou autre)
        Text("État de la tâche")
        State.values().forEach { currentState ->
            Button(
                onClick = { state = currentState }
            ) {
                Text(currentState.name)
            }
        }

        Button(
            onClick = { onTaskCreated(name, selectedDate, state, repositoryId) },
        ) {
            Text("Créer Tâche")
        }
    }
}