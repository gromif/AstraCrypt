package io.gromif.astracrypt.files.files.model.action

internal object Actions {
    data class Holder(
        val browseActions: BrowseActions = BrowseActions(),
        val importActions: ImportActions = ImportActions(),
        val itemActions: ItemActions = ItemActions(),
        val toolbarActions: ToolbarActions = ToolbarActions(),
        val navigation: FilesNavActions = FilesNavActions(),
    )
}
