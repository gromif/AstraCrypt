package com.nevidimka655.astracrypt.app

object AppConfig {
    const val DB_DIRS_COUNT = 25
    const val DB_FILE_NAME_COUNT = 13
    const val DB_THUMB_FILE_NAME_COUNT = DB_FILE_NAME_COUNT
    const val DB_THUMB_QUALITY = 70
    const val DB_THUMB_SIZE = 500

    const val CRYPTO_NONCE_SIZE = 64

    const val ITEM_NAME_MAX_SIZE = 90

    const val PROFILE_NAME_MAX_SIZE = 30
    const val PROFILE_IMAGE_SIZE = 300

    const val AUTH_PASSWORD_HASH_LENGTH = 32
    const val AUTH_PASSWORD_MAX_LENGTH = AUTH_PASSWORD_HASH_LENGTH
    const val AUTH_HINT_MAX_LENGTH = 96

    const val PAGING_PAGE_SIZE = 30
    const val PAGING_INITIAL_LOAD = PAGING_PAGE_SIZE
    const val PAGING_ENABLE_PLACEHOLDERS = false

}