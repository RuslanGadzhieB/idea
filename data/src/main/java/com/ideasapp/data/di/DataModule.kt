package com.ideasapp.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.ideasapp.data.IdeaDataBase
import com.ideasapp.data.dao.NoteDAO
import com.ideasapp.data.dao.ReminderDAO
import com.ideasapp.data.dao.TaskDAO
import com.ideasapp.data.repository.NoteRepositoryImpl
import com.ideasapp.data.repository.ReminderRepositoryImpl
import com.ideasapp.data.repository.TaskRepositoryImpl
import com.ideasapp.domain.repository.NoteRepository
import com.ideasapp.domain.repository.ReminderRepository
import com.ideasapp.domain.repository.TaskRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideAppDate(@ApplicationContext appContext: Context): IdeaDataBase =
        IdeaDataBase(appContext)


    @Provides
    fun provideNoteRepository(appDatabase: IdeaDataBase): NoteRepository {
        return NoteRepositoryImpl(appDatabase.noteDao)
    }


    @Provides
    fun provideNoteDao(appDatabase: IdeaDataBase): NoteDAO = appDatabase.noteDao


    @Provides
    fun provideTaskRepository(appDatabase: IdeaDataBase): TaskRepository {
        return TaskRepositoryImpl(appDatabase.taskDao)
    }


    @Provides
    fun provideTaskDao(appDatabase: IdeaDataBase): TaskDAO = appDatabase.taskDao


    @Provides
    fun provideReminderRepository(appDatabase: IdeaDataBase): ReminderRepository {
        return ReminderRepositoryImpl(appDatabase.reminderDAO)
    }

    @Provides
    fun providerReminderDao(appDatabase: IdeaDataBase): ReminderDAO = appDatabase.reminderDAO

}
