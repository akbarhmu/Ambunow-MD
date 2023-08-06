package braincore.megalogic.ambunow.ui.viewparam

import braincore.megalogic.ambunow.constant.Role

data class UserViewParam(
    val userId: String,
    val name: String,
    val email: String,
    val photo: String,
    val latitude: Double,
    val longitude: Double,
    val fcmToken: String,
    val role: Role,
    val verificationStatus: String
)