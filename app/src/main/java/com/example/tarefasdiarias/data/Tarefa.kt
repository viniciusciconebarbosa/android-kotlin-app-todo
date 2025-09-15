package com.example.tarefasdiarias.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tarefas")
data class Tarefa(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val titulo: String,
    val descricao: String = "",
    val concluida: Boolean = false,
    val dataCriacao: Date = Date(),
    val prioridade: Prioridade = Prioridade.MEDIA
)

enum class Prioridade(val valor: String) {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta")
}