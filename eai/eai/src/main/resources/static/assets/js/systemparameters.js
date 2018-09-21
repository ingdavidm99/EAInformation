$(function () {
	$('.edit').click(function () {
		$.ajax({
    		type: "POST",
    		contentType: "application/json",
    		url: "/eai/findbyidsystemparameters",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : JSON.stringify({idSystemParameters : $(this).attr('title')}),
    		dataType: 'json',
    		success: function(response) {
    			messages(response);
    			
    			if(response.status === null){
    				$("#idSystemParameters").val(response.data.idSystemParameters);
	    			$("#name").val(response.data.name);
	    			$("#value").val(response.data.value);
	    			$("#description").val(response.data.description);
	    			$("#type").val(response.data.type);
	    			$("#userName").val(response.data.userName);
	    			$("#date").val(response.data.date);
    			}
    			
    			$("#systemparametersModal").modal();
    		}
    	});
	});
	
	$('.update').click(function () {
		$('.badge-success, .badge-danger').text('');
		
		$.ajax({
			type: "POST",
    		contentType: "application/json",
    		url: "/eai/savesystemparameters",
    		beforeSend: function(xhr){xhr.setRequestHeader(header, token)},
    		data : 
    			JSON.stringify({
    				idSystemParameters : $("#idSystemParameters").val(),
    				name : $("#name").val(),
    				value : $("#value").val(),
    				description : $("#description").val(),
    				type : $("#type").val(),
    				userName : $("#userName").val(),
    				date : $("#date").val()
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