package com.peraton.ymca.referral.partners

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class Partner(@Id val id: String,
                   var officialName: String,
                   var status: String,
                   var associatedY: String,
                   var feedbackURL: String,
                   var clientId: String,
                   var secretKey: String,
                   var contact: String,
                   var role: String)

data class Contact(@Id val id: String,
                    var name: String,
                    var email: String,
                    var phone: String,
                    var contactType: String)