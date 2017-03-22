var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

var decode64 = function(input) {
	if(!isValid(input)){
		Ext.MessageBox.alert('系统提示',"cookie超时,请重新登陆系统",function(){
			top.location.href='/';
		});
		return;
	}
	var output = "";
	var chr1, chr2, chr3 = "";
	var enc1, enc2, enc3, enc4 = "";
	var i = 0;

	var base64test = /[^A-Za-z0-9\+\/\=]/g;
	if (base64test.exec(input)) {
		Ext.Msg
				.alert("Warning",
						"The document contains invalid characters. Errors can occur when decoding it.");
	}
	input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

	do {
		enc1 = keyStr.indexOf(input.charAt(i++));
		enc2 = keyStr.indexOf(input.charAt(i++));
		enc3 = keyStr.indexOf(input.charAt(i++));
		enc4 = keyStr.indexOf(input.charAt(i++));

		chr1 = (enc1 << 2) | (enc2 >> 4);
		chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
		chr3 = ((enc3 & 3) << 6) | enc4;

		output = output + String.fromCharCode(chr1);

		if (enc3 != 64) {
			output = output + String.fromCharCode(chr2);
		}
		if (enc4 != 64) {
			output = output + String.fromCharCode(chr3);
		}

		chr1 = chr2 = chr3 = "";
		enc1 = enc2 = enc3 = enc4 = "";
	} while (i < input.length);

	return unescape(output);
};

var path = "/rapids-management", adminID = decode64(Ext.util.Cookies
		.get("adminId")), adminName = decode64(Ext.util.Cookies
		.get("adminName"));
var task = new Ext.util.DelayedTask(function() {
	location.href = '/management/login.html';
});

Ext.onReady(function() {
	if (!adminID) {
		Ext.Msg.alert('系统提示', '请重新登陆');
		task.delay(2000);
	}
});

function checkCommonImgType(val){
	var strRegex = '.jpg|.jpeg|.gif|.bmp|.png|';
	var fileExt=val.substr(val.lastIndexOf(".")).toLowerCase();
	if(strRegex.indexOf(fileExt+"|")!=-1)//如果图片文件，则进行图片信息处理
	{
		return true;
	}else{
		Ext.Msg.alert('系统提示', '仅支持jpg,jpeg,gif,bmp,png');
		return false;
	}
}

function isValid(val){
	if(typeof(val) == "undefined" || val==null || val=='' || val==0){
		return false;
	}else{
		return true;
	}
}

Ext
		.apply(
				Ext.form.VTypes,
				{
					url : function(val, field) {
						try {
							var patrn=/^((http|https):\/\/)/i; 
							if (patrn.exec(val))
								return true;
							else
								return false;
						} catch (e) {
							return false;
						}
					},
					urlText : '请输入有效的URL地址.',
					num_character : function(val, field) {
						try {
							var strRegex = /^[0-9a-zA-Z]*$/g;
							if (strRegex.test(val))
								return true;
							return false;
						} catch (e) {
							return false;
						}
					},
					num_characterText : '请输入数字或字母.'
				});