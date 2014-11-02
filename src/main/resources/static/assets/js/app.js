"use strict";

function split(a, n) {
	var len = a.length, out = [], i = 0;
	while (i < len) {
		var size = Math.ceil((len - i) / n--);
		out.push(a.slice(i, i += size));
	}
	return out;
}


function toTitleCase(str) {
	return str.replace(/\w\S*/g, function(txt) {
		return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
	});
}

var app = angular.module("sncf", []).controller('StationsCtrl',	function($scope, Gares, Gares3) {
    Gares.fetch().success(function(resp){
		$scope.gares = resp;
		$.each($scope.gares, function(index, gare){
			gare.name_gare=decodeEntities(gare.name_gare);
		});
		Gares3.fetch().success(function(resp){
			$.each(resp, function(index, gare){
				gare.name_gare=toTitleCase(decodeEntities(gare.name_gare));
			});
	        $scope.gares = $scope.gares.concat(resp);
					
	        $scope.alphabet = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
	        $scope.garesSorted = _.groupBy($scope.gares, function(item) {
	        	return item.name_gare[0];
	        });
		});
    });
});
