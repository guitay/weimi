<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/includes/includeTags.jspf"%>
<HTML>
	<HEAD>
		<TITLE>维秘报销单查询</TITLE>
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
				var bxdh=sel["bxdh"];
				//alert(bxdh);
				custom_window('../finDept/finDept.do?method=loadZD&bxdh='+bxdh,1000,500,'账单详情');
				
			}
			function audit2(sel,isApproved){
				var procinstid=sel["procinstid"];
				custom_window('../finDept/finDept.do?method=loadLC&procinstid='+procinstid,1000,500,'报销流程');
				
			}
			
			function customFastLink($table,row){//this 对象绑定当前行的单元格

					var edit1 = $('<a class="operation_edit LIST_BAR" title="">账单详情</a>');
					var edit2 = $('<a class="operation_edit LIST_BAR" title="">流程详情</a>');
	
					this.append(edit1).append(' ').append(edit2);
					edit1.click(function(){
						audit($table.datagrid("getRows")[$(this).parent().parent().parent().attr("datagrid-row-index")],true);
					});
					edit2.click(function(){
						audit2($table.datagrid("getRows")[$(this).parent().parent().parent().attr("datagrid-row-index")],false);
					});
				return true;
			}
		</script>
	</HEAD>
	<BODY>
		<div id="fun_panel">
			<div id="fun_title">
				<h2>
					维秘财务报销
				</h2>
			</div>
			<div id="query_conn_panel">
				<html:form action="/finDept.do?method=getTask" method="post">
					<table id="query_table">
						<tr>
						
							<td>
								报销单号
							</td>	
							<td>
								<html:text property="bxdh" style="width:120px" />
							</td>
							<!-- 
							<td>
								项目简称
							</td>	
							<td>
								<select name="xmbh" id="xmbh" style="width:120px" >
									<option value="">全部..</option>
									<c:forEach var="xm" items="${xmList }">
										<option value="${xm.xmbh }">${xm.xmmc }</option>
									</c:forEach>
								</select>
							</td> -->
							<td>
								报销部门
							</td>	
							<td align="left">
								<input type="text" id="jgkhdxdh_mc" name="jgkhdxdh_mc" style="width:120px;" class=" jg_bg_img button" readonly="true"/>
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
							<td>人员</td>
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
							<td>
								&nbsp;&nbsp;<input type="button" class="cbutton" value="查 询" onClick="doSubmit()" />
							</td>
						</tr>
					</table>
				</html:form>
			</div>
			
			<div id="data_panel">
				<pastag:list key="audit" url="finDept.do?method=getTask" type="manage"></pastag:list>
			</div>
		</div>
	</BODY>
	<script type="text/javascript">
	 function loadData2()
	 {
	 	 if(typeof witing =='function') witing(); 
		 var params = jQuery("form[name=finDeptForm]").serializeJSON();
		 jQuery('#auditList').datagrid({pageNumber:1,url: 'finDept.do?method=getTask',queryParams:params});
	 }
	
	</script>
</HTML>
