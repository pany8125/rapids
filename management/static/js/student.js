//The data store containing the list of states
var serviceStore = Ext.create('Ext.data.JsonStore', {
	autoLoad: true,
    fields :['id','name'],
    proxy: {
        type: 'ajax',
        url: '/merchant/product/serviceList.do',
        reader: {
            type: 'json',
            totalProperty: "result",
            root: 'rows'
        }
    }
});
var productStore = Ext.create('Ext.data.JsonStore', {
	autoLoad: true,
	fields :['id','name'],
	proxy: {
		type: 'ajax',
		url: '/merchant/product/productList.do',
		reader: {
			type: 'json',
			totalProperty: "result",
			root: 'rows'
		}
	}
});
var merchantStore = Ext.create('Ext.data.JsonStore', {
	autoLoad: true,
    fields :['id','eName'],
    proxy: {
        type: 'ajax',
        url: '/merchant/product/merchantList.do',
        reader: {
            type: 'json',
            totalProperty: "result",
            root: 'rows'
        }
    }
});

var productManagementPop = Ext.create('Ext.window.Window', {
	id: 'productManagementWin',
    title: '增加',
    height: 370,
    width: 480,
    bodyPadding: 5,
    maximizable: true,
    modal: true,
    closeAction: 'hide',
    overflowX: 'hidden',
    overflowY: 'auto',
    items: [
            Ext.create('Ext.form.Panel', {
            	id: 'productManagementForm',
            	width: 460,
            	height: 250,
                url: '/merchant/product/save.do?userName='+adminName,
                layout: 'anchor',
                defaults: {
                    anchor: '80%',
                    labelAlign: 'right',
                    labelWidth: 80,
                    blankText: '必填项'
                },
                // The fields
                defaultType: 'textfield',
                items: [{//隐藏域
                			name: 'id',
                			hidden: true
                		},{
            		        fieldLabel: '产品名称',
            		        name: 'name',
            		        allowBlank: false,
            		        maxLength: 40,
            		        maxLengthText: '不能超过20个字符'
                		},{
                			xtype: 'combo',
							name: 'merchantId',
							id: 'merchantId',
							fieldLabel: '商户名称',
							store: merchantStore,
					   		displayField: 'eName',
					   	    valueField: 'id',
							queryMode: 'local',
							editable:false
                		},{
							fieldLabel:  '<span style="color:gray">icon</span>',
							name: 'icon',
							id: 'icon',
							allowBlank: true
						},{
                			xtype : 'box', 
                			id : 'iconURLview',
                            fieldLabel : "营业执照",  
                            autoEl : {  
                                tag : 'div'
                            } 
                		},{
							fieldLabel: '类型',
							xtype: 'fieldcontainer',
							defaultType: 'radiofield',
							layout: 'hbox',
							items: [
									{
										boxLabel: 'ios',
										name: 'type',
										inputValue: '1',
										checked: true
									}, {
										boxLabel: 'android',
										name: 'type',
										id: 'type_inactive',
										inputValue: '0'
									}
							       ]
						},{
							fieldLabel: '状态',
							xtype: 'fieldcontainer',
							defaultType: 'radiofield',
							layout: 'hbox',
							bodyPadding: 10,
							items: [
									{
										boxLabel: '有效',
										name: 'status',
										inputValue: '1',
                                        id: 'status_active',
										checked: true
									}, {
										boxLabel: '无效',
										name: 'status',
                                        id: 'status_inactive',
										inputValue: '0'
									}
								   ]
						},{
							fieldLabel: '描述',
							xtype: 'textareafield',
							grow:true,
							name: 'description',
							allowBlank: true,
							maxLength: 500,
							width: 400,
							bodyPadding:20,
							maxLengthText: '不能超过500个字符'
						},{
            		        fieldLabel: '创建人',
            		        name: 'createby',
            		        value: adminName,
            		        allowBlank: false,
            		        maxLength: 40,
            		        hidden: true
                		},{
            		        fieldLabel: '修改人',
            		        name: 'updateby',
            		        value: adminName,
            		        maxLength: 40,
            		        hidden: true
                		}]
            }),
		Ext.create('Ext.form.Panel', {
			url: '/merchant/upload/upload',
			width: 560,
			height: 27,
			layout: 'hbox',
			id: 'pkgUploadForm',
			items: [
				{
					fieldLabel: 'icon',
					labelWidth: 80,
					labelAlign: 'right',
					xtype: 'textfield',
					width: 320,
					margin: '0 5 0 0',
					id: 'pkgText',
					readOnly: true
				},{
					xtype: 'filefield',
					buttonText: '浏览',
					name: 'pkg',
					buttonOnly: true,
					listeners: {
						change: function(fileField, string, eOpts){
							Ext.getCmp('pkgText').setValue(string);
							Ext.getCmp('pkgUploadBtn').setDisabled(false);
						}
					}
				},{
					xtype: 'button',
					id: 'pkgUploadBtn',
					text: '上传',
					disabled: true,
					margin: '0 0 0 10',
					handler: function(){
						Ext.getCmp('pkgUploadForm').getForm().submit({

							waitTitle: '系统提示',
							waitMsg: '图片上传中，请稍候......',
							success: function(form, action){
								Ext.getCmp('pkgUploadBtn').setDisabled(true);
								Ext.getCmp('icon').setValue(action.result.msg);
								Ext.get('iconURLview').dom.innerHTML ='点击图片查看大图：<a href="'+action.result.msg+'" target="_blank"><img src="'+action.result.msg+'" height="50"/></a>';
    	        				Ext.Msg.alert('系统提示', '图片上传成功！');
							}
						});
					}
				}
			]
		})
    ],
    buttons: [
        {
        	text: '保存',
        	handler: function(){
        		
        		if(!Ext.getCmp('productManagementForm').getForm().isValid()){
        			return;
        		}
                if(!adminName){
                    Ext.MessageBox.alert('系统提示',"cookie超时,请重新登陆系统",function(){
                        top.location.href='/';
                    });
                    return;
                }
                if(isValid(Ext.getCmp('productManagementForm').form.findField('id').getValue()) && Ext.getCmp('productManagementForm').form.findField('status_inactive').checked){
                    Ext.MessageBox.confirm('系统提示','系统将会把该产品下的服务Relation置为无效',function(e){
                        if(e=='no'){
                            return ;
                        }else{
                            save() ;
                        }
                    });
                }else{
                    save() ;
                }
        	}
        },{
        	text: '取消',
        	handler: function(){
        		productManagementPop.hide();
        	}
        }
    ]
});

