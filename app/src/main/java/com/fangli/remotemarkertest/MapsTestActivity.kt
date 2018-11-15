package com.fangli.remotemarkertest

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsTestActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val manager = RemoteMarkerManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_test)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun removeMarkers(view: View){
        removeMarkers()
    }

    fun removeMarkers(){
        manager.removeAll()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        RemoteMarkerBuilder()
            .setCenterIconUrl("https://api.adorable.io/avatars/98/abott@adorable.png")
            .setMarkerOptions(
                MarkerOptions().position(sydney).title("Marker in Sydney with icon")
            )
            .setContainerIcon(R.drawable.custom_marker_red)
            .build(this, mMap)?.let {
                manager.addMarker(it)
            }
        mMap.setOnMapClickListener {
            addMarker(it)
        }
        mMap.setOnMarkerClickListener {
            manager.getRemoteMarker(it)?.setNewContainerIcon(R.drawable.custom_marker)
            manager.getRemoteMarker(it)?.setNewCenterIconUrl("https://api.adorable.io/avatars/100/abott@adorable.ioas.png")
//            manager.removeMarker(it)
            true
        }

        for (i in 1..10){
            addMarker(sydney)
        }
        removeMarkers()
    }

    private fun addMarker(location: LatLng) {
        val remoteMarker = RemoteMarkerBuilder()
            .setCenterIconUrl("https://api.adorable.io/avatars/98/abott@adorable.png")
            .setMarkerOptions(
                MarkerOptions().position(location).title("Icon")
            )
            .setSize(150)
            .build(this, mMap)

        remoteMarker?.let {
            manager.addMarker(it)
        }
    }




}
