package com.example.tarefasdiarias

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarefasdiarias.adapter.TarefaAdapter
import com.example.tarefasdiarias.data.Prioridade
import com.example.tarefasdiarias.data.Tarefa
import com.example.tarefasdiarias.databinding.ActivityMainBinding
import com.example.tarefasdiarias.databinding.DialogAdicionarTarefaBinding
import com.example.tarefasdiarias.viewmodel.TarefaViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val tarefaViewModel: TarefaViewModel by viewModels()
    private lateinit var adapter: TarefaAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupRecyclerView()
        setupFab()
        observeViewModel()
    }
    
    private fun setupRecyclerView() {
        adapter = TarefaAdapter(
            onTarefaClick = { tarefa -> editarTarefa(tarefa) },
            onCheckboxClick = { tarefa -> toggleTarefaConcluida(tarefa) },
            onDeleteClick = { tarefa -> confirmarDelecao(tarefa) }
        )
        
        binding.recyclerViewTarefas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
    
    private fun setupFab() {
        binding.fabAdicionar.setOnClickListener {
            mostrarDialogAdicionarTarefa()
        }
    }
    
    private fun observeViewModel() {
        tarefaViewModel.allTarefas.observe(this) { tarefas ->
            adapter.submitList(tarefas)
            
            // Mostrar mensagem se não há tarefas
            if (tarefas.isEmpty()) {
                binding.textEmptyState.visibility = android.view.View.VISIBLE
                binding.recyclerViewTarefas.visibility = android.view.View.GONE
            } else {
                binding.textEmptyState.visibility = android.view.View.GONE
                binding.recyclerViewTarefas.visibility = android.view.View.VISIBLE
            }
        }
    }
    
    private fun mostrarDialogAdicionarTarefa(tarefaParaEditar: Tarefa? = null) {
        val dialogBinding = DialogAdicionarTarefaBinding.inflate(layoutInflater)
        
        // Se estamos editando, preencher os campos
        tarefaParaEditar?.let { tarefa ->
            dialogBinding.editTitulo.setText(tarefa.titulo)
            dialogBinding.editDescricao.setText(tarefa.descricao)
            dialogBinding.spinnerPrioridade.setSelection(tarefa.prioridade.ordinal)
        }
        
        AlertDialog.Builder(this)
            .setTitle(if (tarefaParaEditar == null) "Nova Tarefa" else "Editar Tarefa")
            .setView(dialogBinding.root)
            .setPositiveButton("Salvar") { _, _ ->
                val titulo = dialogBinding.editTitulo.text.toString().trim()
                val descricao = dialogBinding.editDescricao.text.toString().trim()
                val prioridade = Prioridade.values()[dialogBinding.spinnerPrioridade.selectedItemPosition]
                
                if (titulo.isNotEmpty()) {
                    if (tarefaParaEditar == null) {
                        // Nova tarefa
                        val novaTarefa = Tarefa(
                            titulo = titulo,
                            descricao = descricao,
                            prioridade = prioridade
                        )
                        tarefaViewModel.inserir(novaTarefa)
                        Snackbar.make(binding.root, "Tarefa adicionada!", Snackbar.LENGTH_SHORT).show()
                    } else {
                        // Editar tarefa existente
                        val tarefaAtualizada = tarefaParaEditar.copy(
                            titulo = titulo,
                            descricao = descricao,
                            prioridade = prioridade
                        )
                        tarefaViewModel.atualizar(tarefaAtualizada)
                        Snackbar.make(binding.root, "Tarefa atualizada!", Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(binding.root, "Título é obrigatório!", Snackbar.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun editarTarefa(tarefa: Tarefa) {
        mostrarDialogAdicionarTarefa(tarefa)
    }
    
    private fun toggleTarefaConcluida(tarefa: Tarefa) {
        if (tarefa.concluida) {
            tarefaViewModel.marcarComoPendente(tarefa)
        } else {
            tarefaViewModel.marcarComoConcluida(tarefa)
        }
    }
    
    private fun confirmarDelecao(tarefa: Tarefa) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar exclusão")
            .setMessage("Deseja realmente excluir a tarefa '${tarefa.titulo}'?")
            .setPositiveButton("Excluir") { _, _ ->
                tarefaViewModel.deletar(tarefa)
                Snackbar.make(binding.root, "Tarefa excluída!", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_completed -> {
                confirmarDelecaoTarefasConcluidas()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun confirmarDelecaoTarefasConcluidas() {
        AlertDialog.Builder(this)
            .setTitle("Limpar concluídas")
            .setMessage("Deseja excluir todas as tarefas concluídas?")
            .setPositiveButton("Excluir") { _, _ ->
                tarefaViewModel.deletarConcluidas()
                Snackbar.make(binding.root, "Tarefas concluídas removidas!", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}