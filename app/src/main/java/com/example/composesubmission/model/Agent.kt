package com.example.composesubmission.model

data class Agent (
    val id: String,
    val name: String,
    val role: String,
    val biography: String,
    val qSkillIcon: String,
    val eSkillIcon: String,
    val cSkillIcon: String,
    val xSkillIcon: String,
    val photoUrl: String,
    val isFavorite: Boolean = false
)