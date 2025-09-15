package com.example.tarefasdiarias.repository

import androidx.lifecycle.LiveData
import com.example.tarefasdiarias.data.Tarefa
import com.example.tarefasdiarias.data.TarefaDao

class TarefaRepository(private val tarefaDao: TarefaDao) {
    
    val allTarefas: LiveData<List<Tarefa>> = tarefaDao.getAllTarefas()
    val tarefasPendentes: LiveData<List<Tarefa>> = tarefaDao.getTarefasPendentes()
    val tarefasConcluidas: LiveData<List<Tarefa>> = tarefaDao.getTarefasConcluidas()
    
    suspend fun inserir(tarefa: Tarefa) {
        tarefaDao.inserir(tarefa)
    }
    
    suspend fun atualizar(tarefa: Tarefa) {
        tarefaDao.atualizar(tarefa)
    }
    
    suspend fun deletar(tarefa: Tarefa) {
        tarefaDao.deletar(tarefa)
    }
    
    suspend fun deletarConcluidas() {
        tarefaDao.deletarConcluidas()
    }
}