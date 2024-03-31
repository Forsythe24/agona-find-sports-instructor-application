package com.solopov.common.data.firebase.dao

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirebaseDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private var auth: FirebaseAuth,
    private var dbReference: DatabaseReference
) {

    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): UserFirebase {

        var user: UserFirebase? = null

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    user = addUserToDatabase(
                        auth.currentUser!!.uid,
                        email,
                        password,
                        name,
                        age,
                        gender,
                    )
                } catch (ex: Exception) {
                    exceptionHandlerDelegate.handleException(ex)
                }
            } else {
                task.exception?.let {
                    exceptionHandlerDelegate.handleException(it)
                }
            }
        }.await()
        return user as UserFirebase
    }

    private fun addUserToDatabase(
        id: String,
        email: String,
        password: String,
        name: String,
        age: Int,
        gender: String,
    ): UserFirebase {
        val user = UserFirebase(auth.currentUser!!.uid, email, password, name, age, gender,
            sport = null,
            photo = null,
            experience = null,
            description = null,
            rating = null,
            hourlyRate = null,
            isInstructor = false)
        dbReference.child("user").child(id).setValue(user)
        return user
    }

    fun signInUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Could I be more successful?")
            } else {
                println("No!")
            }
        }
    }






}
