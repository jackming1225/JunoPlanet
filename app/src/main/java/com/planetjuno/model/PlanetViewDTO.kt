package com.planetjuno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlanetViewDTO(
    var copyright: String? = "",
    var date: String? = "",
    var explanation: String? = "",
    var hdurl: String? = "",
    var mediaType: String? = "",
    var title: String? = "",
    var url: String? = "",
    var isVideo: Boolean? = false
) : Parcelable