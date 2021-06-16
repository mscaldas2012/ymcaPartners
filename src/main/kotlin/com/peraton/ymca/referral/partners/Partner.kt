package com.peraton.ymca.referral.partners

import com.fasterxml.jackson.annotation.JsonIgnore
import com.peraton.ymca.referral.ylocations.Ymca
import com.peraton.ymca.referral.ylocations.YmcaVM
import com.peraton.ymca.referral.ylocations.swapMVVM
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "partners")
class Partner(var officialName: String,
           var code: String,
           var status: String = "Requested",
           @ManyToMany(fetch = FetchType.EAGER)
           var associatedY: List<Ymca>?,
           var feedbackUrl: String,
           var clientId: String,
           @JsonIgnore
           var secretKey: String,
           @OneToMany( cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
           @Fetch(value = FetchMode.SUBSELECT)
           var contacts: List<Contact>?,
           var role: String,
           @Id var partnerId: UUID = UUID.randomUUID(), ) {
    constructor() : this("", "", "Requested", null, "N/A", "N/A", "N/A", null,  "USER")
}

data class PartnerVM(val officialName: String,
                     val code: String,
                     val status: String?,
                     val associatedY: List<YmcaVM>?,
                     val feedbackUrl: String,
                     val contacts: List<Contact>?,
                     val role: String?)

fun PartnerVM.swapMVVM(partner: Partner): Partner {
    partner.officialName = this.officialName
    partner.code = this.code
    partner.status = this.status ?: partner.status
    partner.associatedY = this.associatedY?.swapMVVM()
    partner.feedbackUrl = this.feedbackUrl
    partner.contacts = this.contacts
    partner.role = this.role?: partner.role
    return partner
}