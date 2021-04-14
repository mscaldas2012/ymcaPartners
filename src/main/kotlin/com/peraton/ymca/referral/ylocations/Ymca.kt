package com.peraton.ymca.referral.ylocations

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Ymca(@field:NotBlank @Column(name= "name", unique= true, nullable = false) var name: String = "",
                @field:NotBlank var dagName: String = "",
                @Id var id: UUID? = UUID.randomUUID())
