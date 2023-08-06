package braincore.megalogic.ambunow.data.source.remote.model

import braincore.megalogic.ambunow.constant.Role
import braincore.megalogic.ambunow.ui.viewparam.UserViewParam

data class RemoteUser(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val photo: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val fcmToken: String = "",
    val role: String = "",
    val verificationStatus: String = "",
)

fun RemoteUser.toViewParam(): UserViewParam {
    return UserViewParam(
        userId = userId,
        name = name,
        email = email,
        photo = photo,
        latitude = latitude,
        longitude = longitude,
        fcmToken = fcmToken,
        role = when (role) {
            "admin" -> Role.ADMIN
            "driver" -> Role.DRIVER
            else -> Role.USER
        },
        verificationStatus = verificationStatus
    )
}
