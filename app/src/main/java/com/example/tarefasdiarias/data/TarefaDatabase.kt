package com.example.tarefasdiarias.data

import android.content.Context
import androidx.room.*
import java.util.Date

@Database(
    entities = [Tarefa::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TarefaDatabase : RoomDatabase() {
    
    abstract fun tarefaDao(): TarefaDao
    
    companion object {
        @Volatile
        private var INSTANCE: TarefaDatabase? = null
        
        fun getDatabase(context: Context): TarefaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TarefaDatabase::class.java,
                    "tarefa_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromPrioridade(prioridade: Prioridade): String {
        return prioridade.name
    }
    
    @TypeConverter
    fun toPrioridade(prioridade: String): Prioridade {
        return Prioridade.valueOf(prioridade)
    }
}