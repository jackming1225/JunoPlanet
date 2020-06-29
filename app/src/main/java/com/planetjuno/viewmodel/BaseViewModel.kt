package com.planetjuno.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.planetjuno.helper.PlanetHelper
import com.planetjuno.model.PlanetViewDTO

class BaseViewModel(application: Application) : AndroidViewModel(application) {

    //region ------  Helper Method class instance  ------
    private var planetHelper = PlanetHelper(application)

    /**
     * Planet Data
     */
    var planetData = MutableLiveData<PlanetViewDTO>()

    fun init() {
        fetchPlanetData(
            loadSuccess = {},
            loadFailure = {})
    }

    /**
     * Get Planet data
     */
    fun fetchPlanetData(
        loadSuccess: () -> Unit,
        loadFailure: (String) -> Unit,
        date: String? = ""
    ) {

        planetHelper.fetchPlanetData(
            loadSuccess = { planetViewDTO ->
                planetData.value = planetViewDTO
                loadSuccess()
            },
            loadFailure = {
                loadFailure(it)
            },
            date = date
        )
    }

}