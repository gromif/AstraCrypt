package com.nevidimka655.astracrypt.domain.repository.notes

import com.nevidimka655.notes.domain.repository.Repository

abstract class RepositoryDecorator(private val decoratedRepository: Repository) :
    Repository {



}