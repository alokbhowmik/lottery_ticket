package alok.bhowmik.lotteryticket.lotteryList.adapter

import alok.bhowmik.lotteryticket.databinding.ItemLotteryBinding
import alok.bhowmik.lotteryticket.lotteryList.listener.LotteryChooseListener
import alok.bhowmik.lotteryticket.lotteryList.model.Lottery
import alok.bhowmik.lotteryticket.lotteryList.viewHolder.LotteryViewHolder
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class LotteryListAdapter(
    private val context: Context,
    val listener: LotteryChooseListener, options: FirebaseRecyclerOptions<Lottery>
) : FirebaseRecyclerAdapter<Lottery, LotteryViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LotteryViewHolder {
        val binding = ItemLotteryBinding.inflate(LayoutInflater.from(context), parent, false)
        return LotteryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LotteryViewHolder, position: Int, model: Lottery) {
        holder.bind(model, position, getRef(position), listener)
    }

}

