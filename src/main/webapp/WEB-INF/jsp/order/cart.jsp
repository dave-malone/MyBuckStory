<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>

<h2>Your Cart</h2>

<c:choose>
	<c:when test="${not empty cart}">
		<table style="width:100%" cellspacing="0" cellpadding="5" border="1px solid black">
			<thead>
				<tr>
					<th>&nbsp;<!-- image --></th>
					<th>Item Description</th>
					<th>Quantity</th>
					<th>Unit Price</th>
					<th>Total</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cart}" var="orderItem">
					<tr>
						<td>
							<a href="${itemUrl}" title="View ${orderItem.item.name}">
								<mbs:imageUri id="itemImageUri" imageId="${orderItem.item.image.id}" maxWidthAndHeight="100"/>
								<img align="middle" alt="${orderItem.item.name}" src="${itemImageUri}" />
							</a>
						</td>
						<td>${orderItem.item.name}</td>
						<td>
							<input type="text" class="orderItemQuantity" name="${orderItem.item.id}.quantity" value="${orderItem.quantity}" />
						</td>
						<td>${orderItem.item.salePrice}</td>
						<td id="${orderItem.item.id}.total">${orderItem.total}</td>
						<td>
							<a href="<c:url value="/sellableItem/removeFromCart/${orderItem.item.id}?KeepThis=true&TB_iframe=true&height=100&width=350" />" class="thickbox" title="Remove ${orderItem.item.name} from Cart">Remove</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>Subtotal: <span id="orderSubTotal">${subTotal}</span></div>
		
		<div><button>Checkout</button></div>
	</c:when>
	<c:otherwise>
		Your cart is empty.  Please browse our <a href="<c:url value="/store" />">Store</a> to add items to your cart
	</c:otherwise>
</c:choose>