function save(){
    Ext.getCmp('productManagementForm').submit({
        waitTitle: '系统提示',
        waitMsg: '保存中......',
        success: function(form, action){
            Ext.getCmp('productManagementWin').hide();
            centerPanel.getStore().reload();
            southPanel.getStore().reload();
            serviceStore.reload();
            productStore.reload();
            merchantStore.reload();
            Ext.Msg.alert('系统提示', '保存成功！');
        },
        failure: function(form, action){
            Ext.getCmp('productManagementWin').hide();
            centerPanel.getStore().reload();
            southPanel.getStore().reload();
            serviceStore.reload();
            productStore.reload();
            merchantStore.reload();
            Ext.Msg.alert('系统提示', '保存成功！');
        }
    });
}

var centerPanel = Ext.create('Ext.grid.Panel', {
	region: 'center',
	title: '产品列表',
    columns: [
		        {header: '产品ID',  dataIndex: 'id', width: 100,sortable:true },
		        {header: '产品名称', dataIndex: 'name', width: 200},
				{header: 'ProductId', dataIndex: 'productId', width: 200},
				{header: '商户名称', dataIndex: 'merchantName', width: 200},
		        {header: 'icon', dataIndex: 'icon', width: 100},
		        {header: '类型', dataIndex: 'type', width: 150,sortable:true,renderer:function(value){
					if(value == 1){
						return 'ios';
					}else{
						return 'android';
					}
				}
				},
		        {header: '状态', dataIndex: 'status', width: 150,sortable:true,renderer:function(value){
	        		if(value == 1){
	        			return '有效';
	        		}else{
	        			return '无效';
	        		}
        			}
		        },
				{header: '产品描述', dataIndex: 'description', width: 200},
		        {header: '创建人',  dataIndex: 'createby', width: 150,sortable:true },
		        {header: '创建时间',  dataIndex: 'createtime', width: 200,sortable:true,renderer:function(value){
			        	if(value != null){
			        		return Ext.util.Format.date(new Date(value),'Y-m-d H:i:s');
			        	}else{
			        		return '';
			        	}
		        	}
		        },
		        {header: '修改人',  dataIndex: 'updateby', width: 150,sortable:true },
		        {header: '修改时间',  dataIndex: 'updatetime', width: 200,sortable:true,renderer:function(value){
			        	if(value != null){
			        		return Ext.util.Format.date(new Date(value),'Y-m-d H:i:s');
			        	}else{
			        		return '';
			        	}
		        	}
		        }
		     ],
	store: Ext.create('Ext.data.JsonStore', {
		autoLoad: true,
		storeId: 'centerStore',
		pageSize: 20,
	    fields :['id', 'name', 'merchantId' ,'productId', 'merchantName', 'icon','type','description','status','createby','createtime','updateby','updatetime'],
	    proxy: {
	        type: 'ajax',
	        url: '/merchant/product/allList.do',
	        reader: {
	            type: 'json',
	            totalProperty: "result",
	            root: 'rows'
	        }
	    }
	}),selModel: {
		listeners: {
			select: function(rowModel, record, index, eOpts){

            	var store = southPanel.getStore();  
            	store.baseParams.se_productId=record.data.id;
				southPanel.getStore().load({params: {productId: record.data.id}});
			}
		},	
		mode: 'SINGLE'
	},
	tbar: [
	       	{
	       		xtype: 'button',
	       		text: '增加',
	       		handler: function(){
		       		Ext.getCmp('productManagementForm').getForm().reset();
		       		productManagementPop.setTitle('增加');
		       		productManagementPop.show();
		       		Ext.get('iconURLview').dom.innerHTML ='';
	       		}
	       	},'-',{
	       		xtype: 'button',
	       		text: '编辑',
	       		handler: function(){
	       			var models = centerPanel.getSelectionModel().getSelection();
	       			if(models.length <= 0){
	       				Ext.Msg.alert('系统提示', '请选择要编辑的数据');
	       				return;
	       			}
	       			if(models[0].get('icon') && Ext.get('iconURLview')!=null){
	       				Ext.get('iconURLview').dom.innerHTML ='点击图片查看大图：<a href="'+models[0].get('icon')+'" target="_blank"><img src="'+models[0].get('icon')+'" height="50"/></a>';
	       			}
	       			productManagementPop.setTitle('编辑');
	       			productManagementPop.show();
	       			var record = centerPanel.getStore().getById(models[0].get('id'));
	       			Ext.getCmp('productManagementForm').loadRecord(record);
	       		}
	       	}
	      ],
	bbar: Ext.create('Ext.toolbar.Paging', {//xtype: pagingtoolbar
        store: Ext.data.StoreManager.get('centerStore'),
        displayInfo: true,
        displayMsg: '第{0}-{1}条，共{2}条',
        emptyMsg: "没有数据",
        beforePageText: '第',
        afterPageText: '页，共 {0} 页'
    }),
    listeners: {
    	dblclick: {
            element: 'body', //bind to the underlying body property on the panel
            fn: function(){ 
            	var models = centerPanel.getSelectionModel().getSelection();
       			if(models.length <= 0){
       				Ext.Msg.alert('系统提示', '请选择要编辑的数据');
       				return;
       			}
       			if(models[0].get('icon') && Ext.get('iconURLview')!=null){
       				Ext.get('iconURLview').dom.innerHTML ='点击图片查看大图：<a href="'+models[0].get('icon')+'" target="_blank"><img src="'+models[0].get('icon')+'" height="50"/></a>';
       			}
       			productManagementPop.setTitle('编辑');
       			productManagementPop.show();
       			var record = centerPanel.getStore().getById(models[0].get('id'));
       			Ext.getCmp('productManagementForm').loadRecord(record);
            }
        } 
    }
});

