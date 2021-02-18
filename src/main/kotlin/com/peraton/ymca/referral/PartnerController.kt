package com.peraton.ymca.referral

import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory
import java.security.Principal
import java.util.*
import javax.inject.Inject

@Controller("/partners")
@Secured(SecurityRule.IS_AUTHENTICATED)
class PartnerController {
    companion object {
        val logger = LoggerFactory.getLogger("PartnerController")
    }
    @Inject
    lateinit var partnerService: PartnerService

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
//    @Secured("ADMIN")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun createNewPartner(@Body partner: NewPartner): Credentials {
        logger.info("AUDIT::Creating new partner")
        val newPartner =  partnerService.create(partner)
        return Credentials(newPartner.clientId, newPartner.secretKey)
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
        return Credentials(partner!!.clientId, newKey)
    }

}