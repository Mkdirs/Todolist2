package fr.unilim.iut.todolist2.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.unilim.iut.todolist2.Greeting
import fr.unilim.iut.todolist2.android.db.RepositoryDAO
import fr.unilim.iut.todolist2.android.db.TaskDAO
import fr.unilim.iut.todolist2.android.model.Repository
import fr.unilim.iut.todolist2.android.model.State
import fr.unilim.iut.todolist2.android.model.Task
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.FRANCE)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val taskDao = TaskDAO(this)
        val repositoryDAO = RepositoryDAO(this)
        val testRepo = Repository(0, "Test", emptyList())
        repositoryDAO.put(testRepo)

        taskDao.clear()
        for(i in 0..5){
            taskDao.put(Task(i, "Toto${i+1}", null, State.PENDING, testRepo.id))
        }

        // Cette tâche ne doit pas être affichée
        taskDao.delete(2)

        taskDao.update(4, Task(10, "Modifié", LocalDateTime.now(), State.DONE, testRepo.id))


        setContent {
            //GreetingView(Greeting().greet())
            test_repo(repository = repositoryDAO.get(testRepo.id)!!)
        }
    }
}

@Composable
fun test_repo(repository: Repository){
    Column{
        Text(text = "Repository: ${repository.name}", fontSize = 30.sp)
        for(task in repository.tasks){
            Text(text = "id: ${task.id}, nom: ${task.name}, state: ${task.state}, due: ${task.limit?.let{FORMATTER.format(it)}}")
        }
    }

}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

