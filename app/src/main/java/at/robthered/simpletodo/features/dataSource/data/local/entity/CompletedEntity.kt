package at.robthered.simpletodo.features.dataSource.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "completed",
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
data class CompletedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "completed_id")
    val completedId: Long? = null,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,
)