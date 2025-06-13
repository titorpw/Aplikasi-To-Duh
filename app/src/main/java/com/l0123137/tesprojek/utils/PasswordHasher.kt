package com.l0123137.tesprojek.utils

import org.mindrot.jbcrypt.BCrypt

object PasswordHasher {

    private const val BCRYPT_SALT_ROUNDS = 12

    fun hashPassword(plainTextPassword: String): String {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(BCRYPT_SALT_ROUNDS))
    }

    fun verifyPassword(plainTextPassword: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainTextPassword, hashedPassword)
    }
}