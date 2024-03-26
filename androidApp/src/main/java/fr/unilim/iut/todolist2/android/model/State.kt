package fr.unilim.iut.todolist2.android.model

enum class State(val id:Int) {

    /**
     * La tâche est à faire
     */
    PENDING(0),

    /**
     * La tâche a été réalisée
     */
    DONE(1),

    /**
     * La tâche est en retard
     */
    OVERDUE(2)
}