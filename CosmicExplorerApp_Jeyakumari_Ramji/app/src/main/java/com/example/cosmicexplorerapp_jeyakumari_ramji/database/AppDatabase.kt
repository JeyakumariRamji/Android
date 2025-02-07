package com.example.cosmicexplorerapp_jeyakumari_ramji.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.MarsPhoto
import com.example.cosmicexplorerapp_jeyakumari_ramji.model.FavoritesDao
import androidx.room.migration.Migration
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [MarsPhoto::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migration 0 to 1 (Table creation)
        private val MIGRATION_0_1 = object : Migration(0, 1) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Log when the table creation is about to happen
                Log.d("Database", "Creating MarsPhoto table")
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS MarsPhoto (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        sol INTEGER NOT NULL,
                        imgSrc TEXT NOT NULL,
                        earthDate TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        // Migration 1 to 2 (Add old column)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Log when the column is being added
                Log.d("Database", "Adding old_column_name to MarsPhoto table")
                db.execSQL("ALTER TABLE MarsPhoto ADD COLUMN old_column_name TEXT DEFAULT ''")
                db.execSQL("ALTER TABLE MarsPhoto ADD COLUMN new_column_name TEXT DEFAULT ''")
            }
        }

        // Migration 2 to 3 (Add new column)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                Log.d("Database", "Adding cameraName and new_column_name to MarsPhoto table")
                db.execSQL("ALTER TABLE MarsPhoto ADD COLUMN cameraName TEXT DEFAULT ''")
                db.execSQL("ALTER TABLE MarsPhoto ADD COLUMN new_column_name TEXT DEFAULT ''")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cosmic-db"
                )
                    // Fallback will recreate the database if migration fails
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_0_1, MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}