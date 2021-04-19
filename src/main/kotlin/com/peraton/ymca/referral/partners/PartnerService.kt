package com.peraton.ymca.referral.partners

import com.peraton.ymca.referral.ylocations.YmcaRepository
import com.peraton.ymca.referral.ylocations.swapMVVM
import org.mindrot.jbcrypt.BCrypt
import java.security.InvalidParameterException
import java.util.*
import javax.inject.Singleton

@Singleton
class PartnerService(val repo: PartnerRepository, val yrepo: YmcaRepository) {
    // Descriptive alphabet using three CharRange objects, concatenated
    val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun create(partnerVM: PartnerVM): Partner {
        val clientId = List(12) { alphabet.random() }.joinToString("")
        val newsecret = List(64) { alphabet.random() }.joinToString("")
        //Encrypt secretKey value...
        val pp = Partner(partnerVM.officialName, partnerVM.code, partnerVM.status?: "Requested", partnerVM.associatedY?.swapMVVM(), partnerVM.feedbackUrl,
                clientId, BCrypt.hashpw(newsecret, BCrypt.gensalt()),  partnerVM.contacts, partnerVM.role?: "PARTNER" )
        partnerVM.contacts?.forEach {
            it.partner = pp
            it.contactId = UUID.randomUUID()
        }
        //Check the associated Ys are valid
        partnerVM.associatedY?.forEach {
            if (yrepo.findById( it.id).isEmpty)
                throw Exception("Invalid Y. Could not locate YMCA location for  ${it.id} ")
        }
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

    fun update(partnerId: UUID, partnerVM: PartnerVM): Partner {
        val existing = repo.findById(partnerId)
        if (existing.isPresent) {
            var partner = existing.get()
            partner = partnerVM.swapMVVM(partner)
            return repo.update(partner);
        } else {
            throw Exception("No partner found for ID: $partnerId")
        }
    }

    fun delete(partnerId: UUID) {
        val partnerGone = repo.findById(partnerId)
        if (partnerGone.isPresent)
            return repo.delete(partnerGone.get())
    }


}