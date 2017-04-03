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
					displayField: 'name',
					valueField: 'id'
				},
				{
					fieldLabel: '年龄',
					name: 'age',
					allowBlank: false
				},
				{
					fieldLabel: '性别',
					name: 'sex',
					allowBlank: false
				},
				{
					fieldLabel: '邮箱',
					name: 'email',
					allowBlank: false
				}]
		})
	],
	buttons: [
		{
			text: '保存',
			handler: function () {
				Ext.getCmp('gradeid').setValue(_gradeid);
				var studentForm = Ext.getCmp('studentForm').getForm();
				if (!studentForm.isValid()) {
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
				southPanel.getStore().load({params: {gradeid: record.data.id}});
			}
		},
		mode: 'MULTI'
	},
	store: Ext.create('Ext.data.JsonStore', {
		autoLoad: true,
		storeId: 'centerStore',
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
				Ext.getCmp('gradeForm').getForm().findField('uid').setReadOnly(true);
				Ext.getCmp('gradeForm').getForm().findField('password').setValue();
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
							url: path + '/grade/delgrade?id=' + grade[0].data.id,
							scope: this,
							async: true,
							success: function (response, options) {
								Ext.Msg.alert("系统提示", "删除成功");
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
	]
});

var southPanel = Ext.create('Ext.grid.Panel', {
	region: 'south',
	title: '学生列表',
	height: 260,
	split: true,
	collapsible: true,
	columns: [
		{header: '班级ID', align: 'center', width: 100, dataIndex: 'gradeId'},
		{header: '学生ID', align: 'center', width: 100, dataIndex: 'id'},
		{header: '手机号', align: 'center', width: 200, dataIndex: 'mobile'},
		{header: '密码', align: 'center', width: 200, dataIndex: 'password'},
		{header: '姓名', align: 'center', width: 200, dataIndex: 'name'},
		{header: '班级', align: 'center', width: 200, dataIndex: 'class'},
		{header: '年龄', align: 'center', width: 200, dataIndex: 'age'},
		{header: '性别', align: 'center', width: 200, dataIndex: 'sex'},
		{header: '邮箱', align: 'center', width: 200, dataIndex: 'email'}
	],
	store: Ext.create('Ext.data.JsonStore', {
		storeId: 'southStore',
		fields: ['id', 'gradeId', 'mobile', 'password', 'name', 'class', 'age', 'sex', 'email'],
		proxy: {
			type: 'ajax',
			url: path + '/grade/gradeStudent',
			reader: {
				totalProperty: 'results',
				root: 'rows'
			}
		}
	}),
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
			}
		},
		{
			xtype: 'button',
			text: '编辑学生',
			handler: function () {
				var models = centerPanel.getSelectionModel().getSelection();
				if (models.length <= 0) {
					Ext.Msg.alert('系统提示', '请选择要编辑的数据');
					return;
				}
				studentPop.setTitle('编辑');
				studentPop.show();
				Ext.getCmp('studentForm').loadRecord(models[0]);
				Ext.getCmp('studentForm').getForm().findField('name').setReadOnly(true);
			}

		}, {
			xtype: 'button',
			text: '删除学生',
			handler: function () {
				var records = southPanel.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert('系统提示', '请选择要删除的知识点。');
					return;
				}
				Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
					if ('yes' === option) {
						Ext.Ajax.request({
							url: path + '/grade/delstudent?id=' + records[0].data.id,
							scope: this,
							async: true,
							success: function (response, options) {
								Ext.Msg.alert("系统提示", "删除成功");
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
					params: {title: Ext.getCmp('sMobile').getValue()}});
				southPanel.clearValue();
			}
		}
	]
});

//domReady
Ext.onReady(function () {
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items: [centerPanel, southPanel]
	});
});