package com.planetjuno

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.planetjuno.databinding.ActivityMainBinding
import com.planetjuno.utils.DateUtils
import com.planetjuno.utils.click
import com.planetjuno.utils.gone
import com.planetjuno.utils.show
import com.planetjuno.viewmodel.BaseViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var binding: ActivityMainBinding

    private var cal = Calendar.getInstance()

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val selectedDate = DateUtils.dateFormatForFetchingPlanetData(cal.time)
            binding.pbImageLoading.show()
            baseViewModel.fetchPlanetData(
                date = selectedDate,
                loadSuccess = {},
                loadFailure = {})

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        baseViewModel = ViewModelProvider(this).get(BaseViewModel(application)::class.java)
        baseViewModel.init()
        baseViewModel.planetData.observe(this, Observer { planetViewDTO ->
            planetViewDTO?.let { data ->
                binding.planetData = data
                Picasso.get()
                    .load(data.hdurl)
                    .into(binding.ivImage, object : Callback {
                        override fun onSuccess() {
                            binding.pbImageLoading.gone()
                        }

                        override fun onError(e: Exception?) {
                            TODO("Not yet implemented")
                        }
                    })

                binding.ivZoomOrPlay.setImageResource(
                    if (data.isVideo == true) R.drawable.ic_play
                    else R.drawable.ic_zoom
                )

                binding.ivZoomOrPlay.click {
                    if (data.isVideo == false) {
                        startImageViewer(data.hdurl)
                    } else {
                        startVideoPlayer(data.url)
                    }
                }
            }
        })

        binding.ivDatePicker.click {
            showDatePickerDialog()
        }

    }

    private fun showDatePickerDialog() {

        DatePickerDialog(
            this@MainActivity, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()


    }

    private fun startVideoPlayer(videoUrl: String?) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(videoUrl)
        )
        intent.putExtra("force_fullscreen", true)
        startActivity(intent)
    }

    private fun startImageViewer(hdUrl: String?) {
        val intent = Intent(
            this@MainActivity, ImageViewerActivity::class.java
        )
        intent.putExtra(
            "hdUrl", hdUrl
        )
        startActivity(intent)
    }
}