var serviceManagementPop = Ext.create('Ext.window.Window', {
	id: 'serviceManagementWin',
    title: '增加',
    height: 370,
    width: 480,
    bodyPadding: 5,
    maximizable: true,
    modal: true,
    closeAction: 'hide',
    overflowX: 'hidden',
    overflowY: 'auto',
    items: [
            Ext.create('Ext.form.Panel', {
            	id: 'serviceManagementForm',
            	width: 460,
            	height: 200,
                url: '/merchant/product/saveService.do?userName='+adminName,
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
                            fieldLabel: 'isEdit',
                            name: 'isEdit',
                            allowBlank: false,
                            hidden:true
                		},{
                            fieldLabel: '产品',
                             name: 'productId',
                            allowBlank: false,
                             hidden:true
                         }/*,{
                            xtype: 'fieldLabel',
                            name: 'productId',
                            fieldLabel: '产品',
                            store: productStore,
                            displayField: 'name',
                            valueField: 'id',
                            queryMode: 'local',
                            editable:false
                         }*/,{
							xtype: 'combo',
							name: 'serviceId',
							fieldLabel: '服务',
							store: serviceStore,
							displayField: 'name',
							valueField: 'id',
							editable:false
						},{
                			fieldLabel: 'key',
                			name: 'serviceKey',
                			id: 'serviceKey',
                			readOnly: true
                		},{
							fieldLabel: 'notifyUrl',
							xtype: 'textareafield',
							grow: true,
							name: 'notifyUrl',
							allowBlank: false,
							maxLength: 500,
							width: 400,
							bodyPadding:20,
							maxLengthText: '不能超过500个字符',
                            vtype:'url'

						},{
							fieldLabel: '状态',
							xtype: 'fieldcontainer',
							defaultType: 'radiofield',
							layout: 'hbox',
							bodyPadding: 10,
							items: [
									{
										boxLabel: '有效',
										name: 'status',
										inputValue: '1',
										checked: true
									}, {
										boxLabel: '无效',
										name: 'status',
										inputValue: '0'
									}
								   ]
						},{
            		        fieldLabel: '创建人',
            		        name: 'createby',
            		        value: adminName,
            		        allowBlank: false,
            		        maxLength: 40,
            		        hidden: true
                		},{
            		        fieldLabel: '修改人',
            		        name: 'updateby',
            		        value: adminName,
            		        maxLength: 40,
            		        hidden: true
                		}]
            }),
    ],
    buttons: [
        {
        	text: '保存',
        	handler: function(){
        		
        		if(!Ext.getCmp('serviceManagementForm').getForm().isValid()){
        			return;
        		}
                if(!adminName){
                    Ext.MessageBox.alert('系统提示',"cookie超时,请重新登陆系统",function(){
                        top.location.href='/';
                    });
                    return;
                }
        		Ext.getCmp('serviceManagementForm').submit({
        			waitTitle: '系统提示',
        			waitMsg: '保存中......',
        			success: function(form, action){
        				Ext.getCmp('serviceManagementWin').hide();
        				southPanel.getStore().reload();
        				Ext.Msg.alert('系统提示', action.result.msg ==null?"保存成功":action.result.msg);
        			},
        			failure: function(form, action){
						Ext.getCmp('serviceManagementWin').hide();
						southPanel.getStore().reload();
						Ext.Msg.alert('系统提示', action.result.msg ==null?"保存成功":action.result.msg);
        			}
        		});
        	}
        },{
        	text: '取消',
        	handler: function(){
        		serviceManagementPop.hide();
        	}
        }
    ]
});


