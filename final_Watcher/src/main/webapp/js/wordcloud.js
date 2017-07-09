/*KOREA*/
var koreaConfig = {
	"graphset" : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 50
		}
	} ]
};

/*USA*/
var usaConfig = {
	graphset : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 30
		}
	} ]
};

/*RUSSIA*/
var russiaConfig = {
	graphset : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 30
		}
	} ]
};

/*FRANCE*/
var franceConfig = {
	graphset : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 30
		}
	} ]
};

/*GERMANY*/
var germanyConfig = {
	graphset : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 30
		}
	} ]
};

/*JAPAN*/
var japanConfig = {
	graphset : [ {
		"type" : "wordcloud",
		"options" : {
			"max-items" : 100,
			"style" : {
				"tooltip" : {
					visible : true,
					text : '%text: %hits'
				}
			},
			"words" : [],
			"max-font-size" : 30
		}
	} ]
};

var MAX_LEN = 100;

function createWordcloud() {
	$.ajax({
		url : '/wordList',
		contentType : 'application/json; charset=utf-8',
		type : 'GET',
		dataType : 'json'
	}).done(function(json, status, jqXHR) {
		var wordList = json.wordList;

		for (var idx = 0; idx < MAX_LEN; idx++) {
			koreaConfig.graphset[0].options.words[idx] = {
				"text" : wordList[0][idx].word,
				"count" : wordList[0][idx].frequency
			}
			usaConfig.graphset[0].options.words[idx] = {
					"text" : wordList[2][idx].word,
					"count" : wordList[2][idx].frequency
				}
			russiaConfig.graphset[0].options.words[idx] = {
					"text" : wordList[5][idx].word,
					"count" : wordList[5][idx].frequency
				}
			franceConfig.graphset[0].options.words[idx] = {
					"text" : wordList[4][idx].word,
					"count" : wordList[4][idx].frequency
				}
			germanyConfig.graphset[0].options.words[idx] = {
					"text" : wordList[1][idx].word,
					"count" : wordList[1][idx].frequency
				}
			japanConfig.graphset[0].options.words[idx] = {
					"text" : wordList[3][idx].word,
					"count" : wordList[3][idx].frequency
				}
		}

		/*KOREA*/
		zingchart.render({
			id : 'koreaChart',
			data : koreaConfig,
			height : "100%",
			width : "100%"
		});

		/*USA*/
		zingchart.render({
			id : 'usaChart',
			data : usaConfig,
			height : "100%",
			width : "100%"
		});
		
		/*RUSSIA*/
		zingchart.render({
			id : 'russiaChart',
			data : russiaConfig,
			height : "100%",
			width : "100%"
		});
		
		/*FRANCE*/
		zingchart.render({
			id : 'franceChart',
			data : franceConfig,
			height : "100%",
			width : "100%"
		});
		
		/*GERMANY*/
		zingchart.render({
			id : 'germanyChart',
			data : germanyConfig,
			height : "100%",
			width : "100%"
		});
		
		/*JAPAN*/
		zingchart.render({
			id : 'japanChart',
			data : japanConfig,
			height : "100%",
			width : "100%"
		});
		
	}).fail(function(xhr, status, e) {

	});
}

$(document).ready(function() {
	createWordcloud();
});