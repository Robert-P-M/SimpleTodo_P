package at.robthered.simpletodo.features.dataSource.presentation.model

import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityModel
import at.robthered.simpletodo.features.dataSource.domain.local.relation.TaskWithDetailsAndChildrenModel

fun TaskWithDetailsAndChildrenModel.getCompletedNestedTasksCount(): Int {
    return tasks.count { nestedTask ->
        val isDirectlyCompleted = nestedTask.completed.maxByOrNull { it.createdAt }?.isCompleted ?: false
        val areAllSubtasksCompleted = nestedTask.tasks.isEmpty() || nestedTask.getCompletedNestedTasksCount() == nestedTask.tasks.size

        isDirectlyCompleted && areAllSubtasksCompleted
    }
}

fun TaskWithDetailsAndChildrenModel.areAllNestedTasksCompleted(): Boolean {
    return tasks.all { nestedTasks ->
        val isDirectlyCompleted = nestedTasks.completed.maxByOrNull { it.createdAt }?.isCompleted ?: false
        isDirectlyCompleted && nestedTasks.areAllNestedTasksCompleted()
    }
}

fun TaskWithDetailsAndChildrenModel.isTaskCompleted(): Boolean {
    return completed.maxByOrNull { it.createdAt }?.isCompleted ?: false
}

fun TaskWithDetailsAndChildrenModel.isTaskArchived(): Boolean {
    return archived.maxByOrNull { it.createdAt }?.isArchived ?: false
}

fun TaskWithDetailsAndChildrenModel.getPriorityEnum(): PriorityEnum? {
    return priority.maxByOrNull { it.createdAt }?.priority
}

fun TaskWithDetailsAndChildrenModel.getPriority(): PriorityModel? {
    return priority.maxByOrNull { it.createdAt }
}

fun TaskWithDetailsAndChildrenModel.isEventChecked(): Boolean {
    return event?.isCompleted ?: false
}