package at.robthered.simpletodo.features.core.domain.error

sealed interface ValidationError : Error {
    enum class TaskTitle(val minLength: Int) : ValidationError {
        EMPTY(minLength = 3),
        TOO_SHORT(minLength = 3),
    }

    enum class TaskDescription(val minLength: Int) : ValidationError {
        EMPTY(minLength = 3),
        TOO_SHORT(minLength = 3),
    }

    enum class SectionTitle(val minLength: Int) : ValidationError {
        EMPTY(minLength = 3),
        TOO_SHORT(minLength = 3),
    }

    enum class LinkUrl(minLength: Int, val maxLength: Int): ValidationError {
        EMPTY(minLength = 3, maxLength = 2048),
        INVALID(minLength = 3, maxLength = 2048),
        NO_HTTP_PROTOCOL(minLength = 3, maxLength = 2048),
        MALFORMED(minLength = 3, maxLength = 2048),
        TOO_LONG(minLength = 3, maxLength = 2048),
    }

    enum class LinkTitle(val minLength: Int) : ValidationError {
        EMPTY(minLength = 3),
        TOO_SHORT(minLength = 3),
    }

    enum class LinkDescription(val minLength: Int): ValidationError {
        EMPTY(minLength = 3),
        TOO_SHORT(minLength = 3),
    }
}