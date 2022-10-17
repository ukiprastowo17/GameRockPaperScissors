package com.blackhole.gamerockpaperscissors.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SliderData(
    val title : String,
    val desc : String,
    val imgSlider : String
) : Parcelable
