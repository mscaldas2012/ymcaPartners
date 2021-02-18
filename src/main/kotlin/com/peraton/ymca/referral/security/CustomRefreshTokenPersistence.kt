package com.peraton.ymca.referral.security;

import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.security.authentication.UserDetails
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import org.reactivestreams.Publisher
import java.lang.Boolean
import java.time.Instant
import java.util.*
import javax.inject.Singleton

@Singleton // <1>
class CustomRefreshTokenPersistence     // <2>
    (private val refreshTokenRepository: RefreshTokenRepository) : RefreshTokenPersistence {
    @EventListener // <3>
    override fun persistToken(event: RefreshTokenGeneratedEvent) {
        if (event?.refreshToken != null && event.userDetails != null && event.userDetails.username != null) {
            val payload = event.refreshToken
            refreshTokenRepository.save(UUID.randomUUID(), event.userDetails.username, payload, Boolean.FALSE, Instant.now()) // <4>
        }
    }

    override fun getUserDetails(refreshToken: String): Publisher<UserDetails> {
        return Flowable.create({ emitter: FlowableEmitter<UserDetails> ->
            refreshTokenRepository.findByRefreshToken(refreshToken).let {
                if (it != null) {
                    if (it.revoked) {
                        emitter.onError(
                            OauthErrorResponseException(
                                IssuingAnAccessTokenErrorCode.INVALID_GRANT,
                                "refresh token revoked",
                                null
                            )
                        ) // <5>
                    } else {
                        emitter.onNext(
                            UserDetails(
                                it.username,
                                ArrayList()
                            )
                        ) // <6>
                        emitter.onComplete()
                    }
                } else {
                    emitter.onError(
                        OauthErrorResponseException(
                            IssuingAnAccessTokenErrorCode.INVALID_GRANT,
                            "refresh token not found",
                            null
                        )
                    ) // <7>
                }
            }
        }, BackpressureStrategy.ERROR)
    }
}
