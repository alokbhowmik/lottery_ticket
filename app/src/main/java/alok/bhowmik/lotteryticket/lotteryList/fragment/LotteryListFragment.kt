package alok.bhowmik.lotteryticket.lotteryList.fragment

import alok.bhowmik.lotteryticket.MainActivity
import alok.bhowmik.lotteryticket.R
import alok.bhowmik.lotteryticket.databinding.FragmentLotteryListBinding
import alok.bhowmik.lotteryticket.loginRegister.model.User
import alok.bhowmik.lotteryticket.lotteryList.adapter.LotteryListAdapter
import alok.bhowmik.lotteryticket.lotteryList.listener.LotteryChooseListener
import alok.bhowmik.lotteryticket.lotteryList.model.Lottery
import alok.bhowmik.lotteryticket.utils.*
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class LotteryListFragment : Fragment(), LotteryChooseListener {
    private var mBinding: FragmentLotteryListBinding? = null
    private lateinit var database: DatabaseReference
    private var mAdapter: LotteryListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database.reference
        val query = database.child(LOTTERY).orderByChild("hasBuy").equalTo(false)
        val option = FirebaseRecyclerOptions.Builder<Lottery>()
            .setQuery(query, Lottery::class.java)
            .setLifecycleOwner(this)
            .build()

        mAdapter = LotteryListAdapter(requireContext(), this, option)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentLotteryListBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            lotteryRV.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }

            (activity as MainActivity).setSupportActionBar(listToolbar as Toolbar)
            setHasOptionsMenu(true)
        }
    }

    override fun onBuyLottery(position: Int, ref: DatabaseReference) {
        val userId = requireContext().getFromPrefs(USER_ID, "")
        val email = requireContext().getFromPrefs(USER_EMAIL, "")
        val map = mutableMapOf<String, Any>(
            "hasBuy" to true,
            "userId" to userId,
            "userEmail" to email
        )
        val key = ref.key
        if (key != null) {
            FirebaseDatabase.getInstance().reference
                .child(LOTTERY)
                .child(key)
                .updateChildren(map)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        requireContext().showToast("Yea ! you buy this lottery")
                    } else {
                        requireContext().showToast("Try again !")
                    }
                }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.logoutMenuItem->{
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
                findNavController().navigate(R.id.action_lotteryListFragment_to_loginFragment)
            }

        }.showConfirmationDialog(requireContext())
    }
}
