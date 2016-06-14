var geocoder;
var map;
var markersArray = [];
var address;
//inicializar mapa
function initialize() {
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng(-15.776487056813053, -47.79662229999997);
    var mapOptions = {
        zoom: 4,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.SATELLITE
    }

    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

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

//    alert("POINT(" + location.lat() + " " + location.lng() + ")");
    loc = "POINT(" + location.lat() + " " + location.lng() + ")";
    document.getElementById("loc").value = loc;
    //map.setCenter(location);
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

//obter latlng pelo endereço
function codeAddress() {
    
    var rua = document.getElementById("rua").value;
    var numero = document.getElementById("numero").value;
    var bairro = document.getElementById("bairro").value;
    var cidade = document.getElementById("cidade").value;
    var estado = document.getElementById("estado").value;
    address = rua + "," + numero + "-" + bairro + "-" + cidade + "-" + estado + "- Brasil";
    geocoder.geocode({'address': address}, function(results, status) {
        if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            map.setZoom(17);

            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location

            });

            loc = "POINT(" + results[0].geometry.location.lat() + " " + results[0].geometry.location.lng() + ")";
            document.getElementById("loc").value = loc;
           
        } else {
            alert("Geocode was not successful for the following reason: " + status);
        }
    });
}

google.maps.event.addDomListener(window, 'load', initialize);