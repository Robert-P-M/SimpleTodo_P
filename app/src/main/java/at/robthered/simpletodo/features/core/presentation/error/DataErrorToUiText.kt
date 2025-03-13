package at.robthered.simpletodo.features.core.presentation.error

import at.robthered.simpletodo.R
import at.robthered.simpletodo.features.core.domain.error.DataError
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.presentation.UiText

fun Error.toUiText(): UiText {
    val stringRes = when(this) {
        is DataError -> toDataErrorStringRes()

        else -> R.string.error_generic
    }
    return UiText.StringResourceId(id = stringRes)
}

fun DataError.toUiText(): UiText {
    val stringRes = toDataErrorStringRes()

    return UiText.StringResourceId(id = stringRes)
}

fun DataError.toDataErrorStringRes(): Int {
    val stringRes = when (this) {
        DataError.Local.DISK_FULL -> R.string.error_data_local_disk_full
        DataError.Local.UNKNOWN -> R.string.error_data_local_unknown
        DataError.RemoteError.NETWORK_ERROR -> R.string.error_data_remote_network_error
        DataError.RemoteError.REQUEST_TIMEOUT -> R.string.error_data_remote_request_timeout
        DataError.RemoteError.NO_INTERNET -> R.string.error_data_remote_no_internet
        DataError.RemoteError.UNAUTHORIZED -> R.string.error_data_remote_unauthorized
        DataError.RemoteError.CONFLICT -> R.string.error_data_remote_conflict
        DataError.RemoteError.PAYLOAD_TOO_LARGE -> R.string.error_data_remote_payload_to_large
        DataError.RemoteError.TOO_MANY_REQUESTS -> R.string.error_data_remote_to_many_requests
        DataError.RemoteError.SERVER_ERROR -> R.string.error_data_remote_server_ERROR
        DataError.RemoteError.SERIALIZATION -> R.string.error_data_remote_serialization
        DataError.RemoteError.UNKNOWN -> R.string.error_data_remote_unknown
        DataError.Storage.FILE_CREATION_FAILED -> R.string.error_data_storage_file_creation_failed
        DataError.Storage.WRITE_PERMISSION_DENIED -> R.string.error_data_storage_write_permission_denied
        DataError.Storage.INVALID_FILE_NAME -> R.string.error_data_storage_invalid_file_name
        DataError.Storage.STORAGE_FULL -> R.string.error_data_storage_full
        DataError.Storage.UNKNOWN -> R.string.error_data_storage_unknown
    }

    return stringRes
}