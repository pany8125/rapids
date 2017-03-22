var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

var encode64 = function(input) {
    input = escape(input);
    var output = "";
    var chr1, chr2, chr3 = "";
    var enc1, enc2, enc3, enc4 = "";
    var i = 0;
    
    do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);
        
        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;
        
        if (isNaN(chr2)) {
            enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
            enc4 = 64;
        }
        
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
    } while (i < input.length);
    
    return output;
}

var path="/rapids-management";

Ext.onReady(function() {
	//Ext.QuickTips.init();
	
	var userName = new Ext.form.TextField({
		name : "name",
		id : "name",
		fieldLabel : "用户名",
		blankText : "请输入用户名",
		allowBlank: false,
		selectOnFocus : true,
		cls: 'login_user',
		anchor: '95%',
		value:''
	});
	
	var password = new Ext.form.TextField({
		cls : "key",
		name : "password",
		id : "password",
		fieldLabel : "密&nbsp;&nbsp;&nbsp;码",
		blankText : "请输入密码",
		allowBlank: false,
		selectOnFocus : true,
		inputType : "password",
		cls: 'login_password',
		anchor: '95%',
		value:""
	});
	
	/*var randCode = new Ext.form.TextField({
		cls : "key",
		name : "randCode",
		id:'randCode', 
		fieldLabel : "验证码",
		blankText : "验证码不能为空",
		width:80, 
		allowBlank: false,
		selectOnFocus : true
	});*/
	
	var form = new Ext.FormPanel({
		region: 'center',
		labelAlign : "right",
		labelWidth : 55,
		labelPad : 0,
		frame : true,
		//items : [{html:'&nbsp'},userName, password,randCode,{html:'&nbsp'}],
		items : [{html:'&nbsp'},userName, password,{html:'&nbsp'}],
		listeners: {
			afterlayout : function(){
				password.focus();
			}
		
		}
	});
	
	userName.on("specialkey", function(f, e) {
		if (e.getKey() == e.ENTER) {
			login();
		}
	}, this);
	
	password.on("specialkey", function(f, e) {
		if (e.getKey() == e.ENTER) {
			login();
		}
	}, this);
	
	var win = new Ext.Window({
		el : "win",
		title : "重构记忆后台管理系统",
		width : 300,
		height : 180,
		layout: 'border',
		resizable : false,
		plain : true,
//		autoScroll : true,
		closable : false,
		border: false,
		buttons : [{
			text : "登录",
			id : "login",
			minWidth: 50,
			handler : login
		}, {
			text: "重置",
			minWidth: 50,
			handler : function() {
				form.getForm().reset();
			}
		}],
		items : [form]
	});

	win.show();
	/*var bd = Ext.getDom('randCode'); 
	var bd2 = Ext.get(bd.parentNode); 
	 bd2.createChild([{
		tag: 'span',
		html: ' <a href="javascript:reloadcode();">'
		}, {
		tag: 'img',
		id: 'safecode',
		src: 'image.jsp',
		align: 'absbottom'
		}, {
		tag: 'span',
		html: '</a>'
		}]);*/
	function login(){

		if (!form.getForm().isValid()) {
			Ext.Msg.alert("提示","请输入用户名和密码",function(){
				userName.focus();
			});
			return;
		}
		
        form.getForm().submit({
			waitTitle : '系统信息',
			url: path + '/login/loginValid',   //TODO:提交URL
 			method : 'post',   //指定发送方式
 			waitMsg:"正在验证登录用户,请稍候..."  ,
			clientValidation: true,
            success:function(form, action){ 
				var admin = eval('('+action.result.msg+')');
				var d = new Date();
				d.setTime((new Date()).getTime()+60*60*1000);//cookie expires:1h
				Ext.util.Cookies.set('adminName',encode64(admin.name),d);
				Ext.util.Cookies.set('adminId',encode64(admin.id),d);
        		if(window.parent){
        			window.parent.location.href = 'views/index.html';
        		}else{
        			window.location.href = path + 'views/index.html';
        		}
				
            },
            failure: function(form, action) {
            	if(!action.result){
            		Ext.Msg.alert("提示","网络错误,请重新登录");  
            	}else {
            		Ext.Msg.alert("提示",action.result.msg);  
            	}
		    }
    	});  
	}
});
