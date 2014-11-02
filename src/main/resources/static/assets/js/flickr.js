/* flickr API */
var FLICKR_API_SEARCH_URL = 'https://api.flickr.com/services/rest/?method=flickr.photos.search';
var FLICKR_API_KEY = '2161791d822a98a61637f1d935449b72';
var FLICKR_SORT = 'interestingness-desc'
var FLICKR_PER_PAGE = 200;
var FLICKR_PAGE = 1;
var FLICKR_FORMAT = 'rest';
var FLICKR_LICENSE = '1,2,3,4,5,6,7';
var FLICKR_EXTRAS_SIZE_RAW = 'url_o';
var FLICKR_EXTRAS_SIZE_LARGE = 'url_l';

var FLICKR_API_XML_PHOTOS = 'photos';

var FLICKR_USER_ENZOJZ = '31286543@N06';

function generateRandomId() {
	return Math.floor(Math.random() * FLICKR_PER_PAGE);
}

function flickrRequestURL(sort, text, extras) {
	return FLICKR_API_SEARCH_URL + '&api_key=' + FLICKR_API_KEY + '&sort='
			+ sort + '&text=' + text + '&per_page=' + FLICKR_PER_PAGE
			+ '&page=' + FLICKR_PAGE + '&format=' + FLICKR_FORMAT + '&license='
			+ FLICKR_LICENSE + '&extras=' + extras;
}

function flickrRequestUserURL(user, sort, text, extras) {
	return FLICKR_API_SEARCH_URL + '&api_key=' + FLICKR_API_KEY + '&sort='
			+ sort +  '&user_id=' + user + '&text=' + text + '&per_page=' + FLICKR_PER_PAGE
			+ '&page=' + FLICKR_PAGE + '&format=' + FLICKR_FORMAT + '&license='
			+ FLICKR_LICENSE + '&extras=' + extras;
}

function flickrAjaxRequest(request, field){
	$.ajax({
	    url: request
	}).done(function(data) {
	    var photo = $(data).find(FLICKR_API_XML_PHOTOS).children()[generateRandomId()];
	    var url_o = $(photo).attr(field);
	    $('#sncf-bg-photo').css({
	        'background-image': 'url(' + url_o + ')'
	    });
		$('#sncf-bg-photo').fadeIn(800).delay(800);
	});
}

var initFlickr = function(query) {
	var url_o = FLICKR_EXTRAS_SIZE_RAW;
	var request = flickrRequestURL(FLICKR_SORT, query, url_o);
	flickrAjaxRequest(request, url_o)
};

/**
 * extras value must be equals to url_o or url_l or url_m
 */
var initFlickrUser = function(user, query, extras) {
	var request = flickrRequestUserURL(user, FLICKR_SORT, query, extras);
	flickrAjaxRequest(request, extras);
};

/**
 * extras value must be equals to url_o or url_l or url_m
 */
var initFlickrEnzo = function(query, extras) {
	initFlickrUser(FLICKR_USER_ENZOJZ, query, extras);
};
