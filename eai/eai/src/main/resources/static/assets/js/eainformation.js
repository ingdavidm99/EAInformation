$(function () {
	var x = 0;
	var y = 1;
	var timer;
	
	function progressBar(id){
		if(x != y){
			$.ajax({
        		type: "POST",
        		contentType: "application/json",
        		url: "/eai/progress",
        		beforeSend: function(xhr){
        			xhr.setRequestHeader(header, token)
        		},
        		data : JSON.stringify(),
        		dataType: 'json',
        		success: function(response) {
        			messages(response);
        			
        			if(response.status != 'E'){
	    				$('#progress-bar'+id).css('width',response.percentage+'%');
	            		$('#progress-bar'+id).text(response.percentage+'%');
	            		$('#progress-value'+id).css('width',response.percentage+'%');
	            		$('#status_'+id).text(response.status);
	            		$('#processing_'+id).text(response.count + " - "+ response.length);
	            		$('#success_'+id).text(response.countSuccess);
	            		$('#fail_'+id).text(response.countFail);
	            		$('#error_'+id).text(response.countError);
	            		x = response.count;
	            		y = response.length;
        			}else{
        				clearInterval(timer);
        			}
        		}
        	});
		}else{
			$('.next-step').attr("disabled", false);
			$('.prep-step').attr("disabled", false);
			$('#eainformation'+id).attr("disabled", false);
			clearInterval(timer);
		}
    }
	
	function step(id, url, time){
		$.ajax({
    		type: "POST",
    		contentType: "application/json",
    		url: "/eai/"+url,
    		beforeSend: function(xhr){
    			$('#eainformation'+id).attr("disabled", true);
    			xhr.setRequestHeader(header, token);
    		},
    		data : JSON.stringify(),
    		dataType: 'json',
    		success: function(response) {
    			messages(response);
    			if(response.status != 'E'){
    				x = 0;
    		    	y = 1;
    		    	timer = setInterval(progressBar, time, id);
    			}
    		}
    	});
	}
	
    $('#eainformation1').click(function () {
    	step(1, "step1", 2000);
    });  
	
    $('#eainformation2').click(function () {
    	step(2, "step2", 2000);
    });  
    
    $('#eainformation3').click(function () {
    	step(3, "step3", 2000);
    }); 
    
    $('#eainformation4').click(function () {
    	step(4, "step4", 4000);
    }); 
    
    $('#eainformation5').click(function () {
    	step(5, "step5", 4000);
    }); 
    
    function valueDefault(id) {
    	$('.next-step').attr("disabled", true);
		$('.prep-step').attr("disabled", true);
		
    	$('#progress-bar'+id).css('width','0%');
    	$('#progress-bar'+id).text('0%');
		$('#progress-value'+id).css('width', '0%');
		$('#status_'+id).text("Starting");
		$('#processing_'+id).text("0 - 0");
		$('#success_'+id).text("0");
		$('#fail_'+id).text("0");
		$('#error_'+id).text("0");
	}
    
    $(".next-step").click(function (e) {
    	var id = parseInt($(this).attr('title'));
    	$(".tab"+id).removeClass('active');
    	$("#tab"+id).fadeOut( "slow", function() {
    		id+=1;
    		$(".tab"+id).addClass('active');
    		$("#tab"+id).fadeIn( "slow");
    		
    		valueDefault(id);
    	});
    });
    
    $(".prep-step").click(function (e) {
    	var id = parseInt($(this).attr('title'));
    	$(".tab"+id).removeClass('active');
    	$("#tab"+id).fadeOut( "slow", function() {
    		id-=1;
    		$(".tab"+id).addClass('active');
    		$("#tab"+id).fadeIn( "slow");
    		
    		valueDefault(id);
    	});
    });
});