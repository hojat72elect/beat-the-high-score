package com.github.dwursteisen.beat.extensions

import java.math.BigInteger

val Int.second: Float
    get() = this.toFloat()

fun Int.gcd(other: Int): Int {
    val b1 = BigInteger.valueOf(this.toLong())
    val b2 = BigInteger.valueOf(other.toLong())
    val gcd = b1.gcd(b2)
    return gcd.toInt()
}