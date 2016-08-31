/**
 * 
 */
$(document).ready(function(){
	var domH= $(window).height();
	$('.row').css('height', domH-150);
	$(window).resize(function(){
		var domH= $(window).height();
		$('.row').css('height', domH-150);
	});
});