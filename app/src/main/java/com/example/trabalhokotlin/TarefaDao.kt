package com.example.trabalhokotlin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface TarefaDao {
    @Query("SELECT * FROM Tarefa")
    suspend fun listar(): List<Tarefa>

    @Insert
    suspend fun inserir(tarefa: Tarefa)

    @Delete
    suspend fun deletar(tarefa: Tarefa)

    @Update
    suspend fun atualizar(tarefa: Tarefa)
}

