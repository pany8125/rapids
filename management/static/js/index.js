var topPanel = Ext.create('Ext.toolbar.Toolbar', {
		region : 'north',
		id: 'topPanel',
		items : [
		         '-',
		         '当前用户: '+ adminName,
		         '-',
		         '本地时间: <span id="datetime" style="color:green">'+ Ext.util.Format.date(new Date(),'Y-m-d H:i:s')+'</span>',
		         '-',
		         {
		        	 text: '退出系统'	,
		        	 handler: function(){
		        		 Ext.Msg.confirm("系统信息","是否确认退出系统?",function(text){
		        			 	if(text == 'no')
		        			 		return;
					
								Ext.Ajax.request({    
									url: path + '/login/logout',
									success:function(response,options){
										Ext.util.Cookies.clear('adminName');
										Ext.util.Cookies.clear('adminId');
										top.location.href = '/login.html';
									}
								});  
		        		 })
		        	 }
		         },'-'
		    ]
});

//左侧菜单栏
var westPanel = Ext.create('Ext.tree.Panel', {
	region: 'west',
	title: '重构记忆后台系统',
	width: 180,
	collapsible: true,
	rootVisible: false,
	split: true,
	store : Ext.create('Ext.data.TreeStore', {
		storeId: 'menuStore',
		fields: ['id', 'text', 'leaf', 'url'],
		proxy: {
			type: 'ajax',
			url: path + '/menu/list?adminID=' + adminID
		},
		listeners: {
			load: function(store, records){
				for(var i = 0;i < records.length;i++){
					records[i].data.url = records[i].data.href;
					delete records[i].data.href;
				}
			}
		}
	}),
	listeners: {
		itemclick: function(rowModel, record, index, eOpts){
			var tabId = 'tab_' + record.data.id,
				tab = centerPanel.getComponent(tabId);
			if(record.data.url.trim()){
				if(!tab){
					tab = createPanel(tabId, record.data.text, record.data.url);
					centerPanel.add(tab);
				}
				centerPanel.setActiveTab(tab);
			}
		}
	}
});

//主内容区域
var centerPanel = Ext.create('Ext.tab.Panel', {
	region: 'center',
	activeTab: 0,
	items: {
		title: '首页',
		html: '<center><font size="10" face="Verdana" color="blue">后台管理首页</font></center>'
	}
});

//
Ext.onReady(function(){
	if(!adminName){
		top.location.href = '/management/login.html';
	}
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items: [topPanel, westPanel, centerPanel]
	});
	
	var updateClock = function(){  
		var obj = Ext.query('span[id=datetime]')[0];
		obj.innerHTML = Ext.util.Format.date(new Date(),'Y-m-d H:i:s'); 
	}   
	var task = {  
	    run: updateClock,  
	    interval: 1000 //1 second  
	}  
	var runner = new Ext.util.TaskRunner();  
	runner.start(task); 
	
});

//创建内容显示面板
function createPanel(id, title, href){
	return Ext.create('Ext.Panel', {
		id: id,
		title : title,
		autoScroll : false,
		closable : true,
		border: false,
		html : '<iframe id="' + id + '" src="' + href + '" scrolling="yes" frameborder="0" style="width:100%; height:100%;"></iframe>'
	});
}
