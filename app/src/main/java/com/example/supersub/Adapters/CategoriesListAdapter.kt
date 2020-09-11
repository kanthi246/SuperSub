package com.example.supersub.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.supersub.Models.Category
import com.example.supersub.Models.Explore
import com.example.supersub.R
import com.example.supersub.Utils.getProgressDrawable
import com.example.supersub.Utils.loadImage
import com.example.supersub.Views.ExploreClickListner
import com.example.supersub.databinding.ExploreDataBinding
import com.example.supersub.databinding.RowDataBinding

class CategoriesListAdapter(val categorieslist:ArrayList<Category>):RecyclerView.Adapter<CategoriesListAdapter.CategoryViewHolder>() {

    fun updatecategorylist(newlist:List<Category>){
        categorieslist.clear()
        categorieslist.addAll(newlist)
        notifyDataSetChanged()
    }

    class CategoryViewHolder(var view:RowDataBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<RowDataBinding>(inflater, R.layout.row_data,parent,false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categorieslist.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.view.tvcategories.text=categorieslist[position].name
        //holder.view.imageView1.loadImage(categorieslist[position].icon, getProgressDrawable(holder.view.imageView1.context))
    }
}