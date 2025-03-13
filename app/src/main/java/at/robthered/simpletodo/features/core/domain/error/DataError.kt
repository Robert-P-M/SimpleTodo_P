package at.robthered.simpletodo.features.core.domain.error


sealed interface DataError : Error {
    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN,
    }

    enum class RemoteError : DataError {
        NETWORK_ERROR,
        UNAUTHORIZED,
        CONFLICT,
        PAYLOAD_TOO_LARGE,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        REQUEST_TIMEOUT,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Storage : DataError {
        FILE_CREATION_FAILED,
        WRITE_PERMISSION_DENIED,
        INVALID_FILE_NAME,
        STORAGE_FULL,
        UNKNOWN
    }
}