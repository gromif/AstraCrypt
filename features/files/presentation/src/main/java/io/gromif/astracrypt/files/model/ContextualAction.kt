package io.gromif.astracrypt.files.model

sealed class ContextualAction(
    val resetMode: Boolean = false
) {

    data object Close : ContextualAction()

    data object CreateFolder : ContextualAction()

    data object MoveNavigation : ContextualAction()

    data object Move : ContextualAction(resetMode = true)

    data object Delete : ContextualAction(resetMode = true)

    data class Star(val state: Boolean): ContextualAction(resetMode = true)

}