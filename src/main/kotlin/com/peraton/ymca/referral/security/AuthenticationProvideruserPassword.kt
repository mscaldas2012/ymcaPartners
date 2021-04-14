package com.peraton.ymca.referral.security

import com.peraton.ymca.referral.partners.PartnerRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.mindrot.jbcrypt.BCrypt
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationProviderUserPassword : AuthenticationProvider {
    // <2>
    private val logger = LoggerFactory.getLogger(AuthenticationProviderUserPassword::class.java)

    @Inject
    private lateinit var partnerRepository: PartnerRepository

    override fun authenticate(httpRequest: HttpRequest<*>?, authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse?> {
        return Flowable.create({ emitter: FlowableEmitter<AuthenticationResponse?> ->
            logger.info("checking credentails... " + authenticationRequest.identity)
            logger.info("checking secret... " + authenticationRequest.secret)
            val partner = partnerRepository.retrieveByClientId(authenticationRequest.identity.toString())
            if (partner != null && BCrypt.checkpw(authenticationRequest.secret.toString(), partner.secretKey)) {
                val user = PartnerUserDetails(partner.code, listOf(partner.role),
//                    partner.associatedY?.map{ it.dagName }?.toList()
                    partner.associatedY
                )
                //user.
                emitter.onNext(user)
                emitter.onComplete()
                logger.info("Request Successfully authenticated.")
//            }
//            if (authenticationRequest.identity == "sherlock" && authenticationRequest.secret == "password") {
//                val roles = ArrayList<String>()
//                roles.add("ADMIN")
//                roles.add("USER")
//                emitter.onNext(
//                    UserDetails(
//                        authenticationRequest.identity as String,
//                        roles
//                    )
//                )
//                emitter.onComplete()
//                logger.info("emitter is good!")
            } else {
                emitter.onError(AuthenticationException(AuthenticationFailed()))
                logger.warn("emitter is BAD, BAD, BAD!!!")
            }
        }, BackpressureStrategy.ERROR)
    }
}