function initialize() {
    var lat = document.getElementById("lat").value;
    var log = document.getElementById("log").value;
    var myLatlng = new google.maps.LatLng(lat, log);
//                alert(lat+","+log);
//                var myLatlng = new google.maps.LatLng(-25.363882, 131.044922);
    var mapOptions = {
        zoom: 17,
        center: myLatlng,
        mapTypeId: google.maps.MapTypeId.SATELLITE
    }
    var map = new google.maps.Map(document.getElementById('map-canvas-loc'), mapOptions);

    var marker = new google.maps.Marker({
        position: myLatlng,
        map: map,
        title: 'Localização Imovel'
    });
}

google.maps.event.addDomListener(window, 'load', initialize);