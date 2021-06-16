package com.peraton.ymca.referral.security

import com.peraton.ymca.referral.ylocations.Ymca
import io.micronaut.security.authentication.UserDetails

class PartnerUserDetails( username: String,  roles: Collection<String>, val yLocations: Collection<Ymca>? ): UserDetails(username, roles) {

}