package com.peraton.ymca.referral.partners

import java.util.*
import javax.inject.Singleton

@Singleton
class PartnerService(val repo: PartnerRepository) {

    fun save(partner: Partner) {
//        partner.id = UUID.randomUUID().toString()
        repo.save(partner)
    }

    fun get(id: String): Partner? {
        return repo.findById(id).orElse(null)
    }

    fun findByName(name: String): Partner {
        return repo.findByOfficialName(name)
    }

    fun findByCreds(clientID: String, secretKey: String): Partner {
        return repo.retrieveByClientIdAndSecretKey(clientID, secretKey)
    }
}