package com.l0123137.tesprojek.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.l0123137.tesprojek.data.dao.EventDao
import com.l0123137.tesprojek.data.dao.UserDao
import com.l0123137.tesprojek.data.dao.SessionDao
import com.l0123137.tesprojek.data.model.Event
import com.l0123137.tesprojek.data.model.Session
import com.l0123137.tesprojek.data.model.User

@Database(
    entities = [User::class, Event::class, Session::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun eventDao(): EventDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "toduh_database"
                )
                    // Baris ini penting saat mengubah skema agar aplikasi tidak crash
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}