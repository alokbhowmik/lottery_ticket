package alok.bhowmik.lotteryticket.loginRegister.model

data class User(
    var id: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
    var admin: Boolean = false
)
