$(document).ready(function(){
	$('#addItemToCartForm').submit(function(){				
		var url = $(this).attr('action') + "?" + $(this).serialize()	
		$.ajax({
			type: "PUT",
			url: url,
			success: function(data){
				$('#addItemToCartForm').toggle()
				$('#postAddItemToCartActions').toggle()
			}
		});					
		
		return false
	})
	
	$('.orderItemQuantity').change(function(){				
		var quantity = $(this).val()
		var inputName = $(this).attr('name')
		var itemId = inputName.split('.')[0]
		
		
		if(quantity == '' || quantity == '0'){
			alert('Do you wish to remove this item from your cart?')
		}
		
		$.ajax({
			type: "POST",
			url: '/order/cart/update',
			success: function(data){
				//TODO - update lineItemTotal and orderSubTotal
				var lineItemTotal = $('#' + itemId + '.total').val(data.orderItemTotal)
				var orderSubTotal = $('#orderSubTotal').val(data.orderSubTotal)
			}
		});					
		
		return false
	})
	
});

