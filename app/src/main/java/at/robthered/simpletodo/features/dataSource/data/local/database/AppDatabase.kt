package at.robthered.simpletodo.features.dataSource.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import at.robthered.simpletodo.features.dataSource.data.local.dao.archived.ArchivedDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.completed.CompletedDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.event.EventDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.eventToTask.EventToTaskDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.link.LinkDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.priority.PriorityDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.section.SectionDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.sectionWithTasks.SectionWithTasksWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.dao.task.TaskDao
import at.robthered.simpletodo.features.dataSource.data.local.entity.ArchivedEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.CompletedEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.EventEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.PriorityEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.SectionEntity
import at.robthered.simpletodo.features.dataSource.data.local.entity.TaskEntity
import at.robthered.simpletodo.features.dataSource.data.local.dao.taskWithDetails.TaskWithDetailsDao
import at.robthered.simpletodo.features.dataSource.data.local.entity.LinkEntity

@Database(
    entities = [
        TaskEntity::class,
        CompletedEntity::class,
        ArchivedEntity::class,
        SectionEntity::class,
        PriorityEntity::class,
        EventEntity::class,
        LinkEntity::class
    ],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun completedDao(): CompletedDao
    abstract fun archivedDao(): ArchivedDao
    abstract fun sectionDao(): SectionDao
    abstract fun priorityDao(): PriorityDao
    abstract fun eventDao(): EventDao
    abstract fun taskWithDetailsDao(): TaskWithDetailsDao
    abstract fun sectionWithTaskWithDetailsDao(): SectionWithTasksWithDetailsDao
    abstract fun eventToTaskDao(): EventToTaskDao
    abstract fun linkDao(): LinkDao

    companion object {
        const val DB_NAME = "simple_todo.db"
    }
}