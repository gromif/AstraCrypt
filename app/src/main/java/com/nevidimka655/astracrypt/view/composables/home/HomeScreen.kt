package com.nevidimka655.astracrypt.view.composables.home

/*
@Composable
fun HomeScreen(
    recentFiles: List<FileItem>,
    imageLoader: ImageLoader,
    coilAvatarModel: CoilTinkModel?,
    defaultAvatar: Avatars? = null,
    name: String?,
    onOpenRecent: (FileItem) -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    ProfileWidget(
        imageLoader = imageLoader,
        name = name,
        coilAvatarModel = coilAvatarModel,
        defaultAvatar = defaultAvatar
    )
    CardWithTitle(
        modifier = Modifier.height(350.dp),
        titleText = stringResource(id = R.string.recentlyAdded)
    ) {
        RecentList(recentFiles = recentFiles, imageLoader = imageLoader) {
            onOpenRecent(it)
        }
    }
}

@Composable
fun RecentList(
    recentFiles: List<FileItem>,
    imageLoader: ImageLoader,
    onClick: (item: FileItem) -> Unit
) = LazyRow(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    items(
        count = recentFiles.size,
        key = { recentFiles[it].id }
    ) {
        val item = recentFiles[it]
        RecentListItem(
            name = item.name,
            imageLoader = imageLoader,
            preview = */
/*item.preview*//*
 null,
            itemType = item.type,
            state = item.state
        ) { onClick(item) }
    }
}

@Composable
fun RecentListItem(
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader,
    name: String,
    preview: String? = null,
    itemType: FileTypes,
    state: FileState,
    onClick: () -> Unit
) = OutlinedCard(
    modifier = modifier
        .fillMaxHeight()
        .width(190.dp)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            if (preview == null) Icon(
                modifier = Modifier.size(72.dp),
                imageVector = itemType.iconAlt,
                contentDescription = null,
                tint = itemType.iconTint
            ) else AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(path = preview),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop
            )
            if (state.isStarred) Icon(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(MaterialTheme.spaces.spaceAltSmall)
                    .background(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        shape = CircleShape
                    )
                    .padding(MaterialTheme.spaces.spaceAltSmall)
                    .size(14.dp),
                imageVector = Icons.Filled.Star,
                tint = MaterialTheme.colorScheme.surface,
                contentDescription = null
            )
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = MaterialTheme.spaces.spaceAltSmall),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        }
    }
}*/
