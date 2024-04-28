package com.solopov.common.data.firebase.dao

import android.util.Patterns
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.solopov.common.R
import com.solopov.common.core.resources.ResourceManager
import com.solopov.common.data.firebase.exceptions.AuthenticationException
import com.solopov.common.data.firebase.exceptions.FileUploadingException
import com.solopov.common.data.firebase.exceptions.NoInstructorsFoundException
import com.solopov.common.data.firebase.exceptions.UserDataUpdateFailedException
import com.solopov.common.data.firebase.exceptions.UserDoesNotExistException
import com.solopov.common.data.firebase.model.UserFirebase
import com.solopov.common.utils.ExceptionHandlerDelegate
import com.solopov.common.utils.runCatching
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UserFirebaseDao @Inject constructor(
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    private val auth: FirebaseAuth,
    private val dbReference: DatabaseReference,
    private val resManager: ResourceManager,
    private val storage: FirebaseStorage,
) {

    suspend fun getInstructorsBySport(sport: String): List<UserFirebase> {
        runCatching(exceptionHandlerDelegate) {
            dbReference.child("user").get().await()
        }.onSuccess { dataSnapshot ->
            return dataSnapshot.children
                .filter { uid ->
                    uid.child("sport").value.toString() == sport
                }
                .map { child ->
                    with(child) {
                        UserFirebase(
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
                }
        }.onFailure {
            throw NoInstructorsFoundException(resManager.getString(R.string.no_instructors_found_exception))
        }
        throw NoInstructorsFoundException(resManager.getString(R.string.no_instructors_found_exception))
    }


    suspend fun uploadProfileImage(imageUri: String): String {
        val formatter = SimpleDateFormat(
            resManager.getString(R.string.profile_image_upload_date_format),
            Locale.getDefault()
        )
        val now = Date()
        val filename = formatter.format(now)
        val location =
            resManager.getString(R.string.profile_image_upload_date_format).format(filename)


        val ref = storage.getReference(location)
        runCatching(exceptionHandlerDelegate) {
            ref.putFile(imageUri.toUri()).await()
        }.onSuccess {
            return ref.downloadUrl.await().toString()

        }.onFailure {
            throw FileUploadingException(resManager.getString(R.string.file_uploading_exception))
        }
        throw FileUploadingException(resManager.getString(R.string.file_uploading_exception))
    }

    suspend fun getUserByUid(uid: String): UserFirebase {

        runCatching(exceptionHandlerDelegate) {
            dbReference.child(resManager.getString(R.string.user)).child(uid).get().await()
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

    suspend fun updateUser(user: UserFirebase) {

        val userDetails = HashMap<String, Any>()
        with(user) {
            description?.let { userDetails.put(resManager.getString(R.string.description), it) }
            experience?.let { userDetails.put(resManager.getString(R.string.experience), it) }
            hourlyRate?.let { userDetails.put(resManager.getString(R.string.hourlyrate), it) }
            photo?.let { userDetails.put(resManager.getString(R.string.photo), it) }
            sport?.let { userDetails.put(resManager.getString(R.string.sport), it) }

            userDetails[resManager.getString(R.string.instructor)] = isInstructor
            userDetails[resManager.getString(R.string.name_lower)] = name
            userDetails[resManager.getString(R.string.gender_lower)] = gender
            userDetails["age"] = age
            userDetails["password"] = password
        }
        runCatching(exceptionHandlerDelegate) {
            dbReference.child(resManager.getString(R.string.user)).child(user.id)
                .updateChildren(userDetails).addOnCompleteListener { }.await()
        }.onSuccess {
            return
        }.onFailure {
            throw UserDataUpdateFailedException(resManager.getString(R.string.user_data_update_failed_exception))
        }
    }

    suspend fun updateUserPassword(password: String) {
        auth.currentUser?.updatePassword(password)?.await()
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
        val user = UserFirebase(
            auth.currentUser!!.uid, email, password, name, age, gender,
            sport = "",
            photo = "",
            experience = "",
            description = "",
            rating = 0.0f,
            hourlyRate = 0.0f,
            isInstructor = false
        )
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
                    dbReference.child(resManager.getString(R.string.user))
                        .orderByChild(resManager.getString(R.string.id))
                        .equalTo(auth.currentUser!!.uid)

                        .addValueEventListener(object : ValueEventListener {

                            override fun onDataChange(snapshot: DataSnapshot) {

                                for (postSnapshot in snapshot.children) {
                                    with(postSnapshot) {

                                        user = UserFirebase(
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
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {}

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

        delay(2000L)

        return if (user == null) {
            throw AuthenticationException.WrongEmailOrPasswordException(resManager.getString(R.string.authentication_failed))
        } else {
            true
        }
    }


}
