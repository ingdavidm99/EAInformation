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
    			$("#idSystemParameters").val(response.idSystemParameters);
    			$("#name").val(response.name);
    			$("#value").val(response.value);
    			$("#description").val(response.description);
    			$("#type").val(response.type);
    			$("#userName").val(response.userName);
    			$("#date").val(response.date);
    			
    			$("#systemparametersModal").modal();
    		},
    		error: function(response) {
    			if(response.responseJSON){
    				$('#messageAjax').find('.danger').text(response.responseJSON.message);
    				$("#systemparametersModal").modal();
    			}else{
    				$(location ).attr("href", "/david/signin");
    			}
    	    }
    	});
	});
	
	$('#update').click(function () {
		$('.success, .danger').text('');
		
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
    			if(response.errors){
    				for (var i = 0; i < response.errors.length; i++) {
    					$('#'+response.errors[i].field+"Error").text(response.errors[i].code);
    				}
    			}else{
    				
    			}
    		},
    		error: function(response) {
    			
    		}
    	});
	});
});