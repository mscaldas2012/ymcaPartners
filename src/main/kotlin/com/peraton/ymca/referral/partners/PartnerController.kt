package com.peraton.ymca.referral.partners

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/partners")
class PartnerController {
    @Inject
    lateinit var partnerService: PartnerService

    @Get("/")
    fun getAllPartners(): List<String> {
        return listOf("Parkview", "UMLS")
    }

    @Post("/")
    fun createNewPartner(@Body partner: Partner) {
        return partnerService.save(partner)
    }

    @Get("/{partner_id}")
    fun getPartner(@PathVariable(name= "partner_id") partnerId: String): Partner? {
        return partnerService.get(partnerId)
    }
}