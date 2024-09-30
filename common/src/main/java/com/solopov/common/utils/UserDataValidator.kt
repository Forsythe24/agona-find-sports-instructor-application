package com.solopov.common.utils

import android.util.Patterns
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import javax.inject.Inject

class UserDataValidator @Inject constructor(
    private val resourceManager: ResourceManager,
) {
    fun validateEmail(text: String): String? {
        if (text.isBlank()) {
            return resourceManager.getString(R.string.must_not_be_blank)
        }
        if (!text.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
            return resourceManager.getString(R.string.invalid_email)
        }
        return null
    }

    fun validateAge(text: String): String? {
        if (text.isBlank()) {
            return resourceManager.getString(R.string.must_not_be_blank)
        }
        if (text.toInt() < 18) {
            return resourceManager.getString(R.string.must_be_adult)
        }
        return null
    }

    fun validatePassword(text: String): String? {
        if (text.length < 8) {
            return resourceManager.getString(R.string.minimum_character_password)
        }
        if (!text.matches(PASSWORD_UPPERCASE_REGEX.toRegex())) {
            return resourceManager.getString(R.string.must_contain_uppercase_character)
        }
        if (!text.matches(PASSWORD_LOWERCASE_REGEX.toRegex())) {
            return resourceManager.getString(R.string.must_contain_lowercase_character)
        }
        if (!text.matches(PASSWORD_SPECIAL_CHAR_REGEX.toRegex())) {
            return resourceManager.getString(R.string.must_contain_special_character)
        }
        return null
    }

    fun validateName(text: String): String? {
        if (text.length < 2) {
            return resourceManager.getString(R.string.name_must_be_at_least_two_characters)
        }
        if (!text.matches(NAME_REGEX.toRegex())) {
            return resourceManager.getString(R.string.must_contain_only_letters_of_the_latin_alphabet)
        }
        return null
    }

    companion object {
        const val PASSWORD_UPPERCASE_REGEX = ".*[A-Z].*"
        const val PASSWORD_LOWERCASE_REGEX = ".*[a-z].*"
        const val PASSWORD_SPECIAL_CHAR_REGEX = ".*[@#\$%^&+=].*"
        const val NAME_REGEX = "[A-Za-z -]{2,}"
    }
}
