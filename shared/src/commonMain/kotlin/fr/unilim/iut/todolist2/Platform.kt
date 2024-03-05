package fr.unilim.iut.todolist2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform