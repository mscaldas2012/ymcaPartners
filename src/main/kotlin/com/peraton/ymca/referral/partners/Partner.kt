package com.peraton.ymca.referral.partners

import com.fasterxml.jackson.annotation.JsonIgnore
import com.peraton.ymca.referral.ylocations.Ymca
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "partners")
data class Partner(var officialName: String,
                   var code: String,
                   var status: String = "Requested",
                   @ManyToMany(fetch = FetchType.EAGER)
                   var associatedY: List<Ymca>?,
                   var feedbackUrl: String?,
                   var clientId: String,
                   @JsonIgnore
                   var secretKey: String,
//                   @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
//                   var contact: List<Contact>?,
                   var contact: String?,
                   var role: String,
                   @Id var partnerId: UUID = UUID.randomUUID(),
) {
    constructor() : this("", "", "Requested", null, "N/A", "N/A", "N/A", "N/A", "User")
}

data class NewPartner(val officialName: String,
                      val code: String,
                      val status: String,
                      var associatedY: List<Ymca>?,
                      var feedbackURL: String?,
                      var contact: String?,
                      val role: String)
