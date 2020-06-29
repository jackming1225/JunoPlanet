package com.planetjuno.helper

import android.app.Application
import android.content.Context
import com.planetjuno.model.PlanetViewDTO
import com.planetjuno.model.ResponsePlanet
import com.planetjuno.network.PlanetNetworkRepository
import com.planetjuno.utils.PlaneConstants

class PlanetHelper(application: Application) {

    private val context: Context

    init {
        context = application
    }


    /**
     *  Fetch Renewal Schemes
     */
    fun fetchPlanetData(
        loadSuccess: (PlanetViewDTO) -> Unit,
        loadFailure: (String) -> Unit,
        date: String?
    ) {
        PlanetNetworkRepository.fetchPlanetData(
            success = {
                loadSuccess(parsePlanetViewDTO(it.body()))
            },
            error = {
//                loadFailure(ErrorUtils.rQParseError(it).error.message)
            },
            failure = {
                loadFailure(it.toString())
            },
            date = date
        )
    }

    private fun parsePlanetViewDTO(planetData: ResponsePlanet?): PlanetViewDTO {

        planetData?.let { planet ->
            var hdUrl = ""
            var isVideo = false
            when (planet.media_type) {

                PlaneConstants.MEDIA_TYPE_IMAGE -> {
                    hdUrl = planet.hdurl ?: ""
                    isVideo = false
                }

                PlaneConstants.MEDIA_TYPE_VIDEO -> {
                    val videoUrl = planet.url
                    val videoId = videoUrl?.substringAfter("embed/")?.substringBefore("?") ?: ""
                    hdUrl = if (videoId.isEmpty()) ""
                    else "http://img.youtube.com/vi/$videoId/0.jpg"

                    isVideo = true
                }

            }
            return PlanetViewDTO(
                hdurl = hdUrl,
                date = planetData.date,
                copyright = planetData.copyright,
                explanation = planetData.explanation,
                mediaType = planetData.media_type,
                title = planetData.title + "\n" + planetData.date,
                url = planetData.url,
                isVideo = isVideo
            )

        }
        return PlanetViewDTO()
    }
}