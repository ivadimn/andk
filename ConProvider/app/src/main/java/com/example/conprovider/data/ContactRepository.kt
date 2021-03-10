package com.example.conprovider.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import java.util.regex.Pattern

class ContactRepository {

    private val phonePattern = Pattern.compile("^\\+?[]0-9]{3}-?[0-9]{6,12}\$")

    suspend fun saveContact(name : String, phone : String) = withContext(Dispatchers.IO) {
        if (!phonePattern.matcher(phone).matches() || name.isBlank()) {
            throw IncorrectInformationException()
        }
    }

    suspend fun getAllContacts() : List<Contact> = withContext(Dispatchers.IO) {
        listOf(
            Contact(1, "Вася Пупкин", listOf("+79002578990", "+79207864528")),
            Contact(2, "Маша Звёздная", listOf("+79112469532")),
            Contact(3, "Витя Глушаков", listOf("+79032578990", "+79067113206")),
            Contact(4, "Петя Тряпочкин", listOf("+79268075623", "+79207864528", "+79049231940")),
            Contact(5, "Лёша Пешкой", listOf("+79652578990", "+79207864528"))
        )
    }



}