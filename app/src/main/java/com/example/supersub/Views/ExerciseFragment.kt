package com.example.supersub.Views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.supersub.R
import com.example.supersub.Utils.getProgressDrawable
import com.example.supersub.Utils.loadImage
import com.example.supersub.ViewModels.ExerciseViewModel
import com.example.supersub.databinding.FragmentExerciseBinding
import kotlinx.android.synthetic.main.fragment_exercise.*
import kotlinx.android.synthetic.main.fragment_explore.progressBar
import kotlinx.android.synthetic.main.fragment_explore.tv_error


class ExerciseFragment : Fragment() {
    private lateinit var exerciseid:String
    private lateinit var imageurl:String

    private lateinit var viewModel: ExerciseViewModel

    private lateinit var databinding: FragmentExerciseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        databinding=DataBindingUtil.inflate(inflater, R.layout.fragment_exercise, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { //retrive the data only notnull
            exerciseid=ExerciseFragmentArgs.fromBundle(it).exerciseid
            imageurl=ExerciseFragmentArgs.fromBundle(it).imageurl
        }

        viewModel= ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
        viewModel.getExerciseData(exerciseid)

        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.loading.observe(viewLifecycleOwner, Observer { isloading ->
            isloading.let {
                progressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tv_error.visibility = View.GONE
                    scrollView.visibility = View.GONE
                }
            }
        })

        viewModel.exerciseresponse.observe(viewLifecycleOwner, Observer { exerciselist ->
            exerciselist?.let {
                databinding.scrollView.visibility = View.VISIBLE
                databinding.imExercise.loadImage(imageurl, getProgressDrawable(im_exercise.context))
                databinding.tvStraightdrive.text = exerciselist.subtitle
                databinding.tvDesc.text = exerciselist.description
                val reps =
                    exerciselist.sets.toString() + getString(R.string.sets) + exerciselist.reps + getString(
                        R.string.reps
                    )
                databinding.tvRepetitions.text = reps
                databinding.imIllustration.loadImage(
                    exerciselist.illustration.imageUrl, getProgressDrawable(
                        im_illustration.context
                    )
                )
                databinding.imExercise.setOnClickListener {
                    watchYoutubeVideo(exerciselist.video.url)
                }

            }
        })

        viewModel.loaderror.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                tv_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.exercise_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_bookmark -> {
                view?.let {
                    Toast.makeText(activity, "Bookmark Clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun watchYoutubeVideo(url: String?) {
        val appIntent = Intent(Intent.ACTION_VIEW)
        appIntent.setPackage("com.google.android.youtube")
        appIntent.data = Uri.parse(url)
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}