var southPanel = Ext.create('Ext.grid.Panel', {
	region: 'south',
	title: '服务列表',
    columns: [
		        {header: '服务ID',  dataIndex: 'serviceId', width: 100,sortable:true },
		        {header: '服务名称',  dataIndex: 'name', width: 200},
		        {header: '服务Key', dataIndex: 'serviceKey', width: 200},
		        {header: 'notifyUrl', dataIndex: 'notifyUrl', width: 250},
		        {header: '状态', dataIndex: 'status', width: 150,sortable:true,renderer:function(value){
	        		if(value == 1){
	        			return '有效';
	        		}else{
	        			return '无效';
	        		}
        			}
		        },
				{header: '创建人',  dataIndex: 'createby', width: 150,sortable:true },
		        {header: '创建时间',  dataIndex: 'createtime', width: 200,sortable:true,renderer:function(value){
			        	if(value != null){
			        		return Ext.util.Format.date(new Date(value),'Y-m-d H:i:s');
			        	}else{
			        		return '';
			        	}
		        	}
		        },
		        {header: '修改人',  dataIndex: 'updateby', width: 150,sortable:true },
		        {header: '修改时间',  dataIndex: 'updatetime', width: 200,sortable:true,renderer:function(value){
			        	if(value != null){
			        		return Ext.util.Format.date(new Date(value),'Y-m-d H:i:s');
			        	}else{
			        		return '';
			        	}
		        	}
		        }
		     ],
	store: Ext.create('Ext.data.JsonStore', {
		baseParams:{se_productId:1},
		autoLoad: true,
		storeId: 'south', 
		pageSize: 20,
	    fields :['isEdit','productId','serviceId', 'name','serviceKey','notifyUrl','status','createby','createtime','updateby','updatetime'],
	    proxy: {
	        type: 'ajax',
	        url: '/merchant/product/relationList.do',
	        reader: {
	            type: 'json',
	            totalProperty: "result",
	            root: 'rows'
	        }
	    },
	    listeners: {
	    	load: function(store, records, isSucc, eOpts){
	    		if(store.getCount() > 0){
	    			southPanel.setHeight(450);
	    		}else{
	    			southPanel.setHeight(200);
	    		}
	    	}
	    }
	}),
	tbar: [
	       	{
	       		xtype: 'button',
	       		text: '增加',
	       		handler: function(){
                    if(centerPanel.getSelectionModel().getLastSelected() == null){
                        Ext.Msg.alert('系统提示', '请先选择要添加服务的产品');
                        return;
                    }
		       		Ext.getCmp('serviceManagementForm').getForm().reset();
	   				Ext.getCmp('pkgUploadForm').getForm().reset();
		       		serviceManagementPop.setTitle('增加');
		       		serviceManagementPop.show();
                    Ext.getCmp('serviceManagementForm').form.findField('serviceId').readOnly=false;
                    Ext.getCmp('serviceManagementForm').form.findField('productId').setValue(southPanel.getStore().baseParams.se_productId);
                    Ext.getCmp('serviceManagementForm').form.findField('isEdit').setValue(1);
                }
	       	},'-',{
	       		xtype: 'button',
	       		text: '编辑',
	       		handler: function(){
	       			var models = southPanel.getSelectionModel().getSelection();
	       			if(models.length <= 0){
	       				Ext.Msg.alert('系统提示', '请选择要编辑的数据');
	       				return;
	       			}
	       			serviceManagementPop.setTitle('编辑');
	       			serviceManagementPop.show();
					Ext.getCmp('serviceManagementForm').loadRecord(models[0]);
                    Ext.getCmp('serviceManagementForm').form.findField('serviceId').readOnly=true;
                    Ext.getCmp('serviceManagementForm').form.findField('isEdit').setValue(2);
	       		}
	       	}
	      ],
	bbar: Ext.create('Ext.toolbar.Paging', {//xtype: pagingtoolbar
        store: Ext.data.StoreManager.get('south'),
    
        displayInfo: true,
        displayMsg: '第{0}-{1}条，共{2}条',
        emptyMsg: "没有数据",
        beforePageText: '第',
        afterPageText: '页，共 {0} 页'
    }),
    listeners: {
    	dblclick: {
            element: 'body', //bind to the underlying body property on the panel
            fn: function(){ 
            	var models = southPanel.getSelectionModel().getSelection();
       			if(models.length <= 0){
       				Ext.Msg.alert('系统提示', '请选择要编辑的数据');
       				return;
       			}
       			serviceManagementPop.setTitle('编辑');
       			serviceManagementPop.show();
                Ext.getCmp('serviceManagementForm').loadRecord(models[0]);
                Ext.getCmp('serviceManagementForm').form.findField('serviceId').readOnly=true;
                Ext.getCmp('serviceManagementForm').form.findField('isEdit').setValue(2);
            }
        } 
    }
});


//domReady
Ext.onReady(function(){
	southPanel.getStore().on('beforeload', function(s) {
		var params = s.getProxy().extraParams;  
        Ext.apply(params,{productId:s.baseParams.se_productId});
	});
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items:[centerPanel,southPanel]
	});
});
