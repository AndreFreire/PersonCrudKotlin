package com.example.kotlincrud.controller

import com.example.kotlincrud.dto.PersonDTO
import com.example.kotlincrud.service.PersonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController (val personService : PersonService){

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id : Int) : ResponseEntity<PersonDTO> {
        return personService.getPerson(id).map { ResponseEntity.ok().body(it)}
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun savePerson(@RequestBody person : PersonDTO) :  PersonDTO = personService.savePerson(person)

    @DeleteMapping("/{id}")
    fun deletePerson(@PathVariable id : Int) = personService.deletePerson(id)

    @PutMapping("/{id}")
    fun udpatePerson(@RequestBody person : PersonDTO, @PathVariable id : Int) : ResponseEntity<PersonDTO> {
        return personService.updatePerson(id, person).map { ResponseEntity.ok().body(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @GetMapping
    fun listPerson(): ResponseEntity<List<PersonDTO>> {
        return personService.listPerson().map { ResponseEntity.ok().body(it) }
                .orElse(ResponseEntity.notFound().build())
    }
}