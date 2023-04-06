package ru.altmanea.webapp.data

import arrow.core.Either
import arrow.core.EitherNel
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.zipOrAccumulate
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.common.ItemId
import ru.altmanea.webapp.type.Firstname
import ru.altmanea.webapp.type.Surname
import ru.altmanea.webapp.type.TypeError

@Serializable
class Student private constructor(
    val firstname: Firstname,
    val surname: Surname
) {
    fun fullname() =
        "$firstname $surname"

    companion object {
        operator fun invoke(
            firstname: Either<TypeError, Firstname>,
            surname: Either<TypeError, Surname>
        ): EitherNel<TypeError, Student> = either {
            zipOrAccumulate(
                { firstname.bind() },
                { surname.bind() },
                {
                    if (firstname.getOrNull()?.name == surname.getOrNull()?.name)
                        raise(TypeError.WrongName("Firstname = Surname"))
                }
            ) { f, s, _ -> Student(f, s) }
        }
    }
}

typealias StudentId = ItemId

val Student.json
    get() = Json.encodeToString(this)

