package alok.bhowmik.lotteryticket.splash.fragment

import alok.bhowmik.lotteryticket.R
import alok.bhowmik.lotteryticket.utils.IS_ADMIN
import alok.bhowmik.lotteryticket.utils.USER_ID
import alok.bhowmik.lotteryticket.utils.getFromPrefs
import alok.bhowmik.lotteryticket.utils.getIsAdmin
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireContext().getFromPrefs(USER_ID, "").isNotEmpty()) {
            if (requireContext().getIsAdmin(IS_ADMIN, false)) {
                findNavController().navigate(R.id.action_splashFragment_to_adminLotteryFragment)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_lotteryListFragment)
            }
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}