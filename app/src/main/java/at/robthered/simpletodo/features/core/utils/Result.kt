package at.robthered.simpletodo.features.core.utils

import android.database.sqlite.SQLiteDiskIOException
import android.util.Log
import at.robthered.simpletodo.features.core.domain.error.Error
import at.robthered.simpletodo.features.core.domain.error.DataError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : at.robthered.simpletodo.features.core.domain.error.Error>(val error: E) :
        Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(
    action: (T) -> Unit,
): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E : Error> Result<T, E>.onError(
    action: (E) -> Unit,
): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}

inline fun <T, E : Error> Result<T, E>.handle(
    updateState: (data: T?, error: E?) -> Unit,
): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            updateState(data, null)
            this
        }

        is Result.Error -> {
            updateState(null, error)
            this
        }
    }
}


typealias EmptyResult<E> = Result<Unit, E>

suspend inline fun <T> safeCall(crossinline execute: suspend () -> T): Result<T, Error> {
    return try {
        val result = execute()
        Result.Success(result)
    } catch (e: Exception) {
        e.printStackTrace()
        return Result.Error(mapToDataError(e))
    }
}


fun mapToDataError(e: Throwable): DataError {
    return when (e) {
        is IOException -> DataError.RemoteError.NETWORK_ERROR
        is SQLiteDiskIOException -> DataError.Local.DISK_FULL
        else -> DataError.Local.UNKNOWN
    }
}

inline fun <T> safeFlowCall(crossinline execute: suspend () -> T): Flow<Result<T, Error>> =
    flow {
        try {
            val result = execute()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(mapToDataError(e)))
        }
    }