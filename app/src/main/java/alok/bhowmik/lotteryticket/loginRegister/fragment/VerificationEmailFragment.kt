package alok.bhowmik.lotteryticket.loginRegister.fragment

import alok.bhowmik.lotteryticket.databinding.VerifyEmailBinding
import alok.bhowmik.lotteryticket.loginRegister.view.VerificationEmailView
import alok.bhowmik.lotteryticket.utils.showToast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class VerificationEmailFragment : Fragment(), VerificationEmailView {
    private var mBinding: VerifyEmailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = VerifyEmailBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            mView = this@VerificationEmailFragment
        }
    }

    override fun onOkBtnClick() {
        requireContext().showToast("")
    }
}