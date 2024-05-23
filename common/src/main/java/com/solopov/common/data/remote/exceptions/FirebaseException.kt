package com.solopov.common.data.remote.exceptions

sealed class FirebaseException {
    class FirebaseConnectionFailedException(message: String) : Throwable(message)
    class FileUploadingException(message: String) : Throwable(message)
}
