package com.peraton.ymca.referral

import com.peraton.ymca.referral.partners.Contact
import com.peraton.ymca.referral.partners.Partner
import com.peraton.ymca.referral.partners.PartnerRepository
import io.micronaut.context.annotation.Requires
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener
import org.mindrot.jbcrypt.BCrypt
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@Requires(property = "phase", value = "DEV")
class DataLoaderForDev(val repo: PartnerRepository) {
    companion object {
        val logger = LoggerFactory.getLogger(DataLoaderForDev::class.java)
    }

    @EventListener
    fun loadConferenceData(event: ServiceReadyEvent) {
        logger.info("Loading data at startup")
        val clientId = "unittest"
        val secret = BCrypt.hashpw("unittest-unittest-unittest-unittest", BCrypt.gensalt())
//        val contact = Contact("Marcelo Caldas", "mcq1@cdc.gov", "6785551234")
        val superAdmin = Partner("Super Admin", "SUPER_ADMIN", "active", null,  "N/A", clientId, secret, "marcelo", "ADMIN", )
//        contact.partner = superAdmin
        repo.save(superAdmin)
        logger.info("Super Admin created for Unit TEST")
    }
}