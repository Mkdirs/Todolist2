package fr.unilim.iut.todolist2.android.model

enum class State {

    /**
     * La tâche est à faire
     */
    PENDING,

    /**
     * La tâche a été réalisée
     */
    DONE,

    /**
     * La tâche est en retard
     */
    OVERDUE
}