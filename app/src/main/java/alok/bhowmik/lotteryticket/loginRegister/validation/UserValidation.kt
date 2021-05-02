package alok.bhowmik.lotteryticket.loginRegister.validation

import alok.bhowmik.lotteryticket.enum.ValidationEnum
import alok.bhowmik.lotteryticket.loginRegister.model.User
import android.util.Patterns

class UserValidation(private val listener: UserValidationListener) {


    fun validateUser(user: User) {
        if (isValidEmail(user.email) && isValidPasswrod(user.password) && bothPasswordMatch(
                user.password,
                user.confirmPassword
            )
        ) {
            listener.validationValue(ValidationEnum.SUCCESS)
        }

    }

    private fun bothPasswordMatch(password: String, confirmPassword: String): Boolean {
        return when {
            password != confirmPassword -> {
                listener.validationValue(ValidationEnum.INVALID_BOTH_PASSWORD)
                false
            }
            else -> {
                listener.validationValue(ValidationEnum.SUCCESS)
                true
            }
        }
    }

    private fun isValidPasswrod(password: String): Boolean {
        return when {
            password.isNullOrEmpty() -> {
                listener.validationValue(ValidationEnum.EMPTY_PASSWORD)
                false
            }

            password.length != MIN_PASSWORD_LEN -> {
                listener.validationValue(ValidationEnum.MIN_PASSWORD_LENGTH_INVALID)
                false
            }
            else -> true
        }
    }

     fun isValidEmail(email: String): Boolean {
        return if (email.isNullOrEmpty()) {
            listener.validationValue(ValidationEnum.EMPTY_EMAIL)
            false
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else {
            listener.validationValue(ValidationEnum.INVALID_EMAIL)
            false
        }
    }


    companion object {
        const val MIN_PASSWORD_LEN = 6
    }

    interface UserValidationListener{
         fun validationValue(validationEnum: ValidationEnum)
    }
}