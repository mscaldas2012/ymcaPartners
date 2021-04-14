package com.peraton.ymca.referral.partners

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface ContactRepository: CrudRepository<Contact, UUID> {
    @Executable
    fun findByName(name: String): Contact?

}