Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
let permissionapp = new Vue({
    el: '#permissionapp',
    data() {
        return {
            filterText: '',
            treeData: [],
            menuData: [],
            defaultProps: {
                children: 'children',
                label: 'label',
                disable: 'disabled'
            },
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                type: null,
                keyword: null,
                parentName: null,
                parentId: null
            },
            tableColumns: [
                {
                    name: 'name',
                    label: '权限名'
                },
                {
                    name: 'description',
                    label: '权限描述'
                },
                {
                    name: 'type',
                    dict: 'permission',
                    label: '权限类型'
                },
                {
                    name: 'permissionCode',
                    label: '权限码'
                },
                {
                    name: 'available',
                    dict: 'available',
                    label: '是否有效'
                },
                {
                    name: 'createTime',
                    width: '140',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    width: '140',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '180px',
                    buttons: [
                        {
                            auth: 'sys:permission:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth: 'sys:permission:delFlag',
                            icon: 'el-icon-delete',
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
                menuModel: [],
                permissionFormLabelWidth: '80px',
                cascaderModel: [],
                permissionFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                permission: {
                    name: null,
                    description: null,
                    type: null,
                    permissionCode: null,
                    url: null,
                    available: 'Y'
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
        this.initTree();
    },
    methods: {
        initTree() {
            let that = this;
            httpUtil.get({url: "sys/permission/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.treeData = treeUtil.vueTree(result.data.list);
                }
            });
        },
        initMenuTree() {
            let that = this;
            if (that.menuData.length == 0)
                httpUtil.get({url: "sys/menu/list", data: {}}, function (result) {
                    if (result.code == 0) {
                        that.menuData = treeUtil.vueTree(result.data.list);
                    }
                });
        },
        getTreeNode(data, i, c) {
            let that = this;
            that.queryForm.parentId = data.id;
            that.queryForm.parentName = data.name;
            that.myQuery();
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
                console.log(e);
            }
        },
        myQueryReset() {
            let that = this;
            that.resetForm('queryFormRef');
            that.queryForm.parentId = null;
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
        //父级选择处理
        cascaderChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.permission.parentId = val[val.length - 1];
                that.addOrUpdateForm.permission.parentIds = val.join(',');
            } else {
                that.addOrUpdateForm.permission.parentId = null;
                that.addOrUpdateForm.permission.parentIds = null;
            }
        },
        //父级选择处理
        menuCascaderChange(val) {
            let that = this;

            if (val.length > 0) {
                let menuId = val[val.length - 1];
                let menu = treeUtil.getTreeNode(that.menuData, menuId);
                if (menu !=null  && menu.type !== '1') {
                    that.addOrUpdateForm.menuModel = [];
                    alertMsg(that, {code: -1, msg: '当前选择不是菜单'})
                } else {
                    that.addOrUpdateForm.permission.url = menuId;
                }
            } else {
                that.addOrUpdateForm.permission.url = null;
            }
        },

        myAdd() {
            let that = this;
            that.initMenuTree();
            that.addOrUpdateForm.title = '新增';
            that.addOrUpdateForm.permissionFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.addOrUpdateForm.permission = {
                name: null,
                description: null,
                type: null,
                permissionCode: null,
                url: null,
                available: 'Y'
            };
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.initMenuTree();
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.permissionFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/permission/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    let parentids = result.data.parentIds;
                    that.addOrUpdateForm.cascaderModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.permission = result.data;
                    if (result.data.menu) {
                        that.addOrUpdateForm.menuModel = result.data.menu.parentIds ? result.data.menu.parentIds.split(',') : [];
                        that.addOrUpdateForm.menuModel.push(result.data.menu.id);
                    }
                    that.treeData = treeUtil.updateTreeNodeDisable(that.treeData, that.addOrUpdateForm.permission.id, true);
                }
            });
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.permission.id == null ? "sys/permission/add" : "sys/permission/update";
                    let type = that.addOrUpdateForm.permission.id == null ? "POST" : "PUT";
                    httpUtil.post({
                        url: url,
                        type: type,
                        data: JSON.stringify(that.addOrUpdateForm.permission)
                    }, function (r) {
                        that.myQuery();
                        that.initTree();
                        that.addOrUpdateForm.permissionFormVisible = false;
                        alertMsg(that, r)
                    });
                }
            });
        },
        myDel(index, row) {
            let that = this;
            that.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                httpUtil.del({url: "sys/permission/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    that.initTree();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/permission/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                    that.page.expand = true;
                }
            });
        }
    }
    , watch: {
        filterText(val) {
            this.$refs['permissionTree'].filter(val);
        },
        'addOrUpdateForm.permission.permissionCode'(val) {
            let that = this;
            let value = val;
            if (that.addOrUpdateForm.permission.type !== 'menu')
                that.addOrUpdateForm.permission.url = value.split(':').join('/');
        },
        'addOrUpdateForm.permissionFormVisible'(val) {
            let that = this;
            if(!val && that.addOrUpdateForm.permission.id != null){
                that.treeData = treeUtil.updateTreeNodeDisable(that.treeData, that.addOrUpdateForm.permission.id, false);
            }
        }
    }
});
