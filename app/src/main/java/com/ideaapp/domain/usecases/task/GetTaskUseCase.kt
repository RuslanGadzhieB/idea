package com.ideaapp.domain.usecases.task

import com.ideaapp.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke() = taskRepository.getTasks()
}