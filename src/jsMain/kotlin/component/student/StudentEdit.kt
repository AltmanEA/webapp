package component.student

import arrow.core.Either
import arrow.core.NonEmptyList
import component.template.EditItemProps
import react.FC
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.span
import react.useRef
import react.useState
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname
import ru.altmanea.webapp.type.TypeError
import web.html.HTMLLabelElement
import web.html.InputType

val CStudentEdit = FC<EditItemProps<Student>>("StudentEdit") { props ->
    var firstname by useState(props.item.elem.firstname.name)
    var surname by useState(props.item.elem.surname.name)
    val labelRef = useRef<HTMLLabelElement>()
    span {
        input {
            type = InputType.text
            value = firstname
            onChange = { firstname = it.target.value }
        }
        input {
            type = InputType.text
            value = surname
            onChange = {surname = it.target.value }
        }
        label {
            ref = labelRef
        }
    }
    button {
        +"✓"
        onClick = {
            val student: Either<NonEmptyList<TypeError>, Student> = Student(Firstname(firstname), Surname(surname))
            labelRef.current?.textContent = when (student) {
                is Either.Left -> student.value.joinToString { it.error }
                is Either.Right -> {
                    props.saveElement(student.value)
                    ""
                }
            }
        }
    }
}
