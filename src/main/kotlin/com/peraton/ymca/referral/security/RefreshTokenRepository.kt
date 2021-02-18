package com.peraton.ymca.referral.security;

import edu.umd.cs.findbugs.annotations.NonNull
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.time.Instant
import java.util.*

import javax.transaction.Transactional
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Repository
interface RefreshTokenRepository: CrudRepository<RefreshTokenEntity, String> { // <2>

    @Transactional
    fun save(id: UUID, @NonNull @NotBlank username: String,
             @NonNull @NotBlank  refreshToken:String,
             @NonNull @NotNull revoked:Boolean, dateCreated: Instant): RefreshTokenEntity

    fun  findByRefreshToken(@NonNull @NotBlank  refreshToken:String ): RefreshTokenEntity?

    fun updateByUsername(@NonNull @NotBlank  username:String ,
                          @NonNull @NotNull  revoked:Boolean): Long
}
