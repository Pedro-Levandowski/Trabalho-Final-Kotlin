package com.example.trabalhokotlin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tarefa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descricao: String,
    val detalhes: String = ""
)

