"use strict";

app.factory("Gares", function ($http) {
    var API_URI = 'http://www.raildar.fr/json/gares';
    return {
        fetch : function() {
            return $http.get(API_URI);
        }
    };
});

app.factory("Gares3", function ($http) {
    var API_URI = 'http://www.raildar.fr/json/gares?id_source=3';
    return {
        fetch : function() {
            return $http.get(API_URI);
        }
    };
});
