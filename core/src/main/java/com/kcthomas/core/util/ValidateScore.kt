package com.kcthomas.core.util

import androidx.core.text.isDigitsOnly

// Add additional logic if Business/Product demand so later
fun String.validateScore() = isEmpty() || !isDigitsOnly() || toInt() < 0
