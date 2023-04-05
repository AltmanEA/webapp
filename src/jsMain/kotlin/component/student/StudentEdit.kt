package component.student

import component.template.EditItemProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.data.Student
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname
import web.html.InputType

val CStudentEdit = FC<EditItemProps<Student>>("StudentEdit") { props ->
    var firstname by useState(props.item.elem.firstname)
    var surname by useState(props.item.elem.surname)
    span {
        input {
            type = InputType.text
            value = firstname
            onChange = { firstname = Firstname(it.target.value) }
        }
        input {
            type = InputType.text
            value = surname
            onChange = {surname = Surname(it.target.value) }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(Student(firstname, surname))
        }
    }
}
