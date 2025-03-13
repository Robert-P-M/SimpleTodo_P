package at.robthered.simpletodo.features.taskActivityProtocolDialog.domain.model

import at.robthered.simpletodo.features.dataSource.domain.local.model.ArchivedModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.CompletedModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.EventModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.LinkModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.model.TaskModel

sealed class TaskActivityModel {
    abstract val createdAt: Long

    data class Task(val task: TaskModel): TaskActivityModel() {
        override val createdAt: Long
            get() = task.createdAt.toEpochMilliseconds()
    }

    data class ChildTask(val childTask: TaskModel, val isFirst: Boolean = false): TaskActivityModel() {
        override val createdAt: Long
            get() = childTask.createdAt.toEpochMilliseconds()
    }

    data class Link(
        val link: LinkModel,
        val isFirst: Boolean = false
    ) : TaskActivityModel() {
        override val createdAt: Long
            get() = link.createdAt.toEpochMilliseconds()
    }

    data class Archived(
        val archived: ArchivedModel,
        val isFirst: Boolean,
    ): TaskActivityModel() {
        override val createdAt: Long
            get() = archived.createdAt.toEpochMilliseconds()
    }
    data class Completed(
        val completed: CompletedModel,
        val isFirst: Boolean,
    ): TaskActivityModel() {
        override val createdAt: Long
            get() = completed.createdAt.toEpochMilliseconds()
    }
    data class Event(
        val event: EventModel,
        val isFirst: Boolean,
    ): TaskActivityModel() {
        override val createdAt: Long
            get() = event.createdAt.toEpochMilliseconds()
    }
    data class Priority(
        val priority: PriorityModel,
        val isFirst: Boolean,
    ): TaskActivityModel() {
        override val createdAt: Long
            get() = priority.createdAt.toEpochMilliseconds()
    }
}