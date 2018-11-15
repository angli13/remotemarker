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
        val removed = markersMap.remove(marker)
        marker.remove()
        removed?.marker=null
    }
    fun removeAll(){
        markersMap.keys.forEach {
            it.remove()
        }
        for (m in markersMap.values){
            m.marker = null
        }
        markersMap.clear()
    }

    fun getRemoteMarker(marker: Marker): RemoteMarker?{
        return markersMap[marker]
    }

}