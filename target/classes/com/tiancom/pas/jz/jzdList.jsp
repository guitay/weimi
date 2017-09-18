<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/includeTags.jspf"%>
<HTML>
	<HEAD>
		<TITLE>借支单查询</TITLE>
		<%@ include file="/includes/base_css.jspf"%>
		<%@ include file="/includes/base_lib.jspf"%>
		<%@ include file="/includes/data_grid_resize.jspf"%>
		<SCRIPT src="<c:url value='/pasplus/runing/js/public.js'/>"></SCRIPT>
		<script src="<c:url value='/pasplus/designer/js/util.js'/>"></script>
		<script type="text/javascript">
			function doSubmit(){
			   if(validate()){
				  loadData2();
			   }
			}
			
			function validate() { 
				return $("form").validate().form();
			}
			

			function audit(sel,isApproved){
				var procinstid=sel["procinstid"];
				//alert(procinstid);
				custom_window('../jz/jz.do?method=loadLC&procinstid='+procinstid,1000,500,'借支流程');
				/*if(isApproved){
					msg = "您确定'审批同意'该报销单吗？";
				}else{
					msg= "您确定‘审批不同意’该报销单吗？";
				}
				custom_confirm(msg,null,function(){
					$.post("../finDept/finDept.do?method=completeTask", {taskId:taskId,approved:isApproved},function(callback){
						 loadData2();
					});	
				});*/
			}
			
			function customFastLink($table,row){//this 对象绑定当前行的单元格

					var edit1 = $('<a class="operation_edit LIST_BAR" title="详情">详情</a>');
					this.append(edit1);
					edit1.click(function(){
						audit($table.datagrid("getRows")[$(this).parent().parent().parent().attr("datagrid-row-index")],true);
					});
				return true;
			}
		</script>
	</HEAD>
	<BODY>
		<div id="fun_panel">
			<div id="fun_title">
				<h2>
					借支单查询
				</h2>
			</div>
			<div id="query_conn_panel">
				<html:form action="/jz.do?method=queryByCondition" method="post">
					<table id="query_table">
						<tr>
						
							<td>
								借支单号
							</td>	
							<td>
								<html:text property="jzdh" style="width:120px" />
							</td>
							
<%--							<td>--%>
<%--								项目简称--%>
<%--							</td>	--%>
<%--							<td>--%>
<%--								<select name="xmbh" id="xmbh" style="width:120px" >--%>
<%--									<option value="">全部..</option>--%>
<%--									<c:forEach var="xm" items="${xmList }">--%>
<%--										<option value="${xm.xmbh }">${xm.xmmc }</option>--%>
<%--									</c:forEach>--%>
<%--								</select>--%>
<%--							</td>--%>
							<td>
								部门
							</td>	
							<td align="left">
								<input type="text" id="jgkhdxdh_mc" name="jgkhdxdh_mc" style="width:120px;" class="jg_bg_img button" readonly="true"/>
								<input type="hidden" name="isAsync" id="isAsync" value="false">
								<input type="hidden" name="jgkhdxdh" id="jgkhdxdh"/>
								<input type="hidden" name="crKey" id="crKey" value="jg">
								<input type="hidden" name="jgkhdxdh_tjrq" class="tjrqUUID">
								<input type="hidden" name="jgkhdxdh_dh" id="jgkhdxdh_dh"/>
								<script>
								jQuery("#jgkhdxdh_mc").simplejg({url:'../studio/tag.do?method=toBmsByRightData&crKey=jg',
									autoFix:false,multiple:true,
									submitOpts:[{name:'jgkhdxdh',value:'khdxdh'},
												{name:'jgkhdxdh_dh',value:'jgdh'}]}); 
								</script>
							</td>
							<td>借支人</td>
							<td align="left">
								<input type="text" name="khdxdh_mc" id="khdxdh_mc" style="width:120px;" class="hy_bg_img button" readonly="true"/>
								<input type="hidden" name="isAsync" id="isAsync" value="false">
								<input type="hidden" name="khdxdh" id="khdxdh"/>
								<input type="hidden" name="khdxdh_dh" id="khdxdh_dh"/>
								<input type="hidden" name="khdxdh_tjrq" class="tjrqUUID">
								<input type="hidden" name="crKey" id="crKey" value="jg_ckhy">
								<script>

							 	jQuery('#khdxdh_mc').multipleHy({
							 		jgurl:'../studio/tag.do?method=toBmsByRightData&crKey=jg_ckhy',
									hyurl:'../studio/tag.do?method=loadUserByBankId2&parents=jgkhdxdh&SQLID=studio.selectJgListBySjJgdh&crKey=jg_ckhy',
									multiple:false,
									submitOpts:[{name:'khdxdh',value:'khdxdh'},{name:'khdxdh_dh',value:'hydh'}],
									isDisplayXN:true,
									SQLID_search:'studio.selectJgListBySjJgdh.search'}); 
								</script>
							</td>
							<!-- 
							
							<td>审核状态</td>
							<td align="left">
								<select id="czzt" name="czzt">
									<option value="1">待审核</option>
									<option value="2">已审核</option>
								</select>
							</td>
							 -->
							<td>
								&nbsp;&nbsp;<input type="button" class="cbutton" value="查 询" onClick="doSubmit()" />
							</td>
						</tr>
					</table>
				</html:form>
			</div>
			
			<div id="data_panel">
				<pastag:list key="jzd" url="jz.do?method=queryByCondition" type="manage"></pastag:list>
			</div>
		</div>
	</BODY>
	<script type="text/javascript">
	 function loadData2()
	 {
	 	 if(typeof witing =='function') witing(); 
		 var params = jQuery("form[name=jzForm]").serializeJSON();
		 jQuery('#jzdList').datagrid({pageNumber:1,url: 'jz.do?method=queryByCondition',queryParams:params});
	 }
	
	</script>
</HTML>
