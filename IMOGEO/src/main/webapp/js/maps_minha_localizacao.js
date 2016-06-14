var map;
var markersArray = [];

function initialize() {
    var mapOptions = {
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.SATELLITE
    };
    map = new google.maps.Map(document.getElementById('mapa_busca_minha_loc'),
            mapOptions);

     // Try HTML5 geolocation
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
                    position.coords.longitude);

            var infowindow = new google.maps.InfoWindow({
                map: map,
                position: pos
            });

            map.setCenter(pos);
            placeMarker(pos);

        }, function() {
            handleNoGeolocation(true);
        });
    } else {
        // Browser doesn't support Geolocation
        handleNoGeolocation(false);
    }
    
    google.maps.event.addListener(map, 'dblclick', function(event) {
        placeMarker(event.latLng);
    });
}

//adicionar marcador
function placeMarker(location) {
    var marker = null;
   
    clearOverlays();
    marker = new google.maps.Marker({
        position: location,
        map: map
    });

    loc = "POINT(" + location.lat() + " " + location.lng() + ")";
    document.getElementById("loc").value = loc;
    markersArray.push(marker);

}

//remover marcadores
function clearOverlays() {
    if (markersArray) {
        for (i in markersArray) {
            markersArray[i].setMap(null);
        }
    }
}

function handleNoGeolocation(errorFlag) {
    if (errorFlag) {
        var content = 'Error: The Geolocation service failed.';
    } else {
        var content = 'Error: Your browser doesn\'t support geolocation.';
    }

    var options = {
        map: map,
        position: new google.maps.LatLng(60, 105),
        content: content
    };

    var infowindow = new google.maps.InfoWindow(options);
    map.setCenter(options.position);
}

google.maps.event.addDomListener(window, 'load', initialize);

