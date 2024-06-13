package com.ideasapp.domain.usecase.reminder

import com.ideasapp.domain.model.Reminder
import com.ideasapp.domain.repository.ReminderRepository
import com.ideasapp.domain.repository.ReminderSchedulerRepository

class CreateReminderUseCase (
    private val reminderRepository: ReminderRepository,
    private val reminderScheduler: ReminderSchedulerRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        val id = reminderRepository.insertReminder(reminder)
        reminderScheduler.scheduleAlarm(reminder.copy(id = id))
    }
}