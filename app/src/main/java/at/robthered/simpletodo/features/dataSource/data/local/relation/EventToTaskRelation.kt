package at.robthered.simpletodo.features.dataSource.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity

data class EventToTaskRelation(
    @Embedded
    val currentEvent: EventEntity,
    @Relation(
        parentColumn = "event_id",
        entityColumn = "current_event_id",
        entity = TaskEntity::class
    )
    val task: TaskEntity
)