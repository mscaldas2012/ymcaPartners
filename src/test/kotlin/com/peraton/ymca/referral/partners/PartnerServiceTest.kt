package com.peraton.ymca.referral.partners

import com.peraton.ymca.referral.ylocations.Ymca
import com.peraton.ymca.referral.ylocations.YmcaService
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject

@MicronautTest
internal class PartnerServiceTest {

    @Inject
    lateinit var partnerSrv: PartnerService
    @Inject
    lateinit var ymcaSrv: YmcaService

    @Test
    fun create() {
        ymcaSrv.save(Ymca( "Unit Test-1",  "CHI_Y"))
        ymcaSrv.save(Ymca( "Unit Test-2",  "MI_Y"))

        val ymcas = ymcaSrv.findAll()

        val partner = NewPartner("Unit-Test-1", "PART-1", "Active", ymcas, "N/A", "N/A", "PARTNER")
        partnerSrv.create(partner)
        val saved = partnerSrv.findByCode("PART-1")
        println("Saved: $saved")
        val read = partnerSrv.get(saved.id)
        println("Read: $read")
    }
}