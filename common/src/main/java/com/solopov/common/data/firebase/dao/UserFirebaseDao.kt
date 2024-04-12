package com.solopov.common.data.firebase.dao

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import com.solopov.common.data.firebase.exceptions.UserDoesNotExistException
import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserFirebaseDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val auth: FirebaseAuth,
    private val dbReference: DatabaseReference,
    private val resManager: ResourceManager,
) {

    suspend fun getUserByUid(uid: String): UserFirebase {

        runCatching(exceptionHandlerDelegate) {
            dbReference.child("user").child(uid).get().await()
        }.onSuccess { value ->
            if (value.exists()) {
                with(value) {
                    return UserFirebase(
                        child(resManager.getString(R.string.id)).value.toString(),
                        child(resManager.getString(R.string.email_lower)).value.toString(),
                        child(resManager.getString(R.string.password_lower)).value.toString(),
                        child(resManager.getString(R.string.name_lower)).value.toString(),
                        child(resManager.getString(R.string.age_lower)).value.toString()
                            .toInt(),
                        child(resManager.getString(R.string.gender_lower)).value.toString(),
                        child(resManager.getString(R.string.sport_lower)).value.toString(),
                        child(resManager.getString(R.string.photo_lower)).value.toString(),
                        child(resManager.getString(R.string.experience_lower)).value.toString(),
                        child(resManager.getString(R.string.description_lower)).value.toString(),
                        child(resManager.getString(R.string.rating_lower)).value.toString()
                            .toFloat(),
                        child(resManager.getString(R.string.hourly_rate_lower)).value.toString()
                            .toFloat(),
                        child(resManager.getString(R.string.instructor_lower)).value.toString()
                            .toBoolean(),
                    )
                }
            } else {
                throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))
            }
        }.onFailure {
            throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))
        }
        throw UserDoesNotExistException(resManager.getString(R.string.this_user_does_not_exist_exception))

    }
    suspend fun getCurrentUser(): UserFirebase {
        return getUserByUid(auth.currentUser!!.uid)
    }


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

        delay(1000L)

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
            sport = "",
            photo = "",
            experience = "",
            description = "",
            rating = 0.0f,
            hourlyRate = 0.0f,
            isInstructor = false)
        dbReference.child(resManager.getString(R.string.user)).child(id).setValue(user)
        return user
    }

    suspend fun signInUser(email: String?, password: String?): Boolean {

        var user: UserFirebase? = null
        if (email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw AuthenticationException.InvalidEmailException(resManager.getString(R.string.invalid_email_exception))
        }

        if (password.isNullOrEmpty()) {
            throw AuthenticationException.NoEmptyPasswordException(resManager.getString(R.string.password_must_not_be_empty))
        }





        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                try {
                    dbReference.child(resManager.getString(R.string.user)).orderByChild(resManager.getString(R.string.id)).equalTo(auth.currentUser!!.uid)

                        .addValueEventListener(object: ValueEventListener {

                            override fun onDataChange(snapshot: DataSnapshot) {

                                for (postSnapshot in snapshot.children) {
                                    with(postSnapshot) {

                                        user = UserFirebase(
                                            child(resManager.getString(R.string.id)).value.toString(),
                                            child(resManager.getString(R.string.email_lower)).value.toString(),
                                            child(resManager.getString(R.string.password_lower)).value.toString(),
                                            child(resManager.getString(R.string.name_lower)).value.toString(),
                                            child(resManager.getString(R.string.age_lower)).value.toString().toInt(),
                                            child(resManager.getString(R.string.gender_lower)).value.toString(),
                                            child(resManager.getString(R.string.sport_lower)).value.toString(),
                                            child(resManager.getString(R.string.photo_lower)).value.toString(),
                                            child(resManager.getString(R.string.experience_lower)).value.toString(),
                                            child(resManager.getString(R.string.description_lower)).value.toString(),
                                            child(resManager.getString(R.string.rating_lower)).value.toString().toFloat(),
                                            child(resManager.getString(R.string.hourly_rate_lower)).value.toString().toFloat(),
                                            child(resManager.getString(R.string.instructor_lower)).value.toString().toBoolean(),
                                        )
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                    })
                } catch (ex: Exception) {
                    exceptionHandlerDelegate.handleException(ex)
                }
            } else {
                task.exception?.let {
                    exceptionHandlerDelegate.handleException(it)
                }
            }
        }.await()

        delay(1000L)
        println(user)

        return if(user == null) {
            throw AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))
        } else {
            true
        }
    }






}
