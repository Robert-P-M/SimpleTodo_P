package at.robthered.simpletodo.features.dataSource.domain.local.helper

interface TransactionProvider {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R
}