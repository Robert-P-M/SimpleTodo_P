package at.robthered.simpletodo.features.dataSource.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import at.robthered.simpletodo.features.dataSource.data.local.entity.SectionEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity

data class SectionWithTasksWithDetailsRelation(
    @Embedded val section: SectionEntity,
    @Relation(
        parentColumn = "section_id",
        entityColumn = "section_id",
        entity = TaskEntity::class
    ) val tasks: List<TaskWithDetailsRelation> = emptyList()
)