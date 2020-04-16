package com.avisper.mvvm.sampleapp.newsapi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.avisper.mvvm.sampleapp.newsapi.db.utils.Converters
import com.avisper.mvvm.sampleapp.newsapi.db.utils.DBConstants.DB_NAME

@Database(
    entities = [ArticleEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        /** useInMemory - used for mostly development */
        fun create(context: Context, useInMemory: Boolean = false): AppDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            }
            return databaseBuilder.build()
        }
    }

    abstract fun articlesDao(): ArticlesDAO
}