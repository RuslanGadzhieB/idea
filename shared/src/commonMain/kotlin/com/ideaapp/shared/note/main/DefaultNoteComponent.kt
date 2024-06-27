package com.ideaapp.shared.note.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.ideaapp.shared.note.create_edit.DefaultNoteCreateEditComponent
import com.ideaapp.shared.note.list.DefaultNoteListComponent
import kotlinx.serialization.Serializable

class DefaultNoteComponent(
    componentContext: ComponentContext,
) : NoteComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val _childStackNavigation = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        childFactory = ::child,
    )

    override val noteChild: Value<ChildStack<*, NoteComponent.NoteChild>> = _childStackNavigation


    override fun openCreteNote() {
        navigation.push(Config.CreateEdit())
    }

    private fun child(config: Config, componentContext: ComponentContext): NoteComponent.NoteChild =
        when (config) {
            Config.Secure -> TODO()
            Config.List -> NoteComponent.NoteChild.ListChild(
                DefaultNoteListComponent(
                    getNoteUseCase =,
                    noteClicked = {
                        navigation.push(Config.CreateEdit(it.id))
                    },
                )
            )

            is Config.CreateEdit -> NoteComponent.NoteChild.CreateEditChild(
                DefaultNoteCreateEditComponent(
                    createNoteUseCase =,
                    getNoteByIdUseCase =,
                    deleteNoteUseCase =,
                    onBack = { navigation.pop() },
                    item =,
                )
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class CreateEdit(val id: Long? = 0L) : Config

        @Serializable
        data object Secure : Config

    }

}