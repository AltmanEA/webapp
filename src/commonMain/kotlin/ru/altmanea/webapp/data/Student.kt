package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.common.ItemId
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname

@Serializable
class Student(
    val firstname: Firstname,
    val surname: Surname
){
    fun fullname() =
        "$firstname $surname"

}

typealias StudentId = ItemId

val Student.json
    get() = Json.encodeToString(this)

