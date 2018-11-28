$(function() {
	$('#paginationUrl').click(function () {
		if($(this).is(':checked'))
			$('#paginationUrlRegex').removeAttr('readonly');
		else
			$('#paginationUrlRegex').attr('readonly', 'readonly');
	});
	
	$('#paginationLink').click(function () {
		if($(this).is(':checked'))
			$('#paginationLinkRegex').removeAttr('readonly');
		else
			$('#paginationLinkRegex').attr('readonly', 'readonly');
	});
	
	$('#paginationSubLink').click(function () {
		if($(this).is(':checked'))
			$('#paginationSubLinkRegex').removeAttr('readonly');
		else
			$('#paginationSubLinkRegex').attr('readonly', 'readonly');
	});
	
	$('.run').click(function () {
		$('.badge-success, .badge-danger').text('');
		
		$.ajax({
			type: "POST",
    		contentType: "application/json",
    		url: "/eai/run",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : 
    			JSON.stringify({
    				baseUrl : $("#baseUrl").val(),
    				
    				urlRegex : $("#urlRegex").val(),
    				paginationUrl : $("#paginationUrl").is(':checked') ? true : false,
    				paginationUrlRegex :  $("#paginationUrl").is(':checked') ? $("#paginationUrlRegex").val() : '',
    				
    				linkRegex : $("#linkRegex").val(),
    				paginationLink : $("#paginationLink").is(':checked') ? true : false,
    				paginationLinkRegex : $("#paginationLink").is(':checked')? $("#paginationLinkRegex").val() : '',
    				
    				subLinkRegex : $("#subLinkRegex").val(),
    				paginationSubLink : $("#paginationSubLink").is(':checked') ? true : false,
    				paginationSubLinkRegex : $("#paginationSubLink").is(':checked') ? $("#paginationSubLinkRegex").val() : '',
    				
    				descriptionRegex : $("#descriptionRegex").val()
    			}),
    		dataType: 'json',
    		success: function(response) {
    			$("#modalLoad").fadeOut('fade', function() {
    				messages(response);
				});
    		}
    	});
	});
});
