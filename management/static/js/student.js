var _gradeid;
var gradePop = Ext.create('Ext.window.Window', {
	id: 'gradeWin',
	title: '增加',
	height: 200,
	width: 300,
	bodyPadding: 5,
	maximizable: true,
	modal: true,
	closeAction: 'hide',
	overflowX: 'hidden',
	overflowY: 'auto',
	items: [
		Ext.create('Ext.form.Panel', {
			id: 'gradeForm',
			url: path + '/grade/saveGrade?adminName='+adminName,
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
			}, {

				fieldLabel: '班级名称',
				name: 'name',
				allowBlank: false
			},
				{
					fieldLabel: '班级描述',
					name: 'description',
					allowBlank: false
				},
				{
					fieldLabel: '班级学年',
					name: 'gradeYear',
					allowBlank: false
				}]
		})
	],
	buttons: [
		{
			text: '保存',
			handler: function () {

				if (!Ext.getCmp('gradeForm').getForm().isValid()) {
					Ext.Msg.alert('系统提示', '请按要求填写表格！');
					return;
				}
				Ext.getCmp('gradeForm').submit({
					waitTitle: '系统提示',
					waitMsg: '保存中......',
					success: function (form, action) {
						Ext.getCmp('gradeWin').hide();
						centerPanel.getStore().reload();
						Ext.Msg.alert('系统提示', '保存成功！');
					},
					failure: function (form, action) {
						Ext.Msg.alert('系统提示', action.result.msg);
					}
				});
			}
		}, {
			text: '取消',
			handler: function () {
				gradePop.hide();
			}
		}
	]
});

var studentPop = Ext.create('Ext.window.Window', {
	id: 'studentWin',
	title: '增加',
	height: 300,
	width: 300,
	bodyPadding: 5,
	maximizable: true,
	modal: true,
	closeAction: 'hide',
	overflowX: 'hidden',
	overflowY: 'auto',
	items: [
		Ext.create('Ext.form.Panel', {
			id: 'studentForm',
			url: path + '/grade/saveStudent?adminName='+adminName,
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
				name: 'id',
				hidden: true
			},
				{
					fieldLabel: '手机号',
					name: 'mobile',
					allowBlank: false
				},
				{
					fieldLabel: '密码',
					name: 'password',
					allowBlank: false
				},
				{
					fieldLabel: '姓名',
					name: 'name',
					allowBlank: false
				},
				{
					xtype: 'combo',
					name: 'gradeId',
					id: 'gradeId',
					fieldLabel: '班级',
					store: Ext.create('Ext.data.JsonStore', {
						storeId: 'class',
						autoLoad: true,
						fields: ['id', 'name'],
						proxy: {
							type: 'ajax',
							url: path + '/grade/list',
							reader: {
								totalProperty: 'results',
								root: 'rows'
							}
						}
					}),
					editable: false,
					allowBlank: false,
					displayField: 'name',
					valueField: 'id'
				},
				{
					xtype: 'numberfield',
					fieldLabel: '年龄',
					name: 'age',
					allowBlank: false,
					maxValue: 99,
					minValue: 0
				},
				{
					fieldLabel: '性别',
					name: 'sex',
					xtype: 'combo',
					store: Ext.create('Ext.data.ArrayStore', {
						fields: ['value', 'text'],
						data: [
							[0, '女'],
							[1, '男']
						]
					}),
					editable: false,
					allowBlank: false,
					displayField: 'text',
					valueField: 'value'
				},
				{
					vtype: 'email',
					fieldLabel: '邮箱',
					allowBlank: true,
					name: 'email'
				}]
		})
	],
	buttons: [
		{
			text: '保存',
			handler: function () {
				var studentForm = Ext.getCmp('studentForm').getForm();
				if (!studentForm.isValid()) {
					Ext.Msg.alert('系统提示', '请按要求填写表格！');
					return;
				}
				studentForm.submit({
					waitTitle: '系统提示',
					waitMsg: '保存中......',
					success: function (form, action) {
						if (!action.result.success) {
							Ext.Msg.alert('系统提示', action.result.msg);
						} else {
							Ext.getCmp('studentWin').hide();
							southPanel.getStore().reload();
							Ext.Msg.alert('系统提示', '保存成功！');
						}
					},
					failure: function (form, action) {
						Ext.Msg.alert('系统提示', action.result.msg);
					}
				});
			}
		}, {
			text: '取消',
			handler: function () {
				studentPop.hide();
			}
		}
	]
});

