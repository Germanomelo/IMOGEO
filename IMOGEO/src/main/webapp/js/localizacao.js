var geocoder;
var map;
var markersArray = [];

function initialize() {
    var mapOptions = {
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.SATELLITE
    };
    map = new google.maps.Map(document.getElementById('map-canvas'),
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

function codeAddress() {

    var rua = document.getElementById("rua").value;
    var numero = document.getElementById("numero").value;
    var bairro = document.getElementById("bairro").value;
    var cidade = document.getElementById("cidade").value;
    var estado = document.getElementById("estado").value;
    address = rua + "," + numero + "-" + bairro + "-" + cidade + "-" + estado + "- Brasil";
//    alert(address);
    geocoder.geocode({'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            map.setZoom(17);

            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location

            });
//            placeMarker(marker.geometry.location);
//            loc = "POINT(" + marker.geometry.location.lat() + " " + marker.geometry.location.lng() + ")";
//            alert(loc);
//            document.getElementById("loc").value = loc;

        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

google.maps.event.addDomListener(window, 'load', initialize);