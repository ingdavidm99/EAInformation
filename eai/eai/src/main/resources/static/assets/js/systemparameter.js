$(function () {
	$('.edit').click(function () {
		$.ajax({
    		type: "POST",
    		contentType: "application/json",
    		url: "/eai/findByIdSystemparameter",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : JSON.stringify({idSystemParameter : $(this).attr('title')}),
    		dataType: 'json',
    		success: function(response) {
    			messages(response.messagesResponse);
    			
    			if(response.messagesResponse.status === null){
    				$("#idSystemParameter").val(response.systemParameter.idSystemParameter);
	    			$("#value").val(response.systemParameter.value);
	    			$("#description").val(response.systemParameter.description);
	    			$("#type").val(response.systemParameter.type);
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
    		url: "/eai/updateSystemparameter",
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