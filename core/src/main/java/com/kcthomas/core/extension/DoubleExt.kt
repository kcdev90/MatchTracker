package com.kcthomas.core.extension

import java.math.RoundingMode
import java.text.DecimalFormat

// There is no Kotlin Standard Library to do this! Argh! :(
fun Double.format(): String {
    val df = DecimalFormat("##.#")
    df.roundingMode = RoundingMode.HALF_DOWN
    var formatted = df.format(this)
    when (formatted.length) {
        1 -> formatted = "$formatted.00"
        2 -> formatted = "$formatted.0"
    }
    return formatted
}
