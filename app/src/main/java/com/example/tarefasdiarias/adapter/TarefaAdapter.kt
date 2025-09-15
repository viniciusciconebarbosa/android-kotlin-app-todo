package com.example.tarefasdiarias.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tarefasdiarias.R
import com.example.tarefasdiarias.data.Prioridade
import com.example.tarefasdiarias.data.Tarefa
import com.example.tarefasdiarias.databinding.ItemTarefaBinding
import java.text.SimpleDateFormat
import java.util.*

class TarefaAdapter(
    private val onTarefaClick: (Tarefa) -> Unit,
    private val onCheckboxClick: (Tarefa) -> Unit,
    private val onDeleteClick: (Tarefa) -> Unit
) : ListAdapter<Tarefa, TarefaAdapter.TarefaViewHolder>(TarefaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val binding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TarefaViewHolder(private val binding: ItemTarefaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tarefa: Tarefa) {
            binding.apply {
                textTitulo.text = tarefa.titulo
                textDescricao.text = tarefa.descricao
                checkboxConcluida.isChecked = tarefa.concluida
                
                // Formatação da data
                val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                textData.text = dateFormat.format(tarefa.dataCriacao)
                
                // Configuração da prioridade
                val prioridadeColor = when (tarefa.prioridade) {
                    Prioridade.ALTA -> ContextCompat.getColor(root.context, R.color.prioridade_alta)
                    Prioridade.MEDIA -> ContextCompat.getColor(root.context, R.color.prioridade_media)
                    Prioridade.BAIXA -> ContextCompat.getColor(root.context, R.color.prioridade_baixa)
                }
                viewPrioridade.setBackgroundColor(prioridadeColor)
                textPrioridade.text = tarefa.prioridade.valor
                textPrioridade.setTextColor(prioridadeColor)
                
                // Estilo para tarefas concluídas
                if (tarefa.concluida) {
                    textTitulo.paintFlags = textTitulo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textDescricao.paintFlags = textDescricao.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    root.alpha = 0.6f
                } else {
                    textTitulo.paintFlags = textTitulo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    textDescricao.paintFlags = textDescricao.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    root.alpha = 1.0f
                }
                
                // Listeners
                root.setOnClickListener { onTarefaClick(tarefa) }
                checkboxConcluida.setOnClickListener { onCheckboxClick(tarefa) }
                buttonDelete.setOnClickListener { onDeleteClick(tarefa) }
            }
        }
    }
}

class TarefaDiffCallback : DiffUtil.ItemCallback<Tarefa>() {
    override fun areItemsTheSame(oldItem: Tarefa, newItem: Tarefa): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tarefa, newItem: Tarefa): Boolean {
        return oldItem == newItem
    }
}