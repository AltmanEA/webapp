package ru.altmanea.webapp.repo

import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Grade
import ru.altmanea.webapp.data.GradeInfo
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname

val studentsRepo = ListRepo<Student>()
val lessonsRepo = ListRepo<Lesson>()

fun createTestData() {
    listOf(
        "Sheldon" to "Cooper",
        "Leonard" to "Hofstadter",
        "Howard" to "Wolowitz",
        "Penny" to "Hofstadter"
    ).apply {
        map {
            studentsRepo.create(
                Student(
                    Firstname(it.first),
                    Surname(it.second)
                )
            )
        }
    }

    listOf(
        Lesson("Math"),
        Lesson("Phys"),
        Lesson("Story"),
    ).apply {
        map {
            lessonsRepo.create(it)
        }
    }

    val students = studentsRepo.read()
    val lessons = lessonsRepo.read()
    val sheldon = students.findLast { it.elem.firstname.name == "Sheldon" }
    check(sheldon != null)
    val leonard = students.findLast { it.elem.firstname.name == "Leonard" }
    check(leonard != null)
    val math = lessons.findLast { it.elem.name =="Math" }
    check(math != null)
    val newMath = Lesson(
        math.elem.name,
        arrayOf(
            GradeInfo(sheldon.id, Grade.A),
            GradeInfo(leonard.id, Grade.B)
        ))
    lessonsRepo.update(Item(newMath, math.id, math.version))
}
