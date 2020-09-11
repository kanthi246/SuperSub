package com.example.supersub.Views

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.supersub.Adapters.CategoriesListAdapter
import com.example.supersub.Adapters.ExploreListAdapter
import com.example.supersub.R
import com.example.supersub.Utils.getProgressDrawable
import com.example.supersub.Utils.loadImage
import com.example.supersub.ViewModels.ExploreViewModel
import com.example.supersub.databinding.FragmentExploreBinding
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : Fragment(),ExploreClickListner {

    private lateinit var viewModel: ExploreViewModel
    private val listAdapter=ExploreListAdapter(arrayListOf(),this,"")
    private val categorylistAdapter=CategoriesListAdapter(arrayListOf())

    private lateinit var databinding:FragmentExploreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment using databinding
        databinding= DataBindingUtil.inflate(inflater,R.layout.fragment_explore,container,false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize the viewmodel
        viewModel=ViewModelProviders.of(this).get(ExploreViewModel::class.java)
        viewModel.refresh()

        databinding.rvCategories.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=categorylistAdapter
        }
        databinding.rvFeed.apply {
            layoutManager= LinearLayoutManager(context,)
            adapter=listAdapter
        }
        observeData()
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isloading->
            isloading.let {
                databinding.progressBar.visibility= if(it) View.VISIBLE else View.GONE
                if(it){
                    databinding.tvError.visibility=View.GONE
                    databinding.layout.visibility=View.GONE
                }
            }
        })

        viewModel.exploreresponse.observe(viewLifecycleOwner, Observer {explorelist->
            explorelist?.let {
                databinding.layout.visibility=View.VISIBLE
                databinding.tvWelcome.text=explorelist.banner?.header
                databinding.imBanner.loadImage(explorelist.banner?.image, getProgressDrawable(im_banner.context))
                databinding.tvToppicks.text=explorelist.topPicks?.title
                categorylistAdapter.updatecategorylist(explorelist.categories)
                explorelist.topPicks?.drills?.let { it1 -> listAdapter.updateExplorelist(it1,
                    explorelist.banner?.image.toString()
                ) }
            }
        })

        viewModel.loaderror.observe(viewLifecycleOwner, Observer { isError->
            isError?.let {
                databinding.tvError.visibility= if(it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun OnExploreClicked(v: View, exerciseid: String, imagethub: String) {
        Navigation.findNavController(v).navigate(ExploreFragmentDirections.actionExploreFragmentToExerciseFragment(exerciseid,imagethub))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.explore_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search->{
                Toast.makeText(activity,"Seach Clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_notification->{
                Toast.makeText(activity,"notification Clicked",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}