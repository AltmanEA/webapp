package component.lesson

import component.template.EditItemProps
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Grade
import ru.altmanea.webapp.data.GradeInfo
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Student
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import web.html.InputType
import kotlin.js.json

val CLessonEditContainer = FC<EditItemProps<Lesson>>("LessonEditContainer") { props ->
    val sk = props.item.elem.students.joinToString(separator = "") { "s" }
    val myQueryKey = arrayOf("LessonEditContainer", sk).unsafeCast<QueryKey>()
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = myQueryKey,
        queryFn = {
            fetchText(
                "${Config.studentsPath}byId",
                jso {
                    method = "POST"
                    headers = json("Content-Type" to "application/json")
                    body = Json.encodeToString(props.item.elem.students.map { it.studentId })
                }
            )
        }
    )
    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val studentItems =
            Json.decodeFromString<Array<Item<Student>>>(query.data ?: "")
                .associateBy { it.id }
        val studentGrades = props.item.elem.students.mapNotNull {pair ->
            studentItems[pair.studentId]?.let {
                GradeInfoFull(it, pair.grade)
            }
        }.toTypedArray()
        CLessonEdit {
            item = props.item
            students = studentGrades
            saveElement = props.saveElement
        }
    }
}

class GradeInfoFull(
    val itemStudent: Item<Student>,
    val grade: Grade?
){
    fun newGrade(grade: Grade?) =
        GradeInfoFull(itemStudent, grade)
}

external interface LessonEditProps : Props {
    var item: Item<Lesson>
    var students: Array<GradeInfoFull>
    var saveElement: (Lesson) -> Unit
}

val CLessonEdit = FC<LessonEditProps>("LessonEdit") { props ->
    var name by useState(props.item.elem.name)
    div {
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
        button {
            +"Change Name"
            onClick = {
                props.saveElement(Lesson(name, props.item.elem.students))
            }
        }
    }
    CAddStudentToLesson {
        lesson = props.item
    }
    CGradeEdit {
        students = props.students
        changeStudents = {
            props.saveElement(
                Lesson(props.item.elem.name, it.map {
                    GradeInfo(it.itemStudent.id, it.grade)
                }.toTypedArray())
            )
        }
    }
}

