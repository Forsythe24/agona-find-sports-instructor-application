package com.solopov.common.data.network.exceptions

sealed class FirebaseException(message: String) : Exception(message) {
    class FirebaseConnectionFailedException(message: String) : FirebaseException(message)
    class FileUploadingException(message: String) : FirebaseException(message)
}
