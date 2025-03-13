package at.robthered.simpletodo.features.dataSource.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "archived",
    indices = [
        Index(
            value = ["task_id"],
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
data class ArchivedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "archived_id")
    val archivedId: Long? = null,
    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,
)