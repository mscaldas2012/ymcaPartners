package com.peraton.ymca.referral.partners

import com.peraton.ymca.referral.Credentials
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory
import java.security.Principal
import java.util.*
import javax.inject.Inject

@Controller("/partners")
@Secured(SecurityRule.IS_AUTHENTICATED)
class PartnerController(val partnerService: PartnerService) {
    companion object {
        val logger = LoggerFactory.getLogger("PartnerController")
    }

    @Get("/")
    @Secured("ADMIN")
//    @Secured(SecurityRule.IS_ANONYMOUS)
    fun getAllPartners(principal: Principal): List<Partner> {
        logger.info("AUDIT::Retrieving All Partners. Caller: ${principal.name}")
        return partnerService.findAll()
    }

    @Get("/me")
    fun getMe(principal: Principal): Partner {
        logger.info("AUDIT::Retrieving info about Logged in Partner. Caller: ${principal.name}")
        return partnerService.findByCode(principal.name)
    }

    @Post("/")
    @Secured("ADMIN")
//    @Secured(SecurityRule.IS_ANONYMOUS)
    fun createNewPartner(@Body partnerVM: PartnerVM): MutableHttpResponse<Any>? {
        logger.info("AUDIT::Creating new partner")
        runCatching {
            val newPartner = partnerService.create(partnerVM)
            logger.info("Partner ${newPartner.partnerId} created successfully!")
            return ok(Credentials(newPartner.clientId, newPartner.secretKey, newPartner.partnerId))
        }.onFailure {
            logger.error(it.message)
            return HttpResponse.badRequest("Invalid data passed to create Partner with error: ${it.message}")
        }
        return null
    }

    @Get("/{partner_id}")
    // <3>
    @Secured("ADMIN")
    fun getPartner(@PathVariable(name= "partner_id") partnerId: UUID, principal: Principal): Partner? {
        logger.info("AUDIT::Retrieve Partner ${partnerId}")
        return partnerService.get(partnerId)
    }

    @Post("/{partner_id}/renewKey")
    // <3>
    @Secured("ADMIN")
    fun resetCredentials(@PathVariable(name= "partner_id") partnerId: UUID, principal: Principal): Credentials {
        logger.info("AUDIT::Resetting secret for $partnerId. Caller: ${principal.name}")
        val newKey =  partnerService.resetSecretKey(partnerId)

        val partner = partnerService.get(partnerId)
        return Credentials(partner!!.clientId, newKey, partner.partnerId)
    }


    @Put("/{partner_id}")
    @Secured("ADMIN")
    fun updatePartner(@PathVariable(name = "partner_id") partnerId: UUID, principal: Principal, @Body partner: PartnerVM): MutableHttpResponse<Any>? {
        logger.info("AUDIT::Updating Partner $partnerId")
//        if (partnerId != partner.partnerId) {
//            return HttpResponse.badRequest("Invalid request Path parameter ID and body does not match.")
//        }
        runCatching {
            return HttpResponse.ok(partnerService.update(partnerId, partner))
        }.onFailure {
            return HttpResponse.badRequest("Invalid partner data passed with error: ${it.message}")
        }
        return null
    }

    @Delete("/{partner_id}")
    @Secured("ADMIN")
    fun delete(@PathVariable(name = "partner_id") partnerId: UUID) {
        logger.info("AUDIT::Deleting partner $partnerId")
        partnerService.delete(partnerId)
    }

}