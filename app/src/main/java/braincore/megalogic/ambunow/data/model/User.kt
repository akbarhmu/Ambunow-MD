package braincore.megalogic.ambunow.data.model

data class User(
    val userId: String,
    val name: String,
    val email: String,
    val photo: String,
    val latitude: Double,
    val longitude: Double,
    val fcmToken: String,
    val role: String,
    val verificationStatus: String
)
