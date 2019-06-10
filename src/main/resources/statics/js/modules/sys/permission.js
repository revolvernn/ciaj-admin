Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
var permissionapp = new Vue({
    el: '#permissionapp',
    data() {
        return {
            filterText: '',
            treeData: [],
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
                    name: 'url',
                    label: '资源路径'
                },
                {
                    name: 'available',
                    dict: 'available',
                    label: '是否有效'
                },
                {
                    name: 'createTime',
                    width:'140',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    width:'140',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '150px',
                    buttons: [
                        {
                            auth:'sys:permission:update',
                            label: '修改',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:permission:delFlag',
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
                permissionFormLabelWidth: '80px',
                cascaderModel:[],
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
            var that = this;
            httpUtil.get({url: "sys/permission/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.treeData = treeUtil.vueTree(result.data.list);
                }
            });
        },
        getTreeNode(data, i, c) {
            var that = this;
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
            var that = this;
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
            var that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.permission.parentId = val[val.length - 1];
                that.addOrUpdateForm.permission.parentIds = val.join(',');
            }else {
                that.addOrUpdateForm.permission.parentId = null;
                that.addOrUpdateForm.permission.parentIds = null;
            }
        },
        myAdd() {
            var that = this;
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
            var that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.permissionFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/permission/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    var parentids = result.data.parentIds;
                    that.addOrUpdateForm.cascaderModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.permission = result.data;
                }
            });
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.permission.id == null ? "sys/permission/add" : "sys/permission/update";
                    var type = that.addOrUpdateForm.permission.id == null ? "POST" : "PUT";
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
            var that = this;
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
            var that = this;
            httpUtil.get({url: "sys/permission/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
    , watch: {
        filterText(val) {
            this.$refs['permissionTree'].filter(val);
        },
        'addOrUpdateForm.permission.permissionCode'(val){
            var that = this;
            var value = val;
            that.addOrUpdateForm.permission.url=value.split(':').join('/');
            console.log(that.addOrUpdateForm.permission.url);
        }
    }
});
