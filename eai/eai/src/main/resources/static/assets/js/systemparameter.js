$(function () {
	$('.edit').click(function () {
		$.ajax({
    		type: "POST",
    		contentType: "application/json",
    		url: "/eai/findbyidsystemparameter",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : JSON.stringify({idSystemParameter : $(this).attr('title')}),
    		dataType: 'json',
    		success: function(response) {
    			messages(response[0]);
    			
    			if(response.status === null){
    				$("#idSystemParameter").val(response[1].idSystemParameter);
	    			$("#value").val(response[1].value);
	    			$("#description").val(response[1].description);
	    			$("#type").val(response[1].type);
	    		}
    			
    			$("#systemparameterModal").modal();
    		}
    	});
	});
	
	$('.update').click(function () {
		$('.badge-success, .badge-danger').text('');
		
		$.ajax({
			type: "POST",
    		contentType: "application/json",
    		url: "/eai/savesystemparameter",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : 
    			JSON.stringify({
    				idSystemParameter : $("#idSystemParameter").val(),
    				value : $("#value").val(),
    				description : $("#description").val(),
    				type : $("#type").val()
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