package alok.bhowmik.lotteryticket.admin.adapter

import alok.bhowmik.lotteryticket.admin.fragment.AdminAddLotteryFragment
import alok.bhowmik.lotteryticket.admin.fragment.AdminViewLotteryFragment
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdminAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AdminViewLotteryFragment()
            }
            1 -> {
                AdminAddLotteryFragment()
            }
            else -> {
                throw IllegalArgumentException("Error: Fragment is not find in this position")
            }
        }
    }
}