package com.peraton.ymca.referral.partners

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.peraton.ymca.referral.partners")
		.start()
}

