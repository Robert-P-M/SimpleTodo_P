package at.robthered.simpletodo.features.dataSource.data.local.helper

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import at.robthered.simpletodo.features.dataSource.domain.local.helper.TransactionProvider

class TransactionProviderImpl(
    private val database: RoomDatabase
) : TransactionProvider {
    override suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return database.withTransaction(block)
    }
}