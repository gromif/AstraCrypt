package com.nevidimka655.astracrypt.view.composables.settings.profile

/*
@Composable
fun EditProfileScreen(
    imageLoader: ImageLoader,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null,
    name: String,
    isImageProcessing: Boolean,
    onChangeName: (String) -> Unit,
    onDefaultAvatar: (Avatars) -> Unit,
    onGalleryAvatar: () -> Unit
) = PreferencesScreen {
    val dialogChangeNameState = dialogChangeName(currentName = name) { onChangeName(it.trim()) }
    val dialogChangeAvatarState = Compose.state()
    PreferencesGroup {
        Preference(
            titleText = stringResource(id = R.string.settings_changeName),
            summaryText = name
        ) { dialogChangeNameState.value = true }
        Preference(
            titleText = stringResource(id = R.string.settings_changeAvatar),
            trailingContent = {
                if (!isImageProcessing) ProfileIcon(
                    imageLoader = imageLoader,
                    coilAvatarModel = coilAvatarModel,
                    defaultAvatar = defaultAvatar,
                    iconSize = 56.dp,
                    showBorder = false
                )
            }
        ) { dialogChangeAvatarState.value = true }
    }
    DialogChangeAvatar(
        state = dialogChangeAvatarState,
        galleryCallback = onGalleryAvatar,
        onDefaultAvatarClick = onDefaultAvatar
    )
}

@Composable
private fun dialogChangeName(
    currentName: String,
    onNameChange: (String) -> Unit
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.settings_changeName),
    params = DialogsCore.TextFields.Params(
        text = currentName,
        label = stringResource(id = R.string.name),
        singleLine = true,
        maxLength = AppConfig.PROFILE_NAME_MAX_SIZE,
    ),
    onResult = onNameChange
)

@Composable
private fun DialogChangeAvatar(
    state: MutableState<Boolean>,
    galleryCallback: () -> Unit,
    onDefaultAvatarClick: (Avatars) -> Unit
) {
    if (!state.value) return
    Dialog(
        title = DialogDefaults.titleCentered(title = stringResource(id = R.string.settings_changeAvatar)),
        onDismissRequest = { state.value = false },
        confirmButton = DialogDefaults.textButton(title = stringResource(id = android.R.string.cancel)) {
            state.value = false
        },
        dismissButton = DialogDefaults.textButton(title = stringResource(id = R.string.gallery)) {
            state.value = false
            galleryCallback()
        },
        content = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
            ) {
                items(Avatars.entries) {
                    Box(contentAlignment = Alignment.Center) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .clickable {
                                    state.value = false
                                    onDefaultAvatarClick(it)
                                },
                            painter = defaultProfileAvatar(it),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}*/
