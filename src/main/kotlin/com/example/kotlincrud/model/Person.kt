package com.example.kotlincrud.model

import com.example.kotlincrud.dto.PersonDTO
import javax.persistence.*

@Entity
class Person(

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", nullable = false)
        val id : Int = 0,

        @Column(name = "name", nullable = true)
        val name: String = "",

        @Column(name = "last_name", nullable = true)
        val lastName: String = "",

        @Column(name = "nickname", nullable = true)
        val nickname: String = ""

) {
    fun toDTO(): PersonDTO {
        return PersonDTO(
                id = this.id,
                name = this.name,
                lastName = this.lastName,
                nickname = this.nickname
        )
    }
}