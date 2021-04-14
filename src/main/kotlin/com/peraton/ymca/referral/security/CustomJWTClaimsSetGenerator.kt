package com.peraton.ymca.referral.security

import io.micronaut.security.authentication.UserDetails
import com.nimbusds.jwt.JWTClaimsSet
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.security.token.jwt.generator.claims.ClaimsAudienceProvider
import io.micronaut.security.token.jwt.generator.claims.JwtIdGenerator
import io.micronaut.security.token.config.TokenConfiguration
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator
import io.micronaut.context.annotation.Replaces
import javax.inject.Singleton


@Singleton
@Replaces(bean = JWTClaimsSetGenerator::class)
class CustomJWTClaimsSetGenerator(
    tokenConfiguration: TokenConfiguration?,
    jwtIdGenerator: JwtIdGenerator?,
    claimsAudienceProvider: ClaimsAudienceProvider?,
    applicationConfiguration: ApplicationConfiguration?) :
    JWTClaimsSetGenerator(tokenConfiguration, jwtIdGenerator, claimsAudienceProvider, applicationConfiguration) {
    override fun populateWithUserDetails(builder: JWTClaimsSet.Builder, userDetails: UserDetails) {
        super.populateWithUserDetails(builder, userDetails)
        if (userDetails is PartnerUserDetails) {
            builder.claim("yLocations", userDetails.yLocations)
        }
    }
}