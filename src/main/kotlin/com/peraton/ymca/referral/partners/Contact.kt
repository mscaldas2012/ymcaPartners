package com.peraton.ymca.referral.partners

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "contacts")
 class Contact(var name: String,
               var email: String,
               var phone: String,
               @ManyToOne(fetch = FetchType.LAZY)
               @JoinColumn(name="partnerId", nullable=true)
               @JsonIgnore
               var partner: Partner?,
               @Id var contactId: UUID? = UUID.randomUUID()) {
    constructor() : this("", "", "", null, UUID.randomUUID())
}
