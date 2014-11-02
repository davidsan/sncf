// Instantiate the Bloodhound suggestion engine
var gares = new Bloodhound({
	datumTokenizer : function(d) {
		for ( var prop in d) {
			return Bloodhound.tokenizers.whitespace(d[prop]);
		}
	},
	queryTokenizer : Bloodhound.tokenizers.whitespace,
	remote : {
		url : 'http://www.raildar.fr/json/gares?search=%QUERY',
		filter : function(gares) {
			// Map the remote source JSON array to a JavaScript array
			return $.map(gares, function(gare) {
				return {
					value : decodeEntities(gare.name_gare),
					id : gare.id_gare
				};
			});
		}
	}
});

function toTitleCase(str) {
	return str.replace(/\w\S*/g, function(txt) {
		return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
	});
}

var gares3 = new Bloodhound({
	datumTokenizer : function(d) {
		for ( var prop in d) {
			return Bloodhound.tokenizers.whitespace(d[prop]);
		}
	},
	queryTokenizer : Bloodhound.tokenizers.whitespace,
	remote : {
		url : 'http://www.raildar.fr/json/gares?id_source=3&search=%QUERY',
		filter : function(gares) {
			// Map the remote source JSON array to a JavaScript array
			return $.map(gares, function(gare) {
				return {
					value : toTitleCase(decodeEntities(gare.name_gare)),
					id : gare.id_gare
				};
			});
		}
	}
});

// Initialize the Bloodhound suggestion engine
gares.initialize();
gares3.initialize();

// Instantiate the Typeahead UI
$('#searchbar').typeahead({
  highlight: true,
  minLength: 3
},
{
  name: 'gares',
	displayKey : name,
	source : gares.ttAdapter(),
	engine : Hogan
},
{
	name: 'gares3',
	displayKey : name,
	source : gares3.ttAdapter(),
	engine : Hogan
});

$('#searchbar').bind('typeahead:selected', function(obj, datum, name) {
	window.location = "/gare/" + datum.id;
});


$('#searchbarlg').typeahead({
  highlight: true,
  minLength: 3
},
{
  name: 'gares',
	displayKey : name,
	source : gares.ttAdapter(),
	engine : Hogan,
	templates: {
		header: '<h5 class="typeahead-header">TER &middot; Intercit&eacute; &middot; TGV &middot; idTGV &middot; Thalys</h5>'
	}
},
{
	name: 'gares3',
	displayKey : name,
	source : gares3.ttAdapter(),
	engine : Hogan,
	 templates: {
    header: '<h5 class="typeahead-header">Transilien &middot; RER</h5>'
  }
});

$('#searchbarlg').bind('typeahead:selected', function(obj, datum, name) {
	window.location = "/gare/" + datum.id;
});