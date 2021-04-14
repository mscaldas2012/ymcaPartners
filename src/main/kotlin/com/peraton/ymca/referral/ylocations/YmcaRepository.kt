package com.peraton.ymca.referral.ylocations

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface YmcaRepository: CrudRepository<Ymca, UUID> {

    @Executable
    fun findByName(name: String): Ymca?
}