package com.example.tarefasdiarias.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tarefasdiarias.data.Tarefa
import com.example.tarefasdiarias.data.TarefaDatabase
import com.example.tarefasdiarias.repository.TarefaRepository
import kotlinx.coroutines.launch

class TarefaViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: TarefaRepository
    val allTarefas: LiveData<List<Tarefa>>
    val tarefasPendentes: LiveData<List<Tarefa>>
    val tarefasConcluidas: LiveData<List<Tarefa>>
    
    init {
        val tarefaDao = TarefaDatabase.getDatabase(application).tarefaDao()
        repository = TarefaRepository(tarefaDao)
        allTarefas = repository.allTarefas
        tarefasPendentes = repository.tarefasPendentes
        tarefasConcluidas = repository.tarefasConcluidas
    }
    
    fun inserir(tarefa: Tarefa) = viewModelScope.launch {
        repository.inserir(tarefa)
    }
    
    fun atualizar(tarefa: Tarefa) = viewModelScope.launch {
        repository.atualizar(tarefa)
    }
    
    fun deletar(tarefa: Tarefa) = viewModelScope.launch {
        repository.deletar(tarefa)
    }
    
    fun marcarComoConcluida(tarefa: Tarefa) = viewModelScope.launch {
        repository.atualizar(tarefa.copy(concluida = true))
    }
    
    fun marcarComoPendente(tarefa: Tarefa) = viewModelScope.launch {
        repository.atualizar(tarefa.copy(concluida = false))
    }
    
    fun deletarConcluidas() = viewModelScope.launch {
        repository.deletarConcluidas()
    }
}