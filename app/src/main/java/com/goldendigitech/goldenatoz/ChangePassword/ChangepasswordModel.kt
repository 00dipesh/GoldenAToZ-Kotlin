package com.goldendigitech.goldenatoz.ChangePassword

data class ChangepasswordModel(
    val email: String,
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