var southStore = Ext.create('Ext.data.JsonStore', {
	storeId: 'southStore',
	pageSize: itemsPerPage, // 每页显示条数
	totalProperty: 'result',
	fields: ['id', 'gradeId', 'mobile', 'password', 'name', 'age', 'sex', 'email'],
	proxy: {
		type: 'ajax',
		url: path + '/grade/gradeStudent',
		reader: {
			totalProperty: 'result',
			root: 'rows'
		}
	}
});


var southPaging = Ext.create('Ext.toolbar.Paging', { //TODO:无法分页啊???
	store: southStore,
	displayInfo: true,
	displayMsg: '第{0}-{1}条，共{2}条',
	emptyMsg: "没有数据",
	beforePageText: '第',
	afterPageText: '页，共 {0} 页'
});

var centerPanel = Ext.create('Ext.grid.Panel', {
	region: 'center',
	title: '班级列表',
	columns: [
		{header: '班级ID', dataIndex: 'id', width: 100, sortable: true},
		{header: '班级名称', dataIndex: 'name', width: 100},
		{header: '班级描述', dataIndex: 'description', width: 100},
		{header: '班级学年', dataIndex: 'gradeYear', width: 100},
	],
	selModel: {
		listeners: {
			select: function (rowModel, record, index, eOpts) {
				southStore.load({params: {gradeId: record.data.id,start:0,limit:itemsPerPage,page:1}});
			}
		},
		mode: 'MULTI'
	},
	store: Ext.create('Ext.data.JsonStore', {
		autoLoad: true,
		storeId: 'centerStore',
		pageSize: 5, // 每页显示条数
		fields: ['id', 'name', 'gradeYear', 'description'],
		proxy: {
			type: 'ajax',
			url: path + '/grade/list',
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
			text: '增加班级',
			handler: function () {
				Ext.getCmp('gradeForm').getForm().reset();
				gradePop.setTitle('增加班级');
				gradePop.show();
			}
		},
		{
			xtype: 'button',
			text: '修改班级',
			handler: function () {
				var models = centerPanel.getSelectionModel().getSelection();
				if (models.length <= 0) {
					Ext.Msg.alert('系统提示', '请选择要编辑的数据');
					return;
				}
				gradePop.setTitle('编辑');
				gradePop.show();
				Ext.getCmp('gradeForm').loadRecord(models[0]);
			}

		},
		{
			xtype: 'button',
			text: '删除班级',
			handler: function () {
				var grade = centerPanel.getSelectionModel().getSelection();
				if (grade.length == 0) {
					Ext.Msg.alert('系统提示', '请选择要删除的班级。');
					return;
				}
				Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
					if ('yes' === option) {
						Ext.Ajax.request({
							url: path + '/grade/delGrade?id=' + grade[0].data.id,
							scope: this,
							async: true,
							success: function (response, options) {
								Ext.Msg.alert('系统提示', Ext.decode(response.responseText).msg);
								centerPanel.getStore().reload();
								southPanel.getStore().reload();
							},
							failure: function (form, action) {
								Ext.Msg.alert('系统提示', action.result.msg);
							}
						});
					}
				});
			}
		}
	],
	bbar: Ext.create('Ext.toolbar.Paging', {
		store: Ext.data.StoreManager.get('centerStore'),
		displayInfo: true,
		displayMsg: '第{0}-{1}条，共{2}条',
		emptyMsg: "没有数据",
		beforePageText: '第',
		afterPageText: '页，共 {0} 页'
	})
});

