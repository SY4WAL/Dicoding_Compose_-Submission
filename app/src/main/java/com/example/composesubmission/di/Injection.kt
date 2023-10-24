package com.example.composesubmission.di

import com.example.composesubmission.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}