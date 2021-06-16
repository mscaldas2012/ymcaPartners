package com.peraton.ymca.referral.security


import io.micronaut.data.annotation.AutoPopulated
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.GeneratedValue
import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class RefreshTokenEntity(@Id @AutoPopulated var id: UUID,
                              var username: String,
                              var refreshToken: String,
                              var revoked:Boolean,
                              @DateCreated var dateCreated: Instant
)
 {
    constructor() : this(UUID.randomUUID(), "", "", true, Instant.now())
}