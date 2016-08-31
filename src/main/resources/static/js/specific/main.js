/**
 * 
 */
$(document).ready(function(){
	var domH= $(window).height();
	$('.row').css('height', domH-200);
	$(window).resize(function(){
		var domH= $(window).height();
		$('.row').css('height', domH-200);
	});
});