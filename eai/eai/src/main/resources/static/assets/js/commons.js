$(function() {
	$('.eai').fadeIn('fade');
	
	$('.leftmenutrigger').on('click', function (e) {
    	$('.side-nav').toggleClass("open");
    	$('#wrapper').toggleClass("open");
   	 	e.preventDefault();
  	});
	
	function lenguaje(lang) {
		$.ajax({
    		type: "GET",
    		contentType: "application/json",
    		url: "/eai/"+lang,
    		data : JSON.stringify(),
    		dataType: 'json',
    		success: function(response) {
    			location.reload();
    		},
    		error: function(response) {
    			location.reload();
    	    }
    	});
	} 
	
	$('#en').click(function () {
		lenguaje('langEN?lang=en')
    });
	
	$('#es').click(function () {
		lenguaje('langES?lang=es')
    });
	
	
	$('.required-icon, label').css("cursor","pointer");
	
	$('[data-toggle="tooltip"]').tooltip();
	
	$('[data-toggle="popover"]').popover({
		placement: 'bottom',
		trigger: 'hover'
	});
	
	$('.required-icon').popover({
		placement: 'bottom',
		trigger: 'hover'
	});
	
	$('.datepicker').datepicker({
		weekStart: 0,
	    daysOfWeekHighlighted: "6,0",
	    autoclose: true,
	    todayHighlight: true,
	    format: "yyyy-mm-dd",
	    language: "es"
	});
	
	$('#search').click(function () {
    	$('#formSearch').submit();
    });	
	
	$('#firstPage').click(function () {
		var page = parseInt($('#page').val());
		var newPage = 1;
		
		if(page > newPage){
			$('#page').val(newPage);
			$('#formSearch').submit();
		}
    });
	
	$('#prev').click(function () {
		var page = parseInt($('#page').val());
		var newPage = page-1;
		
		if(newPage >= 1){
			$('#page').val(newPage);
	    	$('#formSearch').submit();
		}
    });
	
	$('#next').click(function () {
		var page = parseInt($('#page').val());
		var size = parseInt($('#size').val());
		var newPage = page+1;
		
		if(newPage <= size){
			$('#page').val(newPage);
	    	$('#formSearch').submit();
		}
    });
	
	$('#lastPage').click(function () {
		var page = parseInt($('#page').val());
		var size = parseInt($('#size').val());
		
		if(page < size){
			$('#page').val(size);
			$('#formSearch').submit();
		}
    });
	
	$('.closeButton').click(function () {
		$('#formSearch').submit();	
	});
	
	$("body").submit(function(){
		$("#modalLoad").fadeIn('fade');
	});
	
	$(".event").click(function(){
		$("#modalLoad").fadeIn('fade');
	});
});