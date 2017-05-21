var _studentid;
var relaPop = Ext.create('Ext.window.Window', {
	id: 'relaWin',
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
			id: 'relaForm',
			url: path + '/rela/saveStudentPack',
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
				name: 'studentid',
				id: 'studentid',
				hidden: true
			}, {
				xtype: 'combo',
				name: 'packid',
				fieldLabel: '知识包',
				editable: false,
				allowBlank: false,
				store: Ext.create('Ext.data.JsonStore', {
					storeId: 'pack',
					autoLoad: true,
					fields: ['id', 'name'],
					proxy: {
						type: 'ajax',
						url: path + '/pack/list',
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
			handler: function () {
				Ext.getCmp('studentid').setValue(_studentid);
				var relaForm = Ext.getCmp('relaForm').getForm();
				if (!relaForm.isValid()) {
					Ext.Msg.alert('系统提示', '请按要求填写表格！');
					return;
				}
				relaForm.submit({
					waitTitle: '系统提示',
					waitMsg: '保存中......',
					success: function (form, action) {
						if (!action.result.success) {
							Ext.Msg.alert('系统提示', action.result.msg);
						} else {
							Ext.getCmp('relaWin').hide();
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
				relaPop.hide();
			}
		}
	]
});

var centerPanel = Ext.create('Ext.grid.Panel', {
	region: 'center',
	title: '学生列表',
	columns: [
		{header: '班级ID', dataIndex: 'gradeId', width: 100},
		{header: '学生ID', dataIndex: 'id', width: 100, sortable: true},
		{header: '手机号', dataIndex: 'mobile', width: 100},
		{header: '姓名', dataIndex: 'name', width: 100}
	],
	store: Ext.create('Ext.data.JsonStore', {
		storeId: 'centerStore',
		fields: ['gradeId', 'id', 'name', 'mobile'],
		proxy: {
			type: 'ajax',
			//url: path + '/rela/stuPackRela',
			reader: {
				totalProperty: 'results',
				root: 'rows'
			}
		}
	}),
	selModel: {
		listeners: {
			select: function (rowModel, record, index, eOpts) {
				southPanel.getStore().load({params: {studentid: record.data.id}});
				eastPanel.getStore().load({params: {studentid: record.data.id}});
			}
		},
		mode: 'MULTI'
	},
	tbar: [
		{
			xtype: 'textfield',
			fieldLabel: '手机号/姓名',
			name: 'sMobile',
			id: 'sMobile'
		},
		{
			xtype: 'button',
			text: '查询',
			handler: function () {
				centerPanel.getStore().load({url: path + '/rela/getStudent',
					params: {sMobile: Ext.getCmp('sMobile').getValue()}});
			}
		}
	]
});

var southPanel = Ext.create('Ext.grid.Panel', {
	region: 'south',
	title: '知识包列表',
	height: 400,
	split: true,
	collapsible: true,
	columns: [
		{header: '学生ID', align: 'center', width: 100, dataIndex: 'studentId'},
		{header: '知识包ID', align: 'center', width: 100, dataIndex: 'packId'},
		{header: '知识包名称', align: 'center', width: 100, dataIndex: 'packName'},
		{header: '知识点总个数', align: 'center', width: 200, dataIndex: 'knowledgeNum'},
		{header: '已掌握知识点总个数', align: 'center', width: 200, dataIndex: 'learnedNum'},
		{header: '最后一次学习时间', align: 'center', width: 200, dataIndex: 'lastLearnTime', renderer: function (value) {
			var now = new Date(parseInt(value));
			return now.toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
		}}
	],
	store: Ext.create('Ext.data.JsonStore', {
		storeId: 'southStore',
		fields: ['studentId', 'packId', 'packName', 'knowledgeNum', 'learnedNum', 'lastLearnTime'],
		proxy: {
			type: 'ajax',
			url: path + '/rela/stuPackRela',
			reader: {
				totalProperty: 'results',
				root: 'rows'
			}
		}
	}),
	tbar: [
		{	//计划列表表头添加按钮
			xtype: 'button',
			text: '增加知识包',
			handler: function () {
				var records = centerPanel.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert('系统提示', '请选择学生');
					return;
				}
				_studentid = records[0].data.id;
				Ext.getCmp('relaForm').getForm().reset();
				Ext.getCmp('relaWin').setTitle('添加');
				Ext.getCmp('relaWin').show();
			}
		},
		{
			xtype: 'button',
			text: '删除关联知识包',
			handler: function () {
				var records = southPanel.getSelectionModel().getSelection();
				if (records.length == 0) {
					Ext.Msg.alert('系统提示', '请选择要删除的知识包。');
					return;
				}
				Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
					Ext.Msg.confirm('系统提示', '删除之后无法恢复,您真的确认要删除吗?', function (option) {
						Ext.Msg.confirm('系统提示', '删除之后学生对于该学习包需要从头开始学习,您真的确认一定要删除吗?', function (option) {
							if ('yes' === option) {
								Ext.Ajax.request({
									url: path + '/rela/delRela?studentId=' + records[0].data.studentId + '&packId=' +records[0].data.packId,
									scope: this,
									async: true,
									success: function (response, options) {
										Ext.Msg.alert("系统提示", "删除成功");
										southPanel.getStore().reload();
									},
									failure: function (form, action) {
										Ext.Msg.alert('系统提示', action.result.msg);
									}
								});
							}
						});
					});
				});
			}
		}
	]
});


var eastPanel = Ext.create('Ext.grid.Panel', {
	region: 'east',
	title: '个人知识点列表',
	width: 350,
	split: true,
	collapsible: true,
	columns: [
		{header: '知识点ID', align: 'center', width: 100, dataIndex: 'knowledgeId'},
		{header: '知识点标题', align: 'center', width: 100, dataIndex: 'name'}, //TODO:关联查询咋搞
		{header: '是否学习完成', align: 'center', width: 200, dataIndex: 'deleted', renderer: function (value) {
			if (value == false) {
				return "未完成";
			} else if (value == true) {
				return "已完成";
			}  else {
				return value;
			}
		}}
	],
	store: Ext.create('Ext.data.JsonStore', {
		storeId: 'eastStore',
		fields: ['knowledgeId', 'name', 'deleted'],
		proxy: {
			type: 'ajax',
			url: path + '/rela/stuKnowRela',
			reader: {
				totalProperty: 'results',
				root: 'rows'
			}
		}
	})
});

//domReady
Ext.onReady(function () {
	Ext.create('Ext.container.Viewport', {
		layout: 'border',
		items: [centerPanel, southPanel, eastPanel ]
	});
});