var southPanel = Ext.create('Ext.grid.Panel', {
	region: 'south',
	title: '学生列表',
	height: 380,
	split: true,
	collapsible: true,
	columns: [
		{header: '班级ID', align: 'center', width: 50, dataIndex: 'gradeId'},
		{header: '学生ID', align: 'center', width: 50, dataIndex: 'id'},
		{header: '手机号', align: 'center', width: 150, dataIndex: 'mobile'},
		{header: '密码', align: 'center', width: 200, dataIndex: 'password', hidden: true},
		{header: '姓名', align: 'center', width: 100, dataIndex: 'name'},
		//{header: '班级', align: 'center', width: 200, dataIndex: 'class'},
		{header: '年龄', align: 'center', width: 200, dataIndex: 'age'},
		{header: '性别', dataIndex: 'sex', width: 80, renderer: function (value) {
			if (value == 0) {
				return "女";
			} else if (value == 1) {
				return "男";
			}  else {
				return value;
			}
		}},
		{header: '邮箱', align: 'center', width: 200, dataIndex: 'email'}
	],
	store: southStore,
	tbar: [
		{	//计划列表表头添加按钮
			xtype: 'button',
			text: '增加学生',
			handler: function () {
				var records = centerPanel.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert('系统提示', '请选择班级');
					return;
				}
				_gradeid = records[0].data.id;
				Ext.getCmp('studentForm').getForm().reset();
				Ext.getCmp('studentWin').setTitle('添加');
				Ext.getCmp('studentWin').show();
				Ext.getCmp('gradeId').setValue(_gradeid);
				Ext.getCmp('studentForm').getForm().findField('gradeId').setReadOnly(true);
			}
		},
		{
			xtype: 'button',
			text: '编辑学生',
			handler: function () {
				var models = southPanel.getSelectionModel().getSelection();
				if (models.length <= 0) {
					Ext.Msg.alert('系统提示', '请选择要编辑的数据');
					return;
				}
				studentPop.setTitle('编辑');
				studentPop.show();
				Ext.getCmp('studentForm').loadRecord(models[0]);
				Ext.getCmp('studentForm').getForm().findField('gradeId').setReadOnly(false);
			}

		}, {
			xtype: 'button',
			text: '删除学生',
			handler: function () {
				var records = southPanel.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert('系统提示', '请选择要删除的学生。');
					return;
				}
				Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
					if ('yes' === option) {
						Ext.Msg.confirm('系统提示', '删除学生,将会删除学生下所有关联的知识包,确认要删除吗?', function (option) {
							if ('yes' === option) {
								Ext.Ajax.request({
									url: path + '/grade/delStudent?id=' + records[0].data.id,
									scope: this,
									async: true,
									success: function (response, options) {
										Ext.Msg.alert('系统提示', Ext.decode(response.responseText).msg);
										centerPanel.getStore().reload();
										southPanel.getStore().reload();
									},
									failure: function (form, action) {
										Ext.Msg.alert('系统提示', action.result.msg);
									}
								});
							}
						})
					}
				});
			}
		},
		'-',
		{
			xtype: 'textfield',
			fieldLabel: '手机号',
			name: 'sMobile',
			id: 'sMobile'
		},
		{
			xtype: 'button',
			text: '查询',
			handler: function () {
				southPanel.getStore().load({url: path + '/grade/getStudent',
					params: {sMobile: Ext.getCmp('sMobile').getValue()}});
			}
		}
	],
	bbar: southPaging
});


southStore.on("beforeload",function(){
	var records = centerPanel.getSelectionModel().getSelection();
	if (records.length == 0) {
		Ext.Msg.alert('系统提示', '请选择班级');
		return;
	}
	Ext.apply(southStore.proxy.extraParams, {gradeId : records[0].data.id});
});

//domReady
Ext.onReady(function () {
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items: [centerPanel, southPanel]
	});
});