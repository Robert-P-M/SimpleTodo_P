package at.robthered.simpletodo.features.core.presentation.navigation

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import kotlinx.serialization.Serializable


@Serializable
sealed class MainRoute {
    @Serializable
    data object HomeScreen : MainRoute()

    @Serializable
    data class TaskDetailsDialog(
        val taskId: Long?,
    ) : MainRoute()

    @Serializable
    data class AddTaskDialog(
        val projectId: Long? = null,
        val taskOfSectionId: Long? = null,
        val parentTaskId: Long? = null,
        val priority: PriorityEnum? = null,
    ) : MainRoute()

    @Serializable
    data class UpdateTaskDialog(
        val taskId: Long,
    ): MainRoute()

    @Serializable
    data class AddSectionDialog(
        val projectId: Long? = null,
    ) : MainRoute()

    @Serializable
    data class UpdateSectionDialog(
        val sectionId: Long,
    ) : MainRoute()

    @Serializable
    data class UpdatePriorityDialog(
        val taskId: Long?,
        val currentPriority: PriorityEnum?,
    ) : MainRoute()

    @Serializable
    data object DueDialog : MainRoute()

    @Serializable
    data class TaskActivityLogDialog(
        val taskId: Long?
    ): MainRoute()

    @Serializable
    data class SharedUrlProcessorDialog(
        val url: String? = null
    ): MainRoute()

    @Serializable
    data object PriorityPickerDialog: MainRoute()
}