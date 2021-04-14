package com.peraton.ymca.referral.partners

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface PartnerRepository: CrudRepository<Partner, UUID> {
    @Executable
    fun findByOfficialName(officialName: String): Partner
    @Executable
    fun retrieveByClientId(clientId: String): Partner?
    @Executable
    fun findByCode(code: String): Partner
}

