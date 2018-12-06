function GetDateStr(AddDayCount) {
	var dd = new Date();
	dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
	var fmt = 'yyyy-MM-dd';
	return dateFtt(fmt, dd);
}

function GetMonthStr(AddMonthCount) {
	var dd = new Date();
	dd.setMonth(dd.getMonth() + AddMonthCount);//获取AddDayCount天后的日期
	var fmt = 'yyyy-MM-dd';
	return dateFtt(fmt, dd);
}

function GetTodayHourStr(AddHourCount) {
	var dd = new Date();
	dd.setHours(dd.getHours() + AddHourCount);//获取AddDayCount天后的日期
	var fmt = 'yyyy-MM-dd HH';
	return dateFtt(fmt, dd);
}

function GetHourStr(AddHourCount) {
	var dd = new Date();
	dd.setHours(dd.getHours() + AddHourCount);//获取AddDayCount天后的日期
	var fmt = 'yyyy-MM-dd HH';
	return dateFtt(fmt, dd);
}

function dateFtt(fmt, date) { //author: meizz   
	var o = {
		"M+" : date.getMonth() + 1, //月份   
		"d+" : date.getDate(), //日   
		"H+" : date.getHours(), //小时   
		"m+" : date.getMinutes(), //分   
		"s+" : date.getSeconds(), //秒   
		"q+" : Math.floor((date.getMonth() + 3) / 3), //季度   
		"S" : date.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}