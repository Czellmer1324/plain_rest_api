package com.czellmer1324.requests

import java.math.BigDecimal

data class AddProductRequest(val name: String? = null, val price: BigDecimal? = null, val description: String? = null)