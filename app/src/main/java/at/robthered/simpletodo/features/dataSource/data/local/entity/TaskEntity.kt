package at.robthered.simpletodo.features.dataSource.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "task",
    indices = [
        Index(
            value = ["section_id"]
        ),
        Index(
            value = ["parent_task_id"]
        )
    ],
    foreignKeys = [
        ForeignKey(
            entity = SectionEntity::class,
            parentColumns = ["section_id"],
            childColumns = ["section_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["task_id"],
            childColumns = ["parent_task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "section_id")
    val sectionId: Long? = null,
    @ColumnInfo(name = "parent_task_id")
    val parentTaskId: Long? = null,
    @ColumnInfo(name = "current_event_id")
    val currentEventId: Long? = null,
    /*@Embedded(prefix = "created")
    val created: AppDateTime*/
)