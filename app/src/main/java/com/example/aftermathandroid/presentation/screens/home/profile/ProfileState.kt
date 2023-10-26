package com.example.aftermathandroid.presentation.screens.home.profile

data class ProfileState(
    val name: String = "",
    val avatar: String? = null,
    val initialName: String = "",
    val initialAvatar: String? = null,
    val loading: Boolean = false,
) {
    private val dataWasEdited: Boolean = name != initialName || avatar != initialAvatar
    val canEditProfile: Boolean = !loading && dataWasEdited
}
