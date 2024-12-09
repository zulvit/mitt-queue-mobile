package ru.zulvit.mittqueuemobile.models

data class Student(
    val firstName: String,
    val lastName: String
)

class Queue(
    val name: String,
    val students: MutableList<Student> = mutableListOf()
) {
    fun add(student: Student) {
        students.add(student)
    }

    fun remove(student: Student) {
        students.remove(student)
    }
}
