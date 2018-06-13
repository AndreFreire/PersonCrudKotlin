package com.example.kotlincrud.service

import com.example.kotlincrud.dto.PersonDTO
import com.example.kotlincrud.model.Person
import com.example.kotlincrud.repository.PersonRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PersonService (private val personRepository: PersonRepository){
    fun getPerson(id : Int): Optional<PersonDTO> {

        val person : Optional<Person> = personRepository.findById(id)

        return person.map { p ->  PersonDTO(
                id = p.id,
                name = p.name,
                lastName = p.lastName,
                nickname = p.nickname
        )}
    }

    fun savePerson(personDTO: PersonDTO): PersonDTO {
        val person = Person(
                name = personDTO.name,
                lastName = personDTO.lastName,
                nickname = personDTO.nickname
        )
        return personRepository.save(person).toDTO()
    }

    fun deletePerson(id: Int) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id)
        }
    }

    fun updatePerson(id: Int, personDTO: PersonDTO): Optional<PersonDTO> {
        var result: PersonDTO? = null
        if (personRepository.existsById(id)) {

            val person = Person(
                    id = id,
                    name = personDTO.name,
                    lastName = personDTO.lastName,
                    nickname = personDTO.nickname
            )
            result = personRepository.save(person).toDTO()
        }
        return Optional.ofNullable(result)

    }

}