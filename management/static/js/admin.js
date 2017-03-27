var _adminid;
var adminPop = Ext.create('Ext.window.Window', {
    id: 'adminWin',
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
            id: 'adminForm',
            url: path + '/admin/saveAdmin',
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
            },{//隐藏域
                name: 'adminId',
                hidden: true
            }, {

                fieldLabel: '用户账号',
                name: 'uid',
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
                    fieldLabel: '手机号',
                    name: 'mobile',
                    allowBlank: false
                }]
        })
    ],
    buttons: [
        {
            text: '保存',
            handler: function () {

                if (!Ext.getCmp('adminForm').getForm().isValid()) {
                    Ext.Msg.alert('系统提示', '请按要求填写表格！');
                    return;
                }
                Ext.getCmp('adminForm').adminId = adminID;
                Ext.getCmp('adminForm').submit({
                    waitTitle: '系统提示',
                    waitMsg: '保存中......',
                    success: function (form, action) {
                        Ext.getCmp('adminWin').hide();
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
            url: path + '/admin/saveAdminRoleMember',
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
            }, {
                xtype: 'combo',
                name: 'roleid',
                fieldLabel: '角色',
                store: Ext.create('Ext.data.JsonStore', {
                    storeId: 'role',
                    autoLoad: true,
                    fields: ['id', 'name'],
                    proxy: {
                        type: 'ajax',
                        url: path + '/admin/rolelist',
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
                Ext.Msg.alert('保存:adminid',_adminid);
                Ext.getCmp('adminid').setValue(_adminid);
                var roleForm = Ext.getCmp('roleForm').getForm();
                if (!roleForm.isValid()) {
                    return;
                }

                roleForm.submit({
                    waitTitle: '系统提示',
                    waitMsg: '保存中......',
                    success: function (form, action) {
                        if (!action.result.success) {
                            Ext.Msg.alert('系统提示', action.result.msg);
                        } else {
                            Ext.getCmp('roleWin').hide();
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
                rolePop.hide();
            }
        }
    ]
});

var centerPanel = Ext.create('Ext.grid.Panel', {
    region: 'center',
    title: '用户列表',
    columns: [
        {header: '用户ID', dataIndex: 'id', width: 100, sortable: true},
        {header: '用户账号', dataIndex: 'uid', width: 100},
        {header: '用户密码', dataIndex: 'password', width: 200},
        {header: '姓名', dataIndex: 'name', width: 100},
        {header: '手机号', dataIndex: 'mobile', width: 100},
        {header: '用户添加人', dataIndex: 'createBy', width: 100},
        {header: '用户添加时间', dataIndex: 'createTime', width: 200}
    ],
    selModel: {
        listeners: {
            select: function (rowModel, record, index, eOpts) {
                southPanel.getStore().load({params: {adminid: record.data.id}});
            }
        },
        mode: 'MULTI'
    },
    store: Ext.create('Ext.data.JsonStore', {
        autoLoad: true,
        storeId: 'centerStore',
        fields: ['id', 'uid', 'password', 'name', 'mobile', 'createBy', 'createTime'],
        proxy: {
            type: 'ajax',
            url: path + '/admin/list',
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
            text: '增加用户',
            handler: function () {
                Ext.getCmp('adminForm').getForm().reset();
                adminPop.setTitle('增加用户');
                adminPop.show();
            }
        },
        {
            xtype: 'button',
            text: '修改用户',
            handler: function () {
                Ext.getCmp('adminForm').getForm().reset();
                adminPop.setTitle('修改用户');
                adminPop.show();
            }
        },
        {
            xtype: 'button',
            text: '删除用户',
            handler: function () {
                var admin = centerPanel.getSelectionModel().getSelection();
                if (admin.length == 0) {
                    Ext.Msg.alert('系统提示', '请选择要删除的用户。');
                    return;
                }
                Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
                    if ('yes' === option) {
                        Ext.Ajax.request({
                            url: path + '/admin/delAdmin?id=' + admin[0].data.id,
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

function delAdminRole(records) {
    Ext.Ajax.request({
        url: path + '/admin/delAdminRole?id=' + records[0].get('id'),
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

var southPanel = Ext.create('Ext.grid.Panel', {
    region: 'south',
    title: '角色列表',
    height: 260,
    split: true,
    collapsible: true,
    columns: [
        {header: '角色ID', align: 'center', width: 100, dataIndex: 'id'},
        {header: '角色名称', align: 'center', width: 200, dataIndex: 'name'},
        {header: '角色描述', align: 'center', width: 300, dataIndex: 'description'}
    ],
    store: Ext.create('Ext.data.JsonStore', {
        storeId: 'southStore',
        fields: ['id', 'name', 'description'],
        proxy: {
            type: 'ajax',
            url: path + '/admin/adminrole',
            reader: {
                totalProperty: 'results',
                root: 'rows'
            }
        }
    }),
    tbar: [
        {	//计划列表表头添加按钮
            xtype: 'button',
            text: '增加角色',
            handler: function () {
                var admins = centerPanel.getSelectionModel().getSelection();
                if (admins.length == 0) {
                    Ext.Msg.alert('系统提示', '请选择账号');
                    return;
                }
                _adminid = admins[0].data.id;
                Ext.getCmp('roleForm').getForm().reset();
                Ext.getCmp('roleWin').setTitle('添加');
                Ext.getCmp('roleWin').show();
            }
        }, '-', {
            xtype: 'button',
            text: '删除角色',
            handler: function () {
                var roles = southPanel.getSelectionModel().getSelection();
                if (roles.length == 0) {
                    Ext.Msg.alert('系统提示', '请选择要删除的角色。');
                    return;
                }
                Ext.Msg.confirm('系统提示', '您确认要删除吗?', function (option) {
                    if ('yes' === option) {
                        delAdminRole(roles);
                    }
                });
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