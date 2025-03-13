package at.robthered.simpletodo.features.dataSource.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import at.robthered.simpletodo.features.dataSource.domain.local.model.PriorityEnum

@Entity(
    tableName = "priority",
    indices = [
        Index(
            value = ["task_id"]
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["task_id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PriorityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "priority_id")
    val priorityId: Long? = null,
    @ColumnInfo(name = "priority_enum")
    val priority: PriorityEnum? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,
)