package com.example.supersub.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.supersub.Models.Drills
import com.example.supersub.R
import com.example.supersub.Utils.getProgressDrawable
import com.example.supersub.Utils.loadImage
import com.example.supersub.Views.ExploreClickListner
import com.example.supersub.databinding.ExploreDataBinding

class ExploreListAdapter(val explorelist: ArrayList<Drills>, val onclick: ExploreClickListner,var imagethub:String):RecyclerView.Adapter<ExploreListAdapter.ExploreViewHolder>() {

    fun updateExplorelist(newlist: List<Drills>, image: String){
        explorelist.clear()
        imagethub=image
        explorelist.addAll(newlist)
        notifyDataSetChanged()
    }

    class ExploreViewHolder(var view: ExploreDataBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<ExploreDataBinding>(
            inflater,
            R.layout.explore_data,
            parent,
            false
        )
        return ExploreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return explorelist.size
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.view.tvSbtitle.text=explorelist[position].subtitle
        holder.view.tvTitle.text="| "+explorelist[position].title
        holder.view.tvDifficulty.text=explorelist[position].difficulty
        holder.view.tvDuration.text=secToTime(explorelist[position].duration)
        holder.view.imageView.loadImage(
            imagethub,
            getProgressDrawable(holder.view.imageView.context)
        )
        holder.view.rowclick.setOnClickListener {it->
            onclick.OnExploreClicked(it,explorelist[position]._id,imagethub)
        }
    }

    fun secToTime(sec: Int): String? {
        val seconds = sec % 60
        val minutes = sec / 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}