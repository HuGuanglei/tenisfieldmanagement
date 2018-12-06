function adminBookSpaceNow() {
	var start = $("#startTimeMuch").val();
	var end = $("#endTimeMuch").val();
	if(start == "" || end == ""){
		alert("时间不能为空！");
		return;
	}
	if (start.split(" ")[0] != end.split(" ")[0]) {
		alert("只能选择同一天的时间段！");
		return;
	}
	var endTime = end.split(" ")[1].split(":")[0];
	var startTime = start.split(" ")[1].split(":")[0];
	if (endTime > 22 || endTime < 8 || startTime < 8 || startTime > 22) {
		alert("只能选择8点到22点的时间段！");
		return;
	}
	var courtName = $("#selectCourtName").val();
	var courtId = $("#courtIdSelect").val();
	if (courtName == "" || courtName == "请选择" || courtId == "") {
		alert("请完整填写选项");
		return;
	}

	$.ajax({
		type : "post",
		url : "api/book",
		contentType : 'application/json',
		data : JSON.stringify({
			"userName" : $("#userNameCourtSelect").val(),
			"courtName" : courtName,
			"courtId" : courtId,
			"startTime" : $("input[name='startTime']").val(),
			"endTime" : $("input[name='endTime']").val()
		}),
		dataType : "json",
		success : function(data) {
			$("#errorBookDisplayDiv").show();
			$("#errorBookMsgDisplay").html(data.errorMsg);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorBookDisplayDiv").show();
			$('#errorBookMsgDisplay').html("预订失败！");
		}
	});
}

function queryAllAccountDetail(){
	var transDate = $("#transDate").val();
	$.ajax({
		type : "post",
		url : "admin/accountDetail",
		data : {
			"transDate" : transDate
		},
		dataType : "json",
		success : function(data) {
			if(data == null){
				return;
			}
			if(transDate == null || transDate == "" ){
				transDate = GetDateStr(0);
			}
			var trHTML = "<caption>" + transDate + "日明细</caption><tr><td>时间</td><td>类型</td><td>金额</td><td>状态</td><td>操作</td></tr>";
			for (var i = 0; i < data.length; i++) {
				var status = "正常";
				var revocDisp = "<a href='javascript:consumeRevoc("+ data[i].id +");'>撤销</a>";
				if(data[i].flag != null && data[i].flag != "0"){
					status = "已撤销";
					revocDisp = "";
				}
				var typeDisp = "充值";
				if(data[i].transType != null){
					if(data[i].transType == "1002"){
						typeDisp = "消费";
					}else if(data[i].transType == "1003"){
						typeDisp = "转账";
					}else if(data[i].transType == "1004"){
						typeDisp = "销户";
					}
					
				}
				trHTML += "<tr><td>" + data[i].transTime + "</td><td>"
						+ typeDisp + "</td><td>"
						+ data[i].transAmt + "</td><td id='curConsumeRevocIndex"+ data[i].id + "'>"
						+ status + "</td><td>" 
						+ revocDisp + "</td>"
						+ "</tr>";
			}
			$("#detailConsumeTbl").html(trHTML);// 在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorConsumeDisplayDiv").show();
			$('#errorConsumeMsgDisplay').html("加载明细失败!");
		}
	});
}

