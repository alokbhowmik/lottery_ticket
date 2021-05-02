package alok.bhowmik.lotteryticket.loginRegister.fragment

import alok.bhowmik.lotteryticket.R
import alok.bhowmik.lotteryticket.databinding.FragmentLoginBinding
import alok.bhowmik.lotteryticket.enum.ValidationEnum
import alok.bhowmik.lotteryticket.loginRegister.model.User
import alok.bhowmik.lotteryticket.loginRegister.validation.UserValidation
import alok.bhowmik.lotteryticket.loginRegister.view.LoginRegistrationView
import alok.bhowmik.lotteryticket.utils.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment(), LoginRegistrationView {

    private var mBinding: FragmentLoginBinding? = null
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
        mBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            user = mUser
            mView = this@LoginFragment
        }

        mBinding?.forgetPasswordTV?.setOnClickListener {
        }
    }

    override fun onButtonClick() {
        UserValidation(object : UserValidation.UserValidationListener {
            override fun validationValue(validationEnum: ValidationEnum) {
                when (validationEnum) {
                    ValidationEnum.EMPTY_EMAIL -> requireContext().showToast("Please provide email to register in this app")
                    ValidationEnum.INVALID_EMAIL -> requireContext().showToast("Please provide valid email address ")
                }
            }

        }).isValidEmail(mUser.email).also {
            if (it && mUser.password.isNotEmpty()) {
                signInUser()
            } else {
                requireContext().showToast("Please provide your password ")
            }
        }
    }

    private fun signInUser() {
        AppLoader.createLoader(requireContext())
        auth.signInWithEmailAndPassword(mUser.email, mUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    requireContext().showToast("Login successfully")
                    val currentUser = auth.currentUser
                    findIsAdminUser(currentUser.uid)
//                    findNavController().navigate(R.id.lotteryListFragment)
                } else {
                    requireContext().showToast("${task.exception?.message}")
                }
                AppLoader.hideLoader()
            }
    }

    override fun onBackClick() {

    }

    override fun onForgetPasswordClick() {
        findNavController().navigate(R.id.forgetPasswordFragment)
    }

    override fun onHaveAccountClick() {
        // # Implement for don't have an account
        findNavController().navigate(R.id.signUpFragment)
    }

    private fun findIsAdminUser(userId: String) {

        database.child(USER)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var isAdminFound = false
                    for (snapshot in dataSnapshot.children) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            if (userId == user.id) {
                                requireContext().saveISAdmin(IS_ADMIN, user.admin)
                                requireContext().savePreference(USER_EMAIL, user.email)
                                requireContext().savePreference(USER_ID, user.id)
                                if (user.admin)
                                    isAdminFound = true
                                break
                            }
                        }
                    }
                    if (isAdminFound) {
                        findNavController().navigate(R.id.adminLotteryFragment)
                    } else {
                        findNavController().navigate(R.id.lotteryListFragment)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: $error")
                }
            })

    }
}