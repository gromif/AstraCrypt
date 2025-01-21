package com.nevidimka655.astracrypt.view.composables.settings.ui.files

/*
@Composable
fun SettingUiFilesScreen(
    filesViewModeFlow: Flow<ViewMode>,
    onChangeViewMode: (ViewMode) -> Unit
) = PreferencesScreen {
    PreferencesGroup {
        val viewMode by filesViewModeFlow.collectAsStateWithLifecycle(initialValue = ViewMode.Grid)
        var dialogViewModeState by DialogsCore.Selectable.radio(
            onSelected = {
                onChangeViewMode(ViewMode.entries[it])
            },
            title = stringResource(id = R.string.viewMode),
            items = ViewMode.entries.map { stringResource(id = it.stringResId) },
            selectedItemIndex = viewMode.ordinal
        )
        Preference(
            titleText = stringResource(id = R.string.viewMode),
            summaryText = stringResource(viewMode.stringResId)
        ) {
            dialogViewModeState = true
        }
    }
}*/
