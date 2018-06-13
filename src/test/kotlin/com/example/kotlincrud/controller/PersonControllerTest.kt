package com.example.kotlincrud.controller

import com.example.kotlincrud.dto.PersonDTO
import com.example.kotlincrud.model.Person
import com.example.kotlincrud.repository.PersonRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    lateinit var repository: PersonRepository

    @Autowired
    protected lateinit var mapper: ObjectMapper

    @Autowired
    protected lateinit var mvc: MockMvc


    fun deleteAll() {
        repository.deleteAll()
    }

    fun createPerson() : Person {
        val person = Person(
                name = "name",
                lastName = "lastName",
                nickname = "nickname"
        )
        return repository.save(person)
    }

    @Before
    fun setUp() {

    }

    @Test
    fun getPersonSuccessTest() {
        val person = createPerson()

        mvc.perform(get("/person/{id}", person.id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", `is`(person.id)))
                .andExpect(jsonPath("$.name", `is`(person.name)))
                .andExpect(jsonPath("$.lastName", `is`(person.lastName)))
                .andExpect(jsonPath("$.nickname", `is`(person.nickname)))

        deleteAll()
    }

    @Test
    fun getPersonNotFoundTest() {
        mvc.perform(get("/person/{id}", 1))
                .andExpect(status().isNotFound)

    }

    @Test
    fun deletePersonSuccessTest() {
        val person = createPerson()

        mvc.perform(delete("/person/{id}", person.id))
                .andExpect(status().isOk())

        assertFalse(repository.existsById(person.id))

        deleteAll()
    }


    @Test
    fun deletePersonNotFoundTest() {
        deleteAll()
        mvc.perform(delete("/person/{id}", 9999))
                .andExpect(status().isOk())

    }

    @Test
    fun updatePersonSuccessTest() {
        val person = createPerson()

        val personDTOUpdated = PersonDTO(
                id = person.id,
                name = "name Updated",
                lastName = "lastNameUpdated",
                nickname = "nicknameUpdated"
        )

        mvc.perform(put("/person/{id}", person.id)
                .content(mapper.writeValueAsString(personDTOUpdated))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", `is`(person.id)))
                .andExpect(jsonPath("$.name", `is`(personDTOUpdated.name)))
                .andExpect(jsonPath("$.lastName", `is`(personDTOUpdated.lastName)))
                .andExpect(jsonPath("$.nickname", `is`(personDTOUpdated.nickname)))

        val updatedPerson = repository.findById(person.id).get()

        assertEquals(personDTOUpdated.id, updatedPerson.id)
        assertEquals(personDTOUpdated.name, updatedPerson.name)
        assertEquals(personDTOUpdated.lastName, updatedPerson.lastName)
        assertEquals(personDTOUpdated.nickname, updatedPerson.nickname)

        deleteAll()
    }

    @Test
    fun savePersonSuccessTest() {

        val personDTOUpdated = PersonDTO(
                id = 1,
                name = "name",
                lastName = "lastName",
                nickname = "nickname"
        )

        mvc.perform(post("/person")
                .content(mapper.writeValueAsString(personDTOUpdated))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", `is`(personDTOUpdated.name)))
                .andExpect(jsonPath("$.lastName", `is`(personDTOUpdated.lastName)))
                .andExpect(jsonPath("$.nickname", `is`(personDTOUpdated.nickname)))

        deleteAll()
    }

}