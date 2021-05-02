package alok.bhowmik.lotteryticket.loginRegister.fragment

import alok.bhowmik.lotteryticket.databinding.FragmentForgetPasswordBinding
import alok.bhowmik.lotteryticket.enum.ValidationEnum
import alok.bhowmik.lotteryticket.loginRegister.validation.UserValidation
import alok.bhowmik.lotteryticket.loginRegister.view.ForgetPasswordView
import alok.bhowmik.lotteryticket.utils.AppLoader
import alok.bhowmik.lotteryticket.utils.showToast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordFragment : Fragment(), ForgetPasswordView {
    private var mBinding: FragmentForgetPasswordBinding? = null
    private var mUserEmail = ""
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            mView = this@ForgetPasswordFragment
        }
    }

    override fun onSendBtnClick() {
        mUserEmail = mBinding?.forgetPasswordEmail?.text.toString()
        UserValidation(object : UserValidation.UserValidationListener {
            override fun validationValue(validationEnum: ValidationEnum) {
                when (validationEnum) {
                    ValidationEnum.INVALID_EMAIL -> requireContext().showToast("This is an invalid email")
                    ValidationEnum.EMPTY_EMAIL -> requireContext().showToast("Email is required")
                }
            }
        }).isValidEmail(mUserEmail).also {
            if (it) {
                AppLoader.createLoader(requireContext())
                sendForgetPasswordEmail(mUserEmail)
            }
        }
    }

    private fun sendForgetPasswordEmail(mUserEmail: String) {
        auth.sendPasswordResetEmail(mUserEmail).addOnCompleteListener { task ->
            AppLoader.hideLoader()
            if (task.isSuccessful) {
                requireContext().showToast("Check your email to reset password")
            } else {
                requireContext().showToast("Try again ! something went wrong")
            }
        }
    }
}