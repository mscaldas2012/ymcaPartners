package com.peraton.ymca.referral

import org.mindrot.jbcrypt.BCrypt
import java.security.InvalidParameterException
import java.util.*
import javax.inject.Singleton

@Singleton
class PartnerService(val repo: PartnerRepository) {
    // Descriptive alphabet using three CharRange objects, concatenated
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun create(partner: NewPartner): Partner {
        val clientId = List(12) { alphabet.random() }.joinToString("")
        val newsecret = List(64) { alphabet.random() }.joinToString("")
        //Encrypt secretKey value...
        val pp = Partner(UUID.randomUUID(), partner.officialName, partner.code, partner.status, partner.associatedY, partner.feedbackURL,
                clientId, BCrypt.hashpw(newsecret, BCrypt.gensalt()), partner.contact, partner.role )
        val savedPartner = repo.save(pp)
        //This One Time, the secret is returned (without hashing)
        savedPartner.secretKey = newsecret
        return savedPartner
    }

    fun resetSecretKey(partnerId: UUID): String {
        val partnerO: Optional<Partner> = repo.findById(partnerId)
        if (partnerO.isPresent) {
            val newsecret = List(64) { alphabet.random() }.joinToString("")
            val partner = partnerO.get()
            partner.secretKey = BCrypt.hashpw(newsecret, BCrypt.gensalt())
            repo.update(partner)
            return newsecret
        }
        throw InvalidParameterException("Unable to find partner for id $partnerId")
    }

//    fun save(partner: Partner): Partner {
//        partner.id = UUID.randomUUID()
//        partner.clientId =   List(12) { alphabet.random() }.joinToString("")
//        //Encrypt secretKey value...
//        val newsecret = List(64) { alphabet.random() }.joinToString("")
//        println("newsecret = ${newsecret}")
//        partner.secretKey = BCrypt.hashpw(newsecret, BCrypt.gensalt())
//        val newPartner =  repo.save(partner)
//        //This One Time, the secret is returned before hashing...
//        newPartner.secretKey = newsecret
//        return newPartner
//    }



    fun get(id: UUID): Partner? {
        return repo.findById(id).orElse(null)
    }

    fun findAll(): List<Partner> {
        return repo.findAll().toList()
    }

    fun findByCode(code: String): Partner {
        return repo.findByCode(code)
    }


}