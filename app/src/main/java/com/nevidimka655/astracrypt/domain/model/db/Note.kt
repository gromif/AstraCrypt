package com.nevidimka655.astracrypt.domain.model.db

data class Note(
    val name: String,
    val text: String,
    val textPreview: String,
    val state: StorageState,
    val creationTime: String
)