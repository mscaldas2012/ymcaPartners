package com.peraton.ymca.referral.partners

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

//@Repository
interface PartnerRepository: CrudRepository<Partner, String> {
    @Executable
    fun findByOfficialName(officialName: String): Partner
//    @Executable
    fun retrieveByClientIdAndSecretKey(clientId: String, secretKey: String): Partner
}

