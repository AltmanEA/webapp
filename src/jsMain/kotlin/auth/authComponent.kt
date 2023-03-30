package auth

import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.dialog
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.access.User
import web.html.InputType

typealias Username = String
typealias Password = String

external interface AuthInProps : Props {
    var signIn: (Username, Password) -> Unit
}

external interface AuthOutProps : Props {
    var user: User
    var signOff: () -> Unit
}

val CAuthIn = FC<AuthInProps>("Auth") { props ->
    var name by useState("")
    var pass by useState("")
    span {
        +"Name: "
        input {
            type = InputType.text
            value = name
            onChange = { name = it.target.value }
        }
    }
    span {
        +"Pass: "
        input {
            type = InputType.text
            value = pass
            onChange = { pass = it.target.value }
        }
    }
    button {
        +"SignIn"
        onClick = {
            props.signIn(name, pass)
        }
    }
}

val CAuthOut = FC<AuthOutProps>("Auth") { props ->
    div {
        +"${props.user.username}"
        button {
            +"SignOut"
            onClick = {
                props.signOff()
            }
        }
    }
}