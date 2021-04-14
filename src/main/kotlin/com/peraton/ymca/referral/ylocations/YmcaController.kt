package com.peraton.ymca.referral.ylocations

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.util.*

@Controller("/ymca")
@Secured(SecurityRule.IS_AUTHENTICATED)
class YmcaController(val service: YmcaService) {

    @Get("/")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun getList(): List<Ymca> {
        return service.findAll()
    }

    @Get("/{ymca_id}")
    @Secured(SecurityRule.IS_ANONYMOUS)
    fun getOne(@PathVariable(name = "ymca_id") ymcaId: UUID): Ymca {
        return service.getOne(ymcaId)
    }

    @Post("/")
    @Secured("ADMIN")
    fun create(@Body ymca: Ymca): Ymca {
        return service.save(ymca)
    }

    @Put("/{ymca_id}")
    @Secured("ADMIN")
    fun update(@PathVariable(name = "ymca_id") ymcaId: UUID, @Body ymca: Ymca) : HttpResponse<*> {
        if (ymcaId != ymca.id) {
            return HttpResponse.badRequest("Invalid request Path parameter ID and body does not match.")
        }
        return HttpResponse.ok(service.save(ymca))
    }

    @Delete("/{ymca_id}")
    @Secured("ADMIN")
    fun delete(@PathVariable(name = "ymca_id") ymcaId: UUID) {
        return service.delete(ymcaId)
    }
}