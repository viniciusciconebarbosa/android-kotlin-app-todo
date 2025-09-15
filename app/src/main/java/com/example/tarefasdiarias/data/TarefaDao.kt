package com.example.tarefasdiarias.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TarefaDao {
    
    @Query("SELECT * FROM tarefas ORDER BY concluida ASC, prioridade DESC, dataCriacao DESC")
    fun getAllTarefas(): LiveData<List<Tarefa>>
    
    @Query("SELECT * FROM tarefas WHERE concluida = 0 ORDER BY prioridade DESC, dataCriacao DESC")
    fun getTarefasPendentes(): LiveData<List<Tarefa>>
    
    @Query("SELECT * FROM tarefas WHERE concluida = 1 ORDER BY dataCriacao DESC")
    fun getTarefasConcluidas(): LiveData<List<Tarefa>>
    
    @Insert
    suspend fun inserir(tarefa: Tarefa)
    
    @Update
    suspend fun atualizar(tarefa: Tarefa)
    
    @Delete
    suspend fun deletar(tarefa: Tarefa)
    
    @Query("DELETE FROM tarefas WHERE concluida = 1")
    suspend fun deletarConcluidas()
}