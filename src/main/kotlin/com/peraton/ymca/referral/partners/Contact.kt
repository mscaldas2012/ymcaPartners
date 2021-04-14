package com.peraton.ymca.referral.partners

import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "contacts")
data class Contact(var name: String,
                   var email: String,
                   var phone: String,
//                   @ManyToOne(fetch = FetchType.LAZY)
//                   @JoinColumn(name="partnerId", nullable=false)
//                   var partner: Partner?,
                    @Id var contactId: UUID = UUID.randomUUID()) {
    constructor() : this("", "", "")
}
