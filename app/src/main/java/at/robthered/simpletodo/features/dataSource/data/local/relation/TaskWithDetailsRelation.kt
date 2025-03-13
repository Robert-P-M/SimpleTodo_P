package at.robthered.simpletodo.features.dataSource.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import at.robthered.simpletodo.features.dataSource.data.local.entity.ArchivedEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.CompletedEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.LinkEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.PriorityEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity


data class TaskWithDetailsRelation(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_id",
        entity = CompletedEntity::class
    ) val completed: List<CompletedEntity> = emptyList(),
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_id",
        entity = ArchivedEntity::class
    ) val archived: List<ArchivedEntity> = emptyList(),
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_id",
        entity = PriorityEntity::class
    )
    val priority: List<PriorityEntity> = emptyList(),
    @Relation(
        parentColumn = "current_event_id",
        entityColumn = "event_id",
        entity = EventEntity::class
    )
    val event: EventEntity? = null,
    @Relation(
        parentColumn = "task_id",
        entityColumn = "task_id",
        entity = LinkEntity::class
    )
    val links: List<LinkEntity> = emptyList()
)