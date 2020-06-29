package com.planetjuno.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ResponsePlanet(
    var copyright: String? = "",
    var date: String? = "",
    var explanation: String? = "",
    var hdurl: String? = "",
    var media_type: String? = "",
    var title: String? = "",
    var url: String? = ""
) : Parcelable