package at.robthered.simpletodo.features.dataSource.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration_2_3 = object : Migration(2,3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `link` (
                `link_id` INTEGER PRIMARY KEY AUTOINCREMENT,
                `title` TEXT NOT NULL,
                `description` TEXT,
                `link_url` TEXT NOT NULL,
                `image_file_path` TEXT,
                `created_at` INTEGER NOT NULL,
                `updated_at` INTEGER NOT NULL,
                `task_id` INTEGER,
                FOREIGN KEY(`task_id`) REFERENCES `task`(`task_id`) ON DELETE CASCADE
            )
        """)

        db.execSQL("CREATE INDEX IF NOT EXISTS `index_link_task_id` ON `link` (`task_id`)")
    }
}