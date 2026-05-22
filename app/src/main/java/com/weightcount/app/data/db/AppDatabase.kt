package com.weightcount.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.weightcount.app.data.dao.TagDao
import com.weightcount.app.data.dao.WeightDao
import com.weightcount.app.data.entity.RecordTagCrossRef
import com.weightcount.app.data.entity.Tag
import com.weightcount.app.data.entity.WeightRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [WeightRecord::class, Tag::class, RecordTagCrossRef::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weightDao(): WeightDao
    abstract fun tagDao(): TagDao

    companion object {
        private const val DB_NAME = "weight_count_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .addCallback(SeedCallback())
                .build()
        }

        private val MIGRATION_1_2 = Migration(1, 2) { db ->
            db.execSQL("ALTER TABLE tags ADD COLUMN sortOrder INTEGER NOT NULL DEFAULT 0")
        }
    }

    private class SeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val tagDao = database.tagDao()
                    val presets = listOf("空腹", "晨起", "饭前", "饭后", "睡前")
                    presets.forEachIndexed { index, name ->
                        try {
                            tagDao.insertTag(Tag(name = name, isPreset = true, sortOrder = index))
                        } catch (_: Exception) { }
                    }
                }
            }
        }
    }
}
