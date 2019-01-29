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
    			messages(response);
    			
    			if(response.status === null){
    				$("#idSystemParameter").val(response.data.idSystemParameter);
	    			$("#value").val(response.data.value);
	    			$("#description").val(response.data.description);
	    			$("#type").val(response.data.type);
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