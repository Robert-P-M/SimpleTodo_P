package at.robthered.simpletodo.features.dataSource.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE task ADD COLUMN current_event_id INTEGER DEFAULT NULL")

        db.execSQL(
            """
            CREATE TABLE task_new (
                task_id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                description TEXT,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                section_id INTEGER,
                parent_task_id INTEGER,
                current_event_id INTEGER,
                FOREIGN KEY(section_id) REFERENCES section(section_id) ON DELETE CASCADE,
                FOREIGN KEY(parent_task_id) REFERENCES task(task_id) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            INSERT INTO task_new (task_id, title, description, created_at, updated_at, section_id, parent_task_id, current_event_id)
            SELECT task_id, title, description, created_at, updated_at, section_id, parent_task_id, current_event_id FROM task
            """.trimIndent()
        )

        db.execSQL("DROP TABLE task")
        db.execSQL("ALTER TABLE task_new RENAME TO task")


        db.execSQL("CREATE INDEX index_task_section_id ON task(section_id)")
        db.execSQL("CREATE INDEX index_task_parent_task_id ON task(parent_task_id)")
    }
}