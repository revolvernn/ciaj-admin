Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
new Vue({
    el: '#roleapp',
    data() {
        return {
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                available: null,
                type: null,
                parentId: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'name',
                    label: '角色名'
                },
                {
                    name: 'code',
                    label: '角色编码'
                },
                {
                    name: 'type',
                    dict: 'role_type',
                    label: '角色类型'
                },
                {
                    name: 'description',
                    label: '角色描述'
                },
                {
                    name: 'available',
                    dict: 'available',
                    label: '是否有效'
                },
                {
                    name: 'createTime',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '250px',
                    buttons: [
                        {
                            auth:'sys:role:update',
                            label: '修改',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:role:permission:rel:adds',
                            label: '分配权限',
                            click: this.myPrivileges,
                            type: 'success'
                        },
                        {
                            auth:'sys:role:clearRoleRel',
                            label: '清空关联',
                            click: this.clearRoleRel,
                            type: 'danger'
                        },
                        {
                            auth:'sys:role:delFlag',
                            label: '删除',
                            click: this.myDel,
                            type: 'danger'
                        }
                    ]
                }
            ],
            page: {},
            addOrUpdateForm: {
                title: '新增',
                roleFormLabelWidth: '80px',
                roleFormVisible: false,
                privilegesVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                role: {
                    name: null,
                    code: null,
                    type: null,
                    description: null,
                    available: null
                },
                privilege: {
                    checkAll: true,
                    defaultProps: {
                        children: 'children',
                        label: 'label',
                        disable: 'disabled'
                    },
                    filterText: '',
                    treeData: []
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
    },
    methods: {
        filterPrivilege(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        initPrivilegeTree() {
            var that = this;
            httpUtil.get({url: "sys/permission/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.privilege.treeData = treeUtil.vueTree(result.data.list);
                    //回显
                    httpUtil.get({
                        url: "sys/role/permission/rel/list",
                        data: {roleId: that.addOrUpdateForm.privilege.roleId}
                    }, function (r) {
                        if (r.code == 0) {
                            that.$refs['privilegeTree'].setCheckedKeys(treeUtil.permissionIds(r.data.list))
                        }
                    });
                }
            });
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
                console.log(e);
            }
        },
        myQueryReset() {
            var that = this;
            that.resetForm('queryFormRef');
            that.myQuery();
        },
        myQuery() {
            this.loadData();
        },
        pagesizechange(val) {
            this.queryForm.pageSize = val;
            this.loadData();
        },
        currentpagechange(val) {
            this.queryForm.pageNo = val;
            this.loadData();
        },
        savePrivileges() {
            var that = this;
            var privilegeIds = that.$refs['privilegeTree'].getCheckedKeys();
            //privilegeIds.push(that.$refs['privilegeTree'].getHalfCheckedKeys());
            var datas = privilegeIds.length == 0 ? [{roleId: that.addOrUpdateForm.privilege.roleId}] : [];
            for (var v in privilegeIds) {
                var data = {};
                data.roleId = that.addOrUpdateForm.privilege.roleId;
                data.permissionId = privilegeIds[v];
                datas.push(data)
            }
            httpUtil.post({
                url: 'sys/role/permission/rel/adds',
                data: JSON.stringify(datas)
            }, function (r) {
                that.addOrUpdateForm.privilegesVisible = false;
                alertMsg(that, r)
            });
        },
        myPrivileges(index, row) {
            var that = this;
            that.addOrUpdateForm.privilegesVisible = true;
            that.addOrUpdateForm.privilege.roleId = row.id;
            that.initPrivilegeTree();
        },
        /**
         * 全选。。
         */
        checkAllPrivilege() {
            var that = this;
            if (that.addOrUpdateForm.privilege.checkAll) {
                var ids = treeUtil.treeToIds(that.addOrUpdateForm.privilege.treeData);
                that.$refs['privilegeTree'].setCheckedKeys(ids)
                that.addOrUpdateForm.privilege.checkAll = false;
            } else {
                that.$refs['privilegeTree'].setCheckedKeys([])
                that.addOrUpdateForm.privilege.checkAll = true;
            }

        },
        myAdd() {
            this.addOrUpdateForm.title = '新增';
            this.addOrUpdateForm.roleFormVisible = true;
            this.addOrUpdateForm.role = {
                name: null,
                code: null,
                type: null,
                description: null,
                available: 'Y'
            };
            this.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            var that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.roleFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            that.addOrUpdateForm.role = row;
            httpUtil.get({url: "sys/role/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.role = result.data;
                }
            });
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.role.id == null ? "sys/role/add" : "sys/role/update";
                    var type = that.addOrUpdateForm.role.id == null ? "POST" : "PUT";
                    httpUtil.post({
                        url: url,
                        type: type,
                        data: JSON.stringify(that.addOrUpdateForm.role)
                    }, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.roleFormVisible = false;
                        alertMsg(that, r)
                    });
                }
            });
        },
        myDel(index, row) {
            var that = this;
            that.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                httpUtil.del({url: "sys/role/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        clearRoleRel(index, row) {
            var that = this;
            that.$confirm('此操作将清空角色关联用户及权限数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                httpUtil.del({url: "sys/role/clearRoleRel/" + row.id}, function (r) {
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            var that = this;
            httpUtil.get({url: "sys/role/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    },
    watch: {
        'addOrUpdateForm.privilege.filterText'(val) {
            this.$refs['privilegeTree'].filter(val);
        }
    }
});