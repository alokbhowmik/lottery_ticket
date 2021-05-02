package alok.bhowmik.lotteryticket.admin.fragment

import alok.bhowmik.lotteryticket.MainActivity
import alok.bhowmik.lotteryticket.R
import alok.bhowmik.lotteryticket.admin.adapter.AdminAdapter
import alok.bhowmik.lotteryticket.databinding.FragmentAdminLotteryBinding
import alok.bhowmik.lotteryticket.utils.ConfirmationDialog
import alok.bhowmik.lotteryticket.utils.USER_ID
import alok.bhowmik.lotteryticket.utils.savePreference
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator

class AdminLotteryFragment : Fragment() {
    private lateinit var adminAdapter: AdminAdapter
    private var mBinding: FragmentAdminLotteryBinding? = null
    private val tabs = arrayOf("View", "Add")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAdminLotteryBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adminAdapter = AdminAdapter(this)
        mBinding?.apply {
            viewPager2.adapter = adminAdapter

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = tabs[position]
            }.attach()

            (activity as MainActivity).setSupportActionBar(adminToolbar as Toolbar)
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.logoutMenuItem -> {
                showConfirmDialog()
                true
            }
            else -> false
        }
    }


    private fun showConfirmDialog(){
        object : ConfirmationDialog(){
            override fun dialogOkDoWork() {
                requireContext().savePreference(USER_ID, "")
                findNavController().navigate(R.id.action_adminLotteryFragment_to_loginFragment)
            }

        }.showConfirmationDialog(requireContext())
    }
}