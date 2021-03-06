package com.fangli.remotemarkertest

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.*


class RemoteMarker(private val context: Context,
                   var marker: Marker?,
                   private var centerIconUrl: String,
                   private var containerIcon: Int,
                   private var size: Int = 100) {

    var containerBitmap = BitmapFactory.decodeResource(context.resources, containerIcon)
    lateinit var scaledBitmap: Bitmap

    init {
        setIconForMarker()
    }

    fun setNewCenterIconUrl(url: String){
        this.centerIconUrl = url
        setIconForMarker()
    }

    fun setNewContainerIcon(containerDrawableId: Int){
        containerIcon = containerDrawableId
        containerBitmap = BitmapFactory.decodeResource(context.resources, containerIcon)
        setIconForMarker()
    }

    private lateinit var job: Job

    private fun buildNewBitmap(bitmap: Bitmap) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val bmOverlay = Bitmap.createBitmap(size, size, containerBitmap.config)
            val canvas = Canvas(bmOverlay)
            canvas.drawBitmap(scaledBitmap, Matrix(), null)
            canvas.drawBitmap(bitmap, scaledBitmap.width*0.125f, scaledBitmap.width*0.04f, null)
            withContext(Dispatchers.Main){
                bmOverlay?.let {
                    if (marker?.position!=null){
                        marker?.setIcon(
                            BitmapDescriptorFactory.fromBitmap(it)
                        )
                    }
                }
            }
        }
    }

    private fun setIconForMarker() {

        scaledBitmap = Bitmap.createScaledBitmap(containerBitmap, size, size, false)
        val newSize = (scaledBitmap.width*0.75).toInt()
        Glide.with(context)
            .asBitmap()
            .apply(RequestOptions().circleCrop().override(newSize,newSize))
            .load(centerIconUrl)
            .into(object: SimpleTarget<Bitmap>(){
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    resource.let {
                        buildNewBitmap(it)
                    }
                }
            })
    }
}

class RemoteMarkerBuilder() {
    var markerOptions: MarkerOptions? = null
    var centerIconUrl: String? = null
    var containerIcon: Int? = null
    var size: Int = 100

    fun setMarkerOptions(markerOptions: MarkerOptions): RemoteMarkerBuilder{

        this.markerOptions = markerOptions.apply {
            containerIcon?.let {
                icon(BitmapDescriptorFactory.fromResource(it))
            }
        }
        return this
    }
    fun setCenterIconUrl(url: String): RemoteMarkerBuilder{
        this.centerIconUrl = url
        return this
    }

    fun setContainerIcon(containerDrawableId: Int): RemoteMarkerBuilder{
        markerOptions?.icon(BitmapDescriptorFactory.fromResource(containerDrawableId))
        this.containerIcon = containerDrawableId
        return this
    }

    fun setSize(size: Int): RemoteMarkerBuilder{
        this.size = size
        return this
    }

    fun build(context: Context, map: GoogleMap): RemoteMarker?{
        if (markerOptions != null && centerIconUrl != null){
            if (containerIcon==null)
                containerIcon=R.drawable.custom_marker
            markerOptions?.icon(BitmapDescriptorFactory.fromResource(R.drawable.empty))
            return RemoteMarker(context, map.addMarker(markerOptions), centerIconUrl!!, containerIcon!!, size)
        }
        return null

    }
}