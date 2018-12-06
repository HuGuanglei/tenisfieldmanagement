function bookSpaceNow() {
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
function selectBookSpace() {
	$
			.ajax({
				type : "post",
				url : "api/bookList",
				data : {
					"bookTime" : $("#bookTime").val()
				},
				dataType : "json",
				success : function(data) {
					var trHTML = "<tr><td>起始时间</td><td>结束时间</td><td>场地名称</td><td>场地编号</td></tr>";
					for (var i = 0; i < data.length; i++) {
						trHTML += "<tr><td>" + data[i].startTime + "</td><td>"
								+ data[i].endTime + "</td><td>"
								+ data[i].courtName + "</td><td>"
								+ data[i].courtId + "</td>" + "</tr>";
					}
					$("#detailBookTbl").html(trHTML);// 在table最后面添加一行
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {

				}
			});
}

function deleteBook(id, obj) {

	$.ajax({
		type : "post",
		url : "admin/deleteBook",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(data) {
			if (data != null && data.errorCode == "200") {
				$('#' + obj).remove();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function dateStartTime() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH',
		minDate : '%y-%M-%d %H:00:00'
	});
}
function dateEndTime() {
	WdatePicker({
		dateFmt : 'yyyy-MM-dd HH',
		minDate : '#F{$dp.$D(\'startTimeMuch\',{H:1})}',
		maxDate : '#F{$dp.$D(\'startTimeMuch\',{H:13})}'
	});
}

function selectCourtRealTime(){
	var start = $("#startTimeMuch").val();
	var end = $("#endTimeMuch").val();
	
	$("#selectCourtName").empty();
	$("#selectCourtName").append(
			"<option>请选择</option>");

	$.ajax({
		type : "post",
		url : "api/avaiCourtList",
		data : {
			"startTime" : start,
			"endTime" : end
			},
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {	
				$("#selectCourtName").append(
						"<option>" + data[i].courtName + "</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function initCourtInfo() {
	var start = $("#startTimeMuch").val();
	var end = $("#endTimeMuch").val();
	
	$.ajax({
		type : "post",
		url : "api/avaiCourtList",
		data : {
			"startTime" : start,
			"endTime" : end
			},
		dataType : "json",
		success : function(data) {
			for (var i = 0; i < data.length; i++) {
				$("#selectCourtName").append(
						"<option>" + data[i].courtName + "</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
//	$.ajax({
//		type : "post",
//		url : "api/courtList",
//		data : {},
//		dataType : "json",
//		success : function(data) {
//			for (var i = 0; i < data.length; i++) {
//				$("#selectCourtName").append(
//						"<option>" + data[i].courtName + "</option>");
//			}
//		},
//		error : function(XMLHttpRequest, textStatus, errorThrown) {
//
//		}
//	});
}

function rondomSelect(){
	if($("#selectCourtName").prop("disabled")==true){
		$("#selectCourtName").attr("disabled",false);
		$("#selectCourtName option:last").remove(); 
		$("#courtIdSelect").val("");
	}else{
		$("#selectCourtName").append("<option>随机</option>");
		$("#selectCourtName").val("随机");
		$("#selectCourtName").attr("disabled",true);
		$("#courtIdSelect").val("-1");
	}
}

function updateUser(){
	var userName = $("#editUserName").val();
	var name = $("#editName").val();
	var emailAddr = $("#editEmail").val();
	var mobile = $("#editMobile").val();
	
	$.ajax({
		type : "post",
		url : "api/updateUser",
		contentType: 'application/json',
		data : JSON.stringify({
			"userName" : userName,
			"name" : name,
			"emailAddr" : emailAddr,
			"mobile" : mobile
		}),
		dataType : "json",
		success : function(data) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html(data.errorMsg);
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html("用户信息更新失败!");
		}
	});
}

function getCurrentUserInfo(){
	$.ajax({
		type : "post",
		url : "api/userInfo",
		data : {
		},
		dataType : "json",
		success : function(data) {
			if(data !=null){
				$('#editUserName').val(data.userName);
				$('#editName').val(data.name);
				$('#editEmail').val(data.emailAddr);
				$('#editMobile').val(data.mobile);
			}else{
				$("#errorUserInfoDisplayDiv").show();
				$('#errorUserInfoMsgDisplay').html("加载用户信息失败!已无该用户？");
			}	
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html("加载用户信息失败!");
		}
	});
}

function closeAccount(){
	$.ajax({
		type : "post",
		url : "api/checkCurrentAccount",
		data : {
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
						url : "api/closeCurrentAccount",
						data : {
						},
						dataType : "json",
						success : function(data) {
							if(data == null){
								return;
							}	
							$("#errorUserInfoDisplayDiv").show();
							$('#errorUserInfoMsgDisplay').html(data.errorMsg);
							if(data.errorCode == "200"){
								$("#balDynamic").html(0);
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							$("#errorUserInfoDisplayDiv").show();
							$('#errorUserInfoMsgDisplay').html("销户失败!");
						}
					});
				}		
			}else{
				$("#errorUserInfoDisplayDiv").show();
				$('#errorUserInfoMsgDisplay').html(data.errorMsg);
			}
			
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html("销户失败!");
		}
	});
}

function queryAccountBal(){
	$('#accountListDisp').html("账户余额");
	$.ajax({
		type : "post",
		url : "api/accountBal",
		data : {
		},
		dataType : "json",
		success : function(data) {
			var lockFlag = data.lockFlag;
			var status = data.status;
			var accountDisp = "正常";
			if(status != null && status == "2" ) {
				accountDisp = "已销户";
			}
			if(lockFlag != null && lockFlag == "1" ) {
				accountDisp = "已锁定";
			}
			
			if(status)
			var trHTML = "<tr><td>账户状态</td><td>"
						+ accountDisp + "</td></tr>" 
						+ "<tr><td>账户余额</td><td id='balDynamic'>"
						+ data.bal + "</td></tr>"
						+ "<tr><td>操作</td><td>"
						+ "<a href='javascript:closeAccount();'>销户</a>" + "</td></tr>";
			
			$("#detailAccountInfoTbl").html(trHTML);// 在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html("余额查询失败!");
		}
	});
}

function queryAccountDetail(){
	$('#accountListDisp').html("明细列表");
	var transTime = $("#transTime").val();
	$.ajax({
		type : "post",
		url : "api/accountDetail",
		data : {
			"transDate" : transTime
		},
		dataType : "json",
		success : function(data) {
			if(data == null){
				return;
			}
			if(transTime == null || transTime == "" ){
				transTime = GetDateStr(0);
			}
			var trHTML = "<caption>" + transTime + "日明细</caption><tr><td>时间</td><td>类型</td><td>金额</td><td>状态</td></tr>";
			for (var i = 0; i < data.length; i++) {
				var status = "正常";
				if(data[i].flag != null && data[i].flag != "0"){
					status = "已撤销";
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
						+ data[i].transAmt + "</td><td>"
						+ status + "</td>" + "</tr>";
			}
			$("#detailAccountInfoTbl").html(trHTML);// 在table最后面添加一行
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#errorUserInfoDisplayDiv").show();
			$('#errorUserInfoMsgDisplay').html("加载明细失败!");
		}
	});
}