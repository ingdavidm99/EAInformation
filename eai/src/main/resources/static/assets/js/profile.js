$(function() {
	$('#changeInformation').click(function () {
		logicCheckbox($(this))
	});
	
	$('#changePassword').click(function () {
		logicCheckbox($(this))
	});
	
	function logicCheckbox(ele) {
		if($(ele).attr('id') === 'changeInformation'){
			$('input[name=password]').attr('readonly', 'readonly');
			$('input[name=newPassword]').attr('readonly', 'readonly');
			$('input[name=repeatPassword]').attr('readonly', 'readonly');
			
			$('input[name=fullName]').removeAttr('readonly');
			$('input[name=birth]').removeAttr('readonly');
			$('input[name=email]').removeAttr('readonly');
			
			$('#option').val('1');
		}else{
			$('input[name=fullName]').attr('readonly', 'readonly');
			$('input[name=birth]').attr('readonly', 'readonly');
			$('input[name=email]').attr('readonly', 'readonly');
			
			$('input[name=password]').removeAttr('readonly');
			$('input[name=newPassword]').removeAttr('readonly');
			$('input[name=repeatPassword]').removeAttr('readonly');
			
			$('#option').val('2');
		}
		
		
		if($(ele).is(':checked'))
			$('#update').removeAttr('disabled');
		else
			$('#update').attr('disabled', 'disabled');
	}
});
