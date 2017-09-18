<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/includeTags.jspf"%>
<HTML>
	<HEAD>
		<TITLE>报销流程</TITLE>
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
				<tr contentEditable='false'><td width='20%'>操作类型</td><td width='20%'>起始时间</td><td width='20%'>结束时间</td><td width='20%'>操作人</td><td width='20%'>备注</td></tr>
					<c:forEach var="lc" items="${lc}">
					<tr>
					<td>${lc.czlx}</td>
					<td>${lc.qssj}</td>
					<td>${lc.jssj}</td>
					<td>${lc.czr}</td>
					<td>${lc.bz}</td>
					</tr>
					</c:forEach>
				</tr>
			</table>
		</div>
		</BODY>
</HTML>
