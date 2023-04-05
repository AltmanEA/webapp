package ru.altmanea.webapp.type

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class Surname(val name: String) {
    override fun toString() = name
}