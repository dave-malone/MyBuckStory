<%@ include file="/WEB-INF/jsp/common/taglibs.inc" %>
<html>	
<head>

	<script type="text/javascript" src="<c:url value="/scripts/jquery-1.4.2.min.js" />"></script>
	<script type="text/javascript" src="<c:url value="/scripts/ecommerce.js" />"></script>
</head>
<body>
	<div>
		<form id="addItemToCartForm" action="<c:url value="/order/cart/${item.id}" />">
			<table>
				<tr><td>Item:</td>
					<td>${item.name}</td></tr>     
				<tr><td>Quantity:</td>
					<td><input type="text" name="quantity" value="1"/></td></tr>			
			</table>
			<input type="submit" name="submit" id="submitButton" value="Add to Cart"/>
		</form>
		
		<p id="postAddItemToCartActions" style="display:none;">
			${item.name} has been added to your cart<br /><br />
			<a href="" onclick="self.parent.refresh('<c:url value="/order/cart" />');self.parent.tb_remove();">View Cart</a><br /><br />
			<a href="" onclick="self.parent.refresh();self.parent.tb_remove();">Continue Shopping</a>
		</p>		
	</div>
</body>
</html>