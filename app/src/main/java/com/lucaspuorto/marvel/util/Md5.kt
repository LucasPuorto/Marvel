package com.lucaspuorto.marvel.util

import com.lucaspuorto.marvel.BuildConfig
import com.lucaspuorto.marvel.util.Constants.HASH_TEXT_LENGTH
import com.lucaspuorto.marvel.util.Constants.MD5
import com.lucaspuorto.marvel.util.Constants.PAD_CHAR
import com.lucaspuorto.marvel.util.Constants.PRIVATE_KEY
import com.lucaspuorto.marvel.util.Constants.RADIX
import com.lucaspuorto.marvel.util.Constants.SIGNUM_1
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Md5 {
    fun get(timestamp: String): String {
        try {
            val md = MessageDigest.getInstance(MD5)
            val input = "$timestamp$PRIVATE_KEY${BuildConfig.PUBLIC_KEY}"
            val messageDigest = md.digest(input.toByteArray())
            return BigInteger(SIGNUM_1, messageDigest).toString(RADIX).padStart(HASH_TEXT_LENGTH, PAD_CHAR)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}
