package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.repo.studentsRepo

fun Route.studentRoutes() {
    route(Config.studentsPath) {
        repoRoutes(studentsRepo)
        get("ByStartName/{startName}") {
            val startName =
                call.parameters["startName"] ?: return@get call.respondText(
                    "Missing or malformed startName",
                    status = HttpStatusCode.BadRequest
                )
            val students = studentsRepo.read().filter {
                it.elem.firstname.startsWith(startName)
            }
            if (students.isEmpty()) {
                call.respondText("No students found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(students)
            }
        }
    }
}