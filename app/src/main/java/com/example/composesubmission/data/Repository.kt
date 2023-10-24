package com.example.composesubmission.data

import com.example.composesubmission.model.Agent
import com.example.composesubmission.model.AgentData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository {

    private val listAgent = mutableListOf<Agent>()

    init {
        if (listAgent.isEmpty()) {
            AgentData.agents.forEach {
                listAgent.add(it)
            }
        }
    }

    fun getAllAgent(): Flow<List<Agent>> {
        return flowOf(listAgent)
    }

    fun getAgentById(id: String): Agent {
        return listAgent.first {
            it.id == id
        }
    }

    fun searchAgent(query: String): Flow<List<Agent>> {
        return flowOf(listAgent.filter { it.name.contains(query, ignoreCase = true) })
    }

    fun getFavoriteAgent(): Flow<List<Agent>> {
        return flowOf(listAgent.filter { it.isFavorite })
    }

    fun updateAgent(id: String, newValue: Boolean): Flow<Boolean> {
        val index = listAgent.indexOfFirst { it.id == id }
        val result = if (index >= 0) {
            val agent = listAgent[index]
            listAgent[index] = agent.copy(isFavorite = newValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}