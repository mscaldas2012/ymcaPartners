package com.peraton.ymca.referral.ylocations

import java.util.*
import javax.inject.Singleton

@Singleton
class YmcaService(val repo: YmcaRepository) {
    fun create(ymca: Ymca): Ymca {
        //Maintain uniqueness of Name:
        val exists = repo.findByName(ymca.name)
        if (exists != null)
            throw Exception("Ymca Name ${ymca.name} already registered")
        ymca.id = UUID.randomUUID()
        return repo.save(ymca)
    }

    fun update(ymca: Ymca): Ymca {
        val exists = repo.findById(ymca.id!!)
        if (exists.isEmpty) {
            throw Exception("Ymca ${ymca.id} not registered")
        }
        val stored = exists.get()
        stored.name = ymca.name
        stored.dagName = ymca.dagName
        return repo.update(stored)
    }

    fun getOne(id: UUID): Ymca {
         return repo.findById(id).get()
    }

    fun findAll(): List<Ymca> {
        return repo.findAll().toList()
    }

    fun delete(ymcaId: UUID) {
        val ymca = repo.findById(ymcaId)
        if (ymca.isPresent)
            repo.delete(ymca.get())
    }

}