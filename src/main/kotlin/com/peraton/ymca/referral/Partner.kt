package com.peraton.ymca.referral

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "partners")
data class Partner(@Id var id: UUID,
                   var officialName: String,
                   var code: String,
                   var status: String,
                   var associatedY: String?,
                   var feedbackUrl: String?,
                   var clientId: String,
                   @JsonIgnore
                   var secretKey: String,
                   var contact: String?,
                   var role: String) {
    constructor() : this(UUID.randomUUID(),"", "", "Requested", "N/A", "N/A", "N/a", "N/A", "N/A", "User")
}

data class NewPartner(val officialName: String,
                      val code: String,
                      val status: String,
                      var associatedY: String?,
                      var feedbackURL: String?,
                      var contact: String?,
                      val role: String)
//
//data class Contact(@Id val id: String,
//                    var name: String,
//                    var email: String,
//                    var phone: String,
//                    var contactType: String)