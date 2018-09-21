$(function() {
	$('#changePassword').click(function () {
		$('input[name=password]').removeAttr('ellipsis');
		$('input[name=newPassword]').removeAttr('ellipsis');
		$('input[name=repeatPassword]').removeAttr('ellipsis');
		
		$('#update').removeAttr('disabled');
		$('#changeInformation, #changePassword').attr('disabled', 'disabled');
		
		$('#option').val('1');
	});
	
	$('#changeInformation').click(function () {
		$('input[name=fullName]').removeClass('ellipsis');
		$('input[name=birth]').removeClass('ellipsis');
		$('input[name=email]').removeClass('ellipsis');
		
		$('#update').removeAttr('disabled');
		$('#changeInformation, #changePassword').attr('disabled', 'disabled');
		
		$('#option').val('2');
	});
});
