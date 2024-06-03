package com.solopov.common.data.network.exceptions

sealed class FirebaseException {
    class FirebaseConnectionFailedException(message: String) : Throwable(message)
    class FileUploadingException(message: String) : Throwable(message)
}
