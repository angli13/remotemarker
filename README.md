# Remote Marker

![alt text](https://user-images.githubusercontent.com/3294241/48396415-ee9a8680-e6df-11e8-8c1a-3540488d700f.png)

Remote Marker is a simple library written in Kotlin that makes it very easy to show markers on a map with a custom container and an image loaded from a URL.
A few features of this are:

  - Load image from a URL
  - Set a custom container for the main image
  - Set the size of the marker
  - Change the image and container after the marker has been shown


### Installation

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.angli13:remotemarker:0.1.1'
	}

### Usage

First you create a RemoteMarkerManager to handle all the markers you are going to put on the map.

    private val manager = RemoteMarkerManager()
    
Then you can create a RemoteMarker

    val remoteMarker = RemoteMarkerBuilder()
            .setCenterIconUrl("https://api.adorable.io/avatars/98/abott@adorable.png")
            .setMarkerOptions(MarkerOptions().position(location).title("Icon"))
	    .setContainerIcon(R.drawable.custom_marker) //Optional
            .setSize(150)
            .build(this, mMap)
As you can see, you will pass the usual MarkerOptions, the Url of your image, the size (squared), as well as a Context and the instance of your GoogleMap. Remember that the map should be initialized by this moment.

Then you add this to your manager for later use:

     remoteMarker?.let {
            manager.addMarker(it)
        }

The manager is just used if for example you want to change something about a marker later on:


    manager.getRemoteMarker(mapMarker)?.setNewContainerIcon(R.drawable.custom_marker_red)
    manager.getRemoteMarker(mapMarker)?.setNewCenterIconUrl("https://api.adorable.io/avatars/100/abott@adorable.ioas.png"
    manager.removeMarker(mapMarker) //This will remove the marker from the manager and the map
    

In here mapMarker is a real marker from your map, you might get this from an OnMarkerClickListener or something similar. This way we totally changed the appereance of a marker, this is very useful if you are using different states for your markers.

License
----

MIT


