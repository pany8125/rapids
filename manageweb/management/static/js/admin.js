var _adminid;
var adminPop = Ext.create('Ext.window.Window', {
	id: 'adminWin',
    title: '增加',
    height: 120,
    width: 300,
    bodyPadding: 5,
    maximizable: true,
    modal: true,
    closeAction: 'hide',
    overflowX: 'hidden',
    overflowY: 'auto',
    items: [
            Ext.create('Ext.form.Panel', {
            	id: 'adminForm',
                url: path + '/admin/saveAdmin.do',
                layout: 'anchor',
                defaults: {
                    anchor: '80%',
                    labelAlign: 'right',
                    labelWidth: 80,
                    blankText: '必填项'
                },
                // The fields
                defaultType: 'textfield',
                items: [{
            		        fieldLabel: 'uid',
            		        name: 'uid',
            		        allowBlank: false
                		}]
            })
    ],
    buttons: [
        {
        	text: '保存',
        	handler: function(){
        		
        		if(!Ext.getCmp('adminForm').getForm().isValid()){
        			return;
        		}
        		
        		Ext.getCmp('adminForm').submit({
        			waitTitle: '系统提示',
        			waitMsg: '保存中......',
        			success: function(form, action){
        				Ext.getCmp('adminWin').hide();
        				centerPanel.getStore().reload();
        				Ext.Msg.alert('系统提示', '保存成功！');
        			},
        			failure: function(form, action){
        				Ext.Msg.alert('系统提示', action.result.msg);
        			}
        		});
        	}
        },{
        	text: '取消',
        	handler: function(){
        		adminPop.hide();
        	}
        }
    ]
});

var rolePop = Ext.create('Ext.window.Window', {
	id: 'roleWin',
    title: '增加',
    height: 120,
    width: 300,
    bodyPadding: 5,
    maximizable: true,
    modal: true,
    closeAction: 'hide',
    overflowX: 'hidden',
    overflowY: 'auto',
    items: [
            Ext.create('Ext.form.Panel', {
            	id: 'roleForm',
                url: path + '/admin/saveAdminRoleMember.do',
                layout: 'anchor',
                defaults: {
                    anchor: '80%',
                    labelAlign: 'right',
                    labelWidth: 80,
                    blankText: '必填项'
                },
                // The fields
                defaultType: 'textfield',
                items: [{
                			name: 'adminid',
                			id: 'adminid',
                			hidden: true
                		},{
                			xtype: 'combo',
							name: 'roleid',
							fieldLabel: '角色',
							store: Ext.create('Ext.data.JsonStore', {
            	       			storeId: 'role',
            	       			autoLoad: true,
            	       			fields: ['id', 'name'],
            	       			proxy: {
            	       				type: 'ajax',
            	       				url: path + '/admin/rolelist.do',
            		       			reader: {
            		       				totalProperty: 'results',
            			       			root: 'rows'
            		       			}
            	       			}
            	       		}),
            	       		displayField: 'name',
					   	    valueField: 'id'
                		}]
            })            
    ],
    buttons: [
        {
        	text: '保存',
        	handler: function(){
        		Ext.getCmp('adminid').setValue(_adminid);
        		var roleForm = Ext.getCmp('roleForm').getForm();
        		if(!roleForm.isValid()){
        			return;
        		}
        		
       			roleForm.submit({
        			waitTitle: '系统提示',
        			waitMsg: '保存中......',
        			success: function(form, action){
        				if(!action.result.success){
        					Ext.Msg.alert('系统提示', action.result.msg);
        				}else{
	        				Ext.getCmp('roleWin').hide();
	        				eastPanel.getStore().reload();
	        				Ext.Msg.alert('系统提示', '保存成功！');
        				}
        			},
        			failure: function(form, action){
        				Ext.Msg.alert('系统提示', action.result.msg);
        			}
        		});
        	}
        },{
        	text: '取消',
        	handler: function(){
        		rolePop.hide();
        	}
        }
    ]
});

var centerPanel = Ext.create('Ext.grid.Panel', {
	region: 'center',
	title: '账号列表',
    columns: [
		        {header: 'ID',  dataIndex: 'id', width: 80,sortable:true },
		        {header: 'uid', dataIndex: 'uid',width: 200}
 		     ],
	selModel: {
	listeners: {
			select: function(rowModel, record, index, eOpts){
				eastPanel.getStore().load({params: {adminid: record.data.id}});
			}
		},	
		mode: 'MULTI'
	},
	store: Ext.create('Ext.data.JsonStore', {
		autoLoad: true,
		storeId: 'centerStore',
	    fields :['id', 'uid'],
	    proxy: {
	        type: 'ajax',
	        url: path + '/admin/adminlist.do',
	        reader: {
	            type: 'json',
	            totalProperty: "result",
	            root: 'rows'
	        }
	    }
	}),
	tbar: [
	       	{
	       		xtype: 'button',
	       		text: '增加',
	       		handler: function(){
		       		Ext.getCmp('adminForm').getForm().reset();
	   				adminPop.setTitle('增加');
		       		adminPop.show();
	       		}
	       	}
	      ]
});

function delAdminRole(records){
	var admins = centerPanel.getSelectionModel().getSelection();
	Ext.Ajax.request({
		url: path + '/admin/delAdminRole.do?id='+records[0].get('id')+'&userId='+admins[0].get('id'),
		scope: this,
		async: true,
		success: function(response, options){
			Ext.Msg.alert("系统提示", "删除成功");
			eastPanel.getStore().reload();
		},
		failure: function(form, action){
			Ext.Msg.alert('系统提示', action.result.msg);
		}
	});
}

var eastPanel = Ext.create('Ext.grid.Panel', {
	region: 'east',
	title: '角色列表',
	width: 500,
	split: true,
	collapsible: true,
	columns: [
	          	{header: 'ID', align: 'center', width: 50, dataIndex: 'id'},
	          	{header: '名称', align: 'center', width: 100, dataIndex: 'name'},
	          	{header: '描述', align: 'center', dataIndex: 'description'}
		     ],
	store: Ext.create('Ext.data.JsonStore', {
	    storeId:'eastStore',
	    fields :['id','name','description'],
	    proxy: {
	        type: 'ajax',
	        url: path + '/admin/adminrole.do',
	        reader: {
	        	totalProperty: 'results',
	            root: 'rows'
	        }
	    }
	}),
	tbar: [
			{	//计划列表表头添加按钮
	       		xtype: 'button',
	       		text: '增加',
	       		handler: function(){
	       			var admins = centerPanel.getSelectionModel().getSelection();
	       			if(admins.length == 0){
	       				Ext.Msg.alert('系统提示', '请选择升级包');
	       				return;
	       			}
	       			_adminid=admins[0].data.id;
	       			Ext.getCmp('roleForm').getForm().reset();
	       			Ext.getCmp('roleWin').setTitle('添加');
	       			Ext.getCmp('roleWin').show();
	       		}
	       	},'-',{
	       		xtype: 'button',
	       		text: '删除',
	       		handler: function(){
	       			var roles = eastPanel.getSelectionModel().getSelection();
       				if(roles.length == 0){
       					Ext.Msg.alert('系统提示', '请选择要删除的计划。'); return;
       				}
	       			Ext.Msg.confirm('系统提示', '您确认要删除吗?', function(option){
	       				if('yes' === option){
	       					delAdminRole(roles);
	       				}
	       			});
	       		}
	       	}
	]
});

//domReady
Ext.onReady(function(){
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items:[centerPanel,eastPanel]
	});
});