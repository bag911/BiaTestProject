package bag.dev.biatestproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Terminal::class, Transactions::class], version = 1, exportSchema = false)
abstract class TerminalDatabase: RoomDatabase() {
    abstract fun terminalDao(): TerminalDao

    companion object {
        @Volatile
        private var INSTANCE: TerminalDatabase? = null

        fun getDatabase(context: Context): TerminalDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, TerminalDatabase::class.java, "terminal_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}