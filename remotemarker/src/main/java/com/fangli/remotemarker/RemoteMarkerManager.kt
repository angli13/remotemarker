package com.fangli.remotemarker

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class RemoteMarkerManager() {

    private val markersMap: HashMap<Marker, RemoteMarker> = hashMapOf()

    fun addMarker(marker: RemoteMarker){
        marker.marker?.let {
            markersMap.put(it, marker)
        }
    }
    fun addMarkers(markers: List<RemoteMarker>){
        markers.forEach { remoteMarker ->
            remoteMarker.marker?.let {
                markersMap.put(it, remoteMarker)
            }
        }
    }
    fun removeMarker(marker: Marker){
        markersMap.remove(marker)
        marker.remove()
    }

    fun getRemoteMarker(marker: Marker): RemoteMarker?{
        return markersMap[marker]
    }

}