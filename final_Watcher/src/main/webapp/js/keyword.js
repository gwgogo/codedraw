/**
 * http://usejsdoc.org/
 */
function callKeywordList(){
	$.ajax({
	       url : '/keyWordList',
	       contentType : 'application/json; charset=utf-8',
	       type : 'GET',
	       dataType : 'json',
	   }).done(function(json, status, jqXHR){
		   var keyWordList = json;
		   var idx = 0;
		   var $keywordData = $('.keywordData');
		   $keywordData.each(function(){
			  $(this).text(keyWordList.wordList[idx++].word); 
		   });
	   }).fail(function(xhr, status, e){
	      
	   });
}

$(document).ready(function(){
	callKeywordList();
});