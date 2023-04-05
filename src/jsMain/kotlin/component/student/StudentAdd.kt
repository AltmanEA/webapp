package component.student

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname
import web.html.InputType

val CStudentAdd = FC<EditAddProps<Student>>("StudentAdd") { props ->
    var firstname by useState("")
    var surname by useState("")
    span {
        input {
            type = InputType.text
            value = firstname
            onChange = { firstname = it.target.value }
        }
        input {
            type = InputType.text
            value = surname
            onChange = { surname = it.target.value }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(Student(Firstname(firstname), Surname(surname)))
        }
    }
}
