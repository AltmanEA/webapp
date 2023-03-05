package component.student

import component.template.ElementInListProps
import react.FC
import react.dom.html.ReactHTML.span
import ru.altmanea.webapp.data.Student


val CStudentInList = FC<ElementInListProps<Student>>("StudentInList") { props ->
    span {
        +props.element.fullname()
    }
}