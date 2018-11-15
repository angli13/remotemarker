package com.fangli.remotemarkertest

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
    fun removeAll(){
        markersMap.keys.forEach {
            it.remove()
        }
        markersMap.clear()
    }

    fun getRemoteMarker(marker: Marker): RemoteMarker?{
        return markersMap[marker]
    }

}