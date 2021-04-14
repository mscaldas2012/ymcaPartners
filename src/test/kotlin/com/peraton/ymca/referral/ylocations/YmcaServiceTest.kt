package com.peraton.ymca.referral.ylocations

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject
import javax.persistence.PersistenceException
import javax.validation.ConstraintViolationException

@MicronautTest
internal class YmcaServiceTest {

    @Inject
    lateinit var service: YmcaService
    @Test
    fun save() {
        val ymca = Ymca( "Unit Test1",  "CHI_Y")
        val saved = service.save(ymca)
        val read =  service.getOne(saved.id)
        println("read: $read")

        val emptyY = Ymca() // SHOULD FAIL...
        try {
            service.save(emptyY)
            assert(false)
        } catch (e: ConstraintViolationException) {
            println("Empty Values Exception appropriately thrown...")
        }
        //Duplicate
        val ymcaDupe = Ymca( "Unit Test1", "CHI_X")
        try {
            service.save(ymcaDupe)
            assert(false)
        } catch (e: Exception) {
            println("Duplicate Exception properly thrown.")
        }

        val allYs = service.findAll()
        allYs.forEach { println("i:  $it")}


    }

    @Test
    fun getOne() {
    }

    @Test
    fun findAll() {
    }

    @Test
    fun delete() {
    }
}