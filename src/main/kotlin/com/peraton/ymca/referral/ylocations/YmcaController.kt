package com.peraton.ymca.referral.ylocations

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/ymca")
@Secured(SecurityRule.IS_AUTHENTICATED)
class YmcaController(val service: YmcaService) {
    companion object {
        val logger = LoggerFactory.getLogger("YmcaController")
    }

    @Get("/")
    fun getList(): List<Ymca> {
        return service.findAll()
    }

    @Get("/{ymca_id}")
    fun getOne(@PathVariable(name = "ymca_id") ymcaId: UUID): Ymca {
        return service.getOne(ymcaId)
    }

    @Post("/")
    @Secured("ADMIN")
    fun create(@Body ymca: Ymca): Ymca {
        return service.create(ymca)
    }

    @Put("/{ymca_id}")
    @Secured("ADMIN")
    fun update(@PathVariable(name = "ymca_id") ymcaId: UUID, @Body ymca: Ymca) : HttpResponse<Any>? {
        if (ymcaId != ymca.id) {
            return HttpResponse.badRequest("Invalid request Path parameter ID and body does not match.")
        }
        runCatching {
            return HttpResponse.ok(service.update(ymca))
        }.onFailure {
            logger.error(it.message)
            return HttpResponse.badRequest("Invalid data passed to update YMCA Location with error: ${it.message}")
        }
        return null
    }

    @Delete("/{ymca_id}")
    @Secured("ADMIN")
    fun delete(@PathVariable(name = "ymca_id") ymcaId: UUID) {
        return service.delete(ymcaId)
    }
}