package at.robthered.simpletodo.features.core.domain.error

sealed interface ImageError: Error {
    enum class Storage: ImageError {
        FILE_CREATION_FAILED,
        WRITE_PERMISSION_DENIED,
        INVALID_FILE_NAME,
        STORAGE_FULL,
        UNKNOWN
    }
    enum class Processing: ImageError {
        INVALID_FORMAT,
        CORRUPTED_DATA,
        UNSUPPORTED_COMPRESSION,
        DECODING_FAILED,
        UNKNOWN
    }
}