<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.threescoops.vo.Product"%>
<%@page import="java.util.Date"%>
<%@page import="com.threescoops.vo.OrderLine"%>
<%@page import="com.threescoops.vo.OrderInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int status = (Integer)request.getAttribute("status");
%>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>3Scoops-주문목록</title>
	<link href="../css/orderlist.css" rel="stylesheet" type="text/css">
</head>
<body>
			<%
			if(status == 1){
				List<OrderInfo> list = (List)request.getAttribute("orderlist");
				//JSTL문법 : <c:set var="list" value="${requestScope.orderList}"></c:set>
			%>	
	<div class="orderList">
		<table style="border:1px solid; width:70%;" border="1">
			<tr>
				<td>주문번호</td>
				<td>주문일자</td>
				<td>상품번호</td>
				<td>상품명</td>
				<td>가격</td>
				<td>주문수량</td>
			</tr>
					<%
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							for(OrderInfo info: list){
							//JSTL문법: <c:forEach var="info" item="list"> 
								int orderNo = info.getOrderNo(); //주문번호
							//JSTL문법: <c:set  var="orderNo" value="${info.orderNo}"/>	
								//Date orderDt = info.getOrderDt();//주문일자
								Date dt = info.getOrderDt();
								String orderDt = sdf.format(dt);
								
							//JSTL문법: <c:set var="orderDt"  value="${info.orderDt}"/>	
								List<OrderLine> lines = info.getLines();//주문상세	
					%>			
			<tr>
	            <td rowspan="<%=lines.size()%>"><%=orderNo%></td>
	            <td rowspan="<%=lines.size()%>"><%=orderDt %></td>
				
					<%			for(int i=0; i<lines.size(); i++){//OrderLine line: lines){
									if(i > 0){
					%>		
				
					<%				}
									OrderLine line = lines.get(i);
									Product p = line.getOrderP(); //주문상품
									String prodNo = p.getProdNo();
									String prodName = p.getProdName();
									int prodPrice = p.getProdPrice();
									
									int quantity = line.getOrderQuantity();//주문수량
					%>		
	             <td><%=prodNo %></td><td><%=prodName %></td><td><%=prodPrice %></td><td><%=quantity %></td>
	        </tr>
					<%
								}
							}
					%>	
		</table>
	</div>
			<%
			}else if(status == 0){
				String msg = (String)request.getAttribute("msg");
			%>
		<script>
			alert('<%=msg%>')
		</script>	
			<%
			}
			%>
</body>
</html>