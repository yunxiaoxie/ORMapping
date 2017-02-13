<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html>
<html>
  <%@ include file="page/head.jsp"%>
  
  <body>
	<%@ include file="page/north.jsp"%>

    <div class="container-fluid">
      <div class="row">
        <%@ include file="page/west.jsp"%>
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
          <h1 class="page-header">Access Control List Manager</h1>
          <div id="toolbar" class="btn-group">
		    <button type="button" class="btn btn-default">
		        <i class="glyphicon glyphicon-plus"></i>
		    </button>
		    <button type="button" class="btn btn-default">
		        <i class="glyphicon glyphicon-heart"></i>
		    </button>
		    <button type="button" class="btn btn-default">
		        <i class="glyphicon glyphicon-trash"></i>
		    </button>
		    <button type="button" class="btn btn-default" style="text-shadow: black 3px 3px 3px;">
		        <i class="glyphicon glyphicon-pencil"></i>
		    </button>
		  </div>
		  <table id="table" 
		  		data-classes="table table-hover table-condensed"
       			data-striped="true"
       			data-search="true"
       			data-toolbar="#toolbar"
       			data-checkbox-header=false
		        data-show-refresh="true"
		        data-show-toggle="true"
       			data-show-columns="true">
		    <thead>
		    <tr>
		        <th class="col-xs-2" data-field="moduleName" data-align="center">模块</th>
		        <th class="col-xs-1" data-field="extend" data-align="center">Extend</th>
		        <th class="col-xs-1" data-field="moduleUrl" data-align="center">Url</th>
		    </tr>
		    </thead>
		  </table>
        </div>
      </div><!-- row -->
    </div>
    <script>
    $(function () {
    	$.get("/getModuleByUser", function(datastr){
    		if (datastr) {
    			//console.log(datastr);
    			var newData = translate(datastr);
    			var powers = _.pluck(newData[0].powers, 'powerName');
    			buildHtml(newData);
    			console.log(newData);
    			var $table = $('#table');
	    		$table.bootstrapTable({
	                onClickCell: function(field, value, row, $element){
	                	if (_.indexOf(powers, field) != -1) {
		            		console.log(field, value, row, $element);
		            		var result = value === true ? false : true;
		            		//$element.find('input').attr('checked', result);
		            		$table.bootstrapTable('updateCell', {index: _.indexOf(newData, row), field: field, value: result});
		            		updatePermission(row.moduleId, field, result);
	                	}
		            }
	            });
	    		$table.bootstrapTable('load', newData);
    		}
    	});
    	
    	// 更新权限
    	function updatePermission(mId, permission, yes){
    		$.post('/updatePermission', {moduleId: mId, permission: permission, yesno: yes}, function(data){
    			if (data) {
    				var result = JSON.parse(data);
    				if (result.success) {
    					alert('update permission successfully.');
    				}
    			}
    		});
    	}
    	
    	// 动态生成html列
    	function buildHtml(data) {
    		var nameArr = _.pluck(data[0].powers, 'powerName');
    		var html = '';
    		_.each(nameArr, function(el, index, list){
    			html+= "<th data-checkbox=false data-field='"+el+"' data-align='center'>"+el+"</th>";
    		});
    		$("#table tr").append(html);
    	}
        
    	// 将属性列表power移到根节点
    	function translate(data) {
    		var d = JSON.parse(data);
    		_.each(d, function(el, index, list){
    			if (el && el.powers && el.powers.length > 0) {
    				var nameArr = _.pluck(el.powers, 'powerName'), boolArr = _.pluck(el.powers, 'powerBool');
    				var obj = _.object(nameArr, boolArr);
    				el = _.extend(el, obj);
    				//delete el.powers;
    			}
    		});
    		return d;
    	}
    });
    </script>
  </body>
</html>