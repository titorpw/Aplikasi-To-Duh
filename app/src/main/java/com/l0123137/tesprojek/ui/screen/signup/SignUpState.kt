package com.l0123137.tesprojek.ui.screen.signup

import java.time.LocalDate

data class SignUpState(
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val bornDate: LocalDate? = null,
    val password: String = "",
    val confirmPassword: String = "",

    val firstNameError: Boolean = false,
    val lastNameError: Boolean = false,
    val usernameError: Boolean = false,
    val bornDateError: Boolean = false,
    val passwordError: Boolean = false,
    val confirmPasswordError: Boolean = false
)