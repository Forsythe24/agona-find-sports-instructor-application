package com.solopov.common.utils

import android.util.Patterns
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import javax.inject.Inject

class UserDataValidator @Inject constructor (
    private val resourceManager: ResourceManager
) {

    fun validateEmail(text: String): String? {
        if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return resourceManager.getString(R.string.invalid_email)
        }
        return null
    }

    fun validateAge(text: String): String? {
        if (text.isEmpty()) {
            return resourceManager.getString(R.string.must_not_be_empty)
        }
        if(!text.matches(resourceManager.getString(R.string.age_regex).toRegex())) {
            return resourceManager.getString(R.string.must_contain_only_digits)
        }
        if (text.toInt() < 18) {
            return resourceManager.getString(R.string.must_be_adult)
        }
        return null
    }

    fun validatePassword(text: String): String?
    {
        if(text.length < 8)
        {
            return resourceManager.getString(R.string.minimum_character_password)
        }
        if(!text.matches(resourceManager.getString(R.string.password_regex_one_uppercase_char).toRegex()))
        {
            return resourceManager.getString(R.string.must_contain_uppercase_character)
        }
        if(!text.matches(resourceManager.getString(R.string.password_regex_one_lowercase_char).toRegex()))
        {
            return resourceManager.getString(R.string.must_contain_lowercase_character)
        }
        if(!text.matches(resourceManager.getString(R.string.password_regex_one_special_char).toRegex()))
        {
            return resourceManager.getString(R.string.must_contain_special_character)
        }

        return null
    }


    fun validateName(text: String): String?
    {
        if(text.length < 2) {
            return resourceManager.getString(R.string.name_must_be_at_least_two_characters)
        }
        if(!text.matches(resourceManager.getString(R.string.name_regex).toRegex())) {
            return resourceManager.getString(R.string.must_contain_only_letters_of_the_latin_alphabet)
        }

        return null
    }
}