function consumeRevoc(id){
	$.ajax({
		type : "post",
		url : "admin/consumeRevoc",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if(data !=null && data.errorCode == "200"){
				$('#curConsumeRevocIndex' + id).html("已撤销");
			}else {
				$("#errorConsumeDisplayDiv").show();
				$("#errorConsumeMsgDisplay").html(data.errorMsg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorConsumeDisplayDiv").show();
			$("#errorConsumeMsgDisplay").html("撤销失败！");
		}
	});
}

function selectConsumeSpace(){
	$("#detailConsumeTbl").html($("#detailConsumeHideTbl").html());
}

function selectConsumeDetailSpace(){
	queryAllAccountDetail();
}

function chargeConsume(){
	var userNameWithMoney = $("#userNameWithMoney").val();
	var moneyWithMoney = $("#moneyWithMoney").val();
	var remarkWithMoney = $("#remarkWithMoney").val();
	$.ajax({
		type : "post",
		url : "admin/chargeConsume",
		contentType: 'application/json',
		data : JSON.stringify({
			"transAmt" : moneyWithMoney,
			"transType" : "1001",
			"userName" : userNameWithMoney,
			"remark" : remarkWithMoney
		}),
		dataType : "json",
		success : function(data) {
			$("#errorConsumeDisplayDiv").show();
			$("#errorConsumeMsgDisplay").html(data.errorMsg);
			if(data.errorCode == "200"){
				 $("#userNameWithMoney").val("");
				 $("#moneyWithMoney").val("");
				 $("#remarkWithMoney").val("");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorConsumeDisplayDiv").show();
			$("#errorConsumeMsgDisplay").html("充值失败！");
		}
	});
}

function consumeConsume(){
	var userNameWithMoney = $("#userNameNeedMoney").val();
	var moneyWithMoney = $("#moneyNeedMoney").val();
	var remarkWithMoney = $("#remarkNeedMoney").val();
	$.ajax({
		type : "post",
		url : "admin/consumeConsume",
		contentType: 'application/json',
		data : JSON.stringify({
			"transAmt" : moneyWithMoney,
			"transType" : "1002",
			"userName" : userNameWithMoney,
			"remark" : remarkWithMoney
		}),
		dataType : "json",
		success : function(data) {
			$("#errorConsumeDisplayDiv").show();
			$("#errorConsumeMsgDisplay").html(data.errorMsg);
			if(data.errorCode == "200"){
				 $("#userNameNeedMoney").val("");
				 $("#moneyNeedMoney").val("");
				 $("#remarkNeedMoney").val("");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorConsumeDisplayDiv").show();
			$("#errorConsumeMsgDisplay").html("消费失败！");
		}
	});
}

function selectCourtInfo(){
	
	$.ajax({
		type : "post",
		url : "api/courtList",
		data : {
		},
		dataType : "json",
		success : function(data) {
			var trHTML = "<tr><td>场地名称</td><td>场地编号</td><td>创建时间</td><td>操作</td></tr>";
			for(var i =0;i<data.length;i++){
				trHTML += "<tr id='curCourtListIndex"+ data[i].courtId + "'><td>" + data[i].courtName + "</td><td>"
					+ data[i].courtId + "</td><td>"
					+ data[i].createDate + "</td><td>" 
					+ "<a href='javascript:deleteCourt(" + data[i].courtId + ",\"curCourtListIndex"+ data[i].courtId +"\");'>删除</a>" + "</td>"
					+ "</tr>";
			}
			$("#detailTbl").html(trHTML);//在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function deleteCourt(courtId,obj){
	$.ajax({
		type : "post",
		url : "admin/deleteCourt",
		data : {
			"courtId":courtId
		},
		dataType : "json",
		success : function(data) {
			if(data !=null && data.errorCode == "200"){
				$('#' + obj).remove();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function addCourtNow(){
	var courtName = $("#courtNameAdd").val();
	var courtId = $("#courtIdAdd").val();
	if(courtName == ""){
		alert("场地不能为空");
		return;
	}
	if(courtId == ""){
		alert("场地编号不能为空");
		return;
	}
	$.ajax({
		type : "post",
		url : "admin/addCourt",
		contentType: 'application/json',
		data : JSON.stringify({
			"courtName" : courtName,
			"courtId" : courtId
		}),
		dataType : "json",
		success : function(data) {
			$("#errorDisplayDiv").show();
			$("#errorMsgDisplay").html(data.errorMsg);
			if(data.errorCode =="200"){
				$("#courtNameAdd").val("");
				$("#courtIdAdd").val("");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorDisplayDiv").show();
			$('#errorMsgDisplay').html("添加场地失败！");
		}
	});
}

function selectAllBookSpace(){
	$.ajax({
		type : "post",
		url : "admin/bookList",
		data : {
			"bookTime" : $("#bookTime").val()
		},
		dataType : "json",
		success : function(data) {
			var trHTML = "<tr><td>起始时间</td><td>结束时间</td><td>场地名称</td><td>场地编号</td><td>操作</td></tr>";
			for(var i =0;i<data.length;i++){
				trHTML += "<tr id='curIndex"+ data[i].id + "'><td>" + data[i].startTime + "</td><td>"
					+ data[i].endTime + "</td><td>" 
					+ data[i].courtName + "</td><td>" 
					+ data[i].courtId + "</td><td>" 
					+ "<a href='javascript:deleteBook(" + data[i].id + ",\"curIndex"+ data[i].id +"\");'>删除</a>" + "</td>"
					+ "</tr>";
			}
			$("#detailBookTbl").html(trHTML);//在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function selectUserInfo(){
	var text = $("#userSelectText").val();
	$.ajax({
		type : "post",
		url : "admin/users",
		data : {
			"userName": text
		},
		dataType : "json",
		success : function(data) {
			var trHTML = "<tr><td>用户名</td><td>姓名</td><td>邮箱</td><td>手机号</td><td>账户</td><td>操作</td></tr>";
			for(var i =0;i<data.length;i++){
				trHTML += "<tr id='curUserIndex"+ data[i].userName + "'><td>" + data[i].userName + "</td><td>"
					+ data[i].name + "</td><td>" 
					+ data[i].emailAddr + "</td><td>" 
					+ data[i].mobile + "</td><td>" 
					+ "<a href='javascript:getAccountInfo(\"" + data[i].userName + "\");'>详情</a>&nbsp;&nbsp;<a href='javascript:openAccount(\"" + data[i].userName + "\");'>开户</a></td><td>" 
					+ "<a href='javascript:editUser(\"" + data[i].userName + "\",\"" + data[i].name + "\",\"" + data[i].emailAddr + "\",\"" + data[i].mobile + "\");'>编辑</a>&nbsp;&nbsp;" 

					+ "<a href='javascript:deleteUser(\"" + data[i].userName + "\",\"curUserIndex"+ data[i].userName +"\");'>删除</a>" + "</td>"
					+ "</tr>";
			}
			$("#detailUsersTbl").html(trHTML);//在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function getAccountInfo(userName){
	$.ajax({
		type : "post",
		url : "admin/accountInfo",
		data : {
			"userName" : userName
		},
		dataType : "json",
		success : function(data) {
			if(data == null){
				$("#errorUsersDisplayDiv").show();
				$('#errorUsersMsgDisplay').html("暂无账户信息，请检查用户名是否正确或该用户是否已正常开户！");
				return;
			}
			$("#editAccountInfoDisplayDiv").show();

			var lockFlag = data.lockFlag;
			var status = data.status;
			var accountDisp = "正常";
			if(status != null && status == "2" ) {
				accountDisp = "已销户";
			}
			if(lockFlag != null && lockFlag == "1" ) {
				accountDisp = "已锁定";
			}
			var type = "个人账户";
			if(data.accType != null && data.accType != "100" ) {
				type = "系统账户";
			}
			$("#accountUserName").val(data.userName);
			$("#accountType").val(type);
			$("#accountOpenTime").val(data.openTime);
			$("#accountStatus").val(accountDisp);
			$("#accountBal").val(data.bal);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUsersDisplayDiv").show();
			$('#errorUsersMsgDisplay').html("账户信息查询失败!请检查用户名是否正确或该用户是否已正常开户！");
		}
	});
	
}

function openAccount(userName){
	$.ajax({
		type : "post",
		url : "admin/openAccount",
		data : {
			"userName" : userName
		},
		dataType : "json",
		success : function(data) {
			$("#errorUsersDisplayDiv").show();
			$('#errorUsersMsgDisplay').html(data.errorMsg);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUsersDisplayDiv").show();
			$('#errorUsersMsgDisplay').html("开户失败!");
		}
	});
	
}

function closeAccountWithAdmin(){
	var userName = $("#accountUserName").val();
	if(userName == null || userName == "")return;
	$.ajax({
		type : "post",
		url : "admin/checkAccount",
		data : {
			"userName" : userName
		},
		dataType : "json",
		success : function(data) {
			if(data == null){
				return;
			}
			if(data.errorCode == "400"){
				var mymessage = confirm(data.errorMsg);
				if(mymessage == true){
					$.ajax({
						type : "post",
						url : "admin/closeAccount",
						data : {
							"userName" : userName
						},
						dataType : "json",
						success : function(data) {
							if(data == null){
								return;
							}	
							$("#editAccountInfoDisplayDiv").hide();
							$("#errorUsersDisplayDiv").show();
							$('#errorUsersMsgDisplay').html(data.errorMsg);
							
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							$("#editAccountInfoDisplayDiv").hide();
							$("#errorUsersDisplayDiv").show();
							$('#errorUsersMsgDisplay').html("销户失败!");
						}
					});
				}		
			}else{
				$("#editAccountInfoDisplayDiv").hide();
				$("#errorUsersDisplayDiv").show();
				$('#errorUsersMsgDisplay').html(data.errorMsg);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#editAccountInfoDisplayDiv").hide();
			$("#errorUsersDisplayDiv").show();
			$('#errorUsersMsgDisplay').html("销户失败!");
		}
	});
	
}

function deleteUser(userName,obj){
	$.ajax({
		type : "post",
		url : "admin/deleteUser",
		data : {
			"userName":userName
		},
		dataType : "json",
		success : function(data) {
			if(data !=null && data.errorCode == "200"){
				$('#' + obj).remove();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function consumeStatistics(){
	$.ajax({
		type : "post",
		url : "admin/consumeStatistics",
		data : {
			
		},
		dataType : "json",
		success : function(data) {
			$("#allDeposit").val(data.errorCode);
			$("#allIncome").val(data.errorMsg);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}

function editUser(userName,name,emailAddr,mobile){
	$("#editUserDisplayDiv").show();
	$("#editUserName").val(userName);
	$("#editName").val(name);
	$("#editEmail").val(emailAddr);
	$("#editMobile").val(mobile);
}



function addUserNow(){
	var userName = $("#newUserName").val();
	var passwd = $("#newPasswd").val();
	var name = $("#newName").val();
	var emailAddr = $("#newEmail").val();
	var mobile = $("#newMobile").val();
	
	$.ajax({
		type : "post",
		url : "admin/addUser",
		contentType: 'application/json',
		data : JSON.stringify({
			"userName" : userName,
			"passwd" : passwd,
			"name" : name,
			"emailAddr" : emailAddr,
			"mobile" : mobile
		}),
		dataType : "json",
		success : function(data) {
			$("#errorUsersDisplayDiv").show();
			$("#errorUsersMsgDisplay").html(data.errorMsg);
			if(data.errorCode =="200"){
				$("#newUserName").val("");
				$("#newPasswd").val("");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	});
}