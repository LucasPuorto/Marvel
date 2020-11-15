package com.lucaspuorto.marvel.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Md5 {
    fun get(ts: String): String {
        try {
            val md = MessageDigest.getInstance(MD5)

            val messageDigest = md.digest(
                ts.toByteArray()
                        + PRIVATE_KEY.toByteArray()
                        + PUBLIC_KEY.toByteArray()
            )

            val no = BigInteger(SIGNUM_1, messageDigest)

            var hashText = no.toString(RADIX)
            while (hashText.length < HASH_TEXT_LENGTH) {
                hashText = "0$hashText"
            }
            return hashText
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}
