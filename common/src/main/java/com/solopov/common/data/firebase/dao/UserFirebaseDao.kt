package com.solopov.common.data.firebase.dao

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.options
import javax.inject.Inject
import kotlin.concurrent.timerTask

class UserFirebaseDao {

    @Inject lateinit var auth: FirebaseAuth

    fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Could I be more successful?")
            } else {
                println("No!")
            }
        }

    }


}
