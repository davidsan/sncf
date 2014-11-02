var initGoogleMaps = function (lat, lng) {
        google.maps.event.addDomListener(window, 'load', initializeMaps);

        var geocoder = new google.maps.Geocoder();
        var latlng = new google.maps.LatLng(lat, lng);

        var mapOptions = {
            center: latlng,
            scrollWheel: false,
            zoom: 16,
            disableDefaultUI: true,
            styles: [{
                "featureType": "water",
                "stylers": [{
                    "visibility": "on"
                }, {
                    "color": "#acbcc9"
                }]
            }, {
                "featureType": "landscape",
                "stylers": [{
                    "color": "#f2e5d4"
                }]
            }, {
                "featureType": "road.highway",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#c5c6c6"
                }]
            }, {
                "featureType": "road.arterial",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#e4d7c6"
                }]
            }, {
                "featureType": "road.local",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#fbfaf7"
                }]
            }, {
                "featureType": "poi.park",
                "elementType": "geometry",
                "stylers": [{
                    "color": "#c5dac6"
                }]
            }, {
                "featureType": "administrative",
                "stylers": [{
                    "visibility": "on"
                }, {
                    "lightness": 33
                }]
            }, {
                "featureType": "road"
            }, {
                "featureType": "poi.park",
                "elementType": "labels",
                "stylers": [{
                    "visibility": "on"
                }, {
                    "lightness": 20
                }]
            }, {}, {
                "featureType": "road",
                "stylers": [{
                    "lightness": 20
                }]
            }]
        };

        var marker = new google.maps.Marker({
            position: latlng,
            url: '/',
            title: "Marker at " + latlng,
            animation: google.maps.Animation.DROP
        });

        var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

        function initializeMaps() {
            marker.setMap(map);
            codeLatLng();
        };

        function codeLatLng() {
            geocoder.geocode({
                'latLng': latlng
            }, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    if (results[1]) {
                        document.getElementById('sncf-address').innerHTML = '<i class="glyphicon glyphicon-map-marker"></i>' + results[1].formatted_address;
                    } else {
                        console.log('No results found');
                    }
                } else {
                    console.log('Geocoder failed due to: ' + status);
                }
            });
        }; 
    };