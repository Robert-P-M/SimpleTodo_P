package at.robthered.simpletodo.features.dataSource.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable


@Entity(
    tableName = "event",
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
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "event_id")
    val eventId: Long? = null,
    @ColumnInfo(name = "start")
    val start: Long,
    // TODO: refactor to enum ACTIVE
    @ColumnInfo(name = "is_active")
    val isActive: Boolean = false,
    @ColumnInfo(name = "duration_minutes")
    val durationMinutes: Int? = null,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "is_with_time")
    val isWithTime: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "is_notification_enabled")
    val isNotificationEnabled: Boolean = false,
    @ColumnInfo(name = "task_id")
    val taskId: Long? = null,
)
/*

@androidx.annotation.Keep
@Serializable
enum class EventStatus {
    ACTIVE,
    INACTIVE
}*/