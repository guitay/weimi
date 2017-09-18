<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/includeTags.jspf"%>
<HTML>
	<HEAD>
		<TITLE>账单详情</TITLE>
		<%@ include file="/includes/base_css.jspf"%>
		<%@ include file="/includes/base_lib.jspf"%>
		<%@ include file="/includes/data_grid_resize.jspf"%>
		<style type="text/css">
			 tr{height:22px;}
			 td{padding: 3px 4px;}
		</style>
		</HEAD>
	<BODY>
		<div id="fun_panel">
			<table  width='100%' border='1' cellpadding='0' cellspacing='1' contentEditable='false'>
				<tr contentEditable='false'><td width='25%'>报销单号</td><td width='25%'>账单金额</td><td width='25%'>日期</td><td width='25%'>账单说明</td></tr>
					<c:forEach var="lc" items="${zd}">
					<tr>
					<td>${lc.bxdh}</td>
					<td>${lc.zdje}</td>
					<td>${lc.rq}</td>
					<td>${lc.zdsm}</td>
					</tr>
					</c:forEach>
				</tr>
			</table>
		</div>
		</BODY>
</HTML>
