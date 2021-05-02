package alok.bhowmik.lotteryticket.loginRegister.fragment

import alok.bhowmik.lotteryticket.R
import alok.bhowmik.lotteryticket.databinding.FragmentSignUpBinding
import alok.bhowmik.lotteryticket.enum.ValidationEnum
import alok.bhowmik.lotteryticket.loginRegister.listener.FireBaseValueChangeListener
import alok.bhowmik.lotteryticket.loginRegister.model.User
import alok.bhowmik.lotteryticket.loginRegister.validation.UserValidation
import alok.bhowmik.lotteryticket.loginRegister.view.LoginRegistrationView
import alok.bhowmik.lotteryticket.utils.AppLoader
import alok.bhowmik.lotteryticket.utils.LOTTERY
import alok.bhowmik.lotteryticket.utils.USER
import alok.bhowmik.lotteryticket.utils.showToast
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment(R.layout.fragment_sign_up), LoginRegistrationView,
    UserValidation.UserValidationListener {

    private var mBinding: FragmentSignUpBinding? = null
    private val mUser = User("", "", "", "", false)
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            user = mUser
            mView = this@SignUpFragment
        }
    }

    override fun onButtonClick() {
        UserValidation(this).validateUser(mUser)
    }

    override fun onBackClick() {
        activity?.onBackPressed()
    }

    override fun onForgetPasswordClick() {

    }

    override fun onHaveAccountClick() {
        findNavController().navigateUp()
    }

    override fun validationValue(validationEnum: ValidationEnum) {

        when (validationEnum) {
            ValidationEnum.SUCCESS -> {
                createUser(mUser)
            }
            ValidationEnum.EMPTY_EMAIL -> {
                requireContext().showToast("Please provide email to register in this app")
            }
            ValidationEnum.EMPTY_PASSWORD -> {
                requireContext().showToast("Please provide password ")
            }
            ValidationEnum.INVALID_EMAIL -> {
                requireContext().showToast("Please provide valid email address ")
            }
            ValidationEnum.MIN_PASSWORD_LENGTH_INVALID -> {
                requireContext().showToast("Min password length should be 6")
            }
            ValidationEnum.INVALID_BOTH_PASSWORD -> {
                requireContext().showToast("Both password are not match")
            }
        }
    }

    private fun createUser(user: User) {
        AppLoader.createLoader(requireContext())
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                AppLoader.hideLoader()
                when {
                    task.isSuccessful -> {
                        val user = auth.currentUser
                        mUser.id = user.uid
                        mUser.password = ""
                        mUser.confirmPassword = ""
                        addUserToDB()
                        if (user.isEmailVerified) {
                            requireContext().showToast("This email address is already exist")
                        } else {
                            user.sendEmailVerification()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        findNavController().navigate(R.id.verificationEmailFragment)
                                    }
                                }
                        }
                    }
                    else -> {
                        requireContext().showToast("This email address is already exist")
                    }
                }
            }
    }

    private fun addUserToDB() {
        AppLoader.createLoader(requireContext())
        database.child(USER)
            .push()
            .setValue(mUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireContext().showToast("You can now Login to the system")
                } else {
                    requireContext().showToast("Try again !")
                    Log.d("TAG", "addALottery: ${task.exception}")
                }
                AppLoader.hideLoader()
            }
    }

}