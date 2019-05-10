Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
var menuapp = new Vue({
    el: '#menuapp',
    data() {
        return {
            filterText: '',
            treeData: [],
            defaultProps: {
                children: 'children',
                label: 'label',
                disable: 'disabled'
            },
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                type: null,
                enabled: null,
                parentName: null,
                parentId: null
            },
            tableColumns: [
                {
                    name: 'name',
                    label: '菜单名称'
                },
                {
                    name: 'url',
                    label: '菜单URL'
                },
                {
                    name: 'permissionCode',
                    label: '权限码'
                },
                {
                    name: 'type',
                    dict:'menu_type',
                    label: '类型'
                },
                {
                    name: 'icon',
                    label: '图标'
                },
                {
                    name: 'sequence',
                    label: '排序'
                },
                {
                    name: 'enabled',
                    dict: 'enabled',
                    label: '是否启用'
                },
                {
                    name: 'createTime',
                    sortable: 'custom',
                    sortBy:'m.create_time',
                    width:'140',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    sortable: 'custom',
                    sortBy:'m.update_time',
                    width:'140',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width:'150',
                    buttons: [
                        {
                            auth:'sys:menu:update',
                            label: '修改',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:menu:delFlag',
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
                menuFormLabelWidth: '80px',
                menuFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                menu:{
                    name: null,
                    parentId: null,
                    parentIds: null,
                    url: null,
                    permissionCode: null,
                    type: null,
                    icon: null,
                    sequence: 0,
                    enabled:'Y'
                }
            },
            rules: {
                name: [{required: true, message: '必填', trigger: 'blur'}],
                type: [{required: true, message: '必填', trigger: 'blur'}],
            }
        }
    },
    created: function () {
        this.initTree();
    },
    methods: {
        sortchange(val){
            var that = this;
            that.queryForm.orderBy=val.sortBy;
            that.myQuery();
        },
        initTree(){
            var that = this;
            httpUtil.get({url: "sys/menu/list", data: {}}, function (result) {
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
        myAdd() {
            this.addOrUpdateForm.title = '新增';
            this.addOrUpdateForm.menuFormVisible = true;
            this.addOrUpdateForm.cascaderModel = [];
            this.addOrUpdateForm.menu = {
                                             name: null,
                                             url: null,
                                             permissionCode: null,
                                             type: null,
                                             icon: null,
                                            sequence: 0,
                                            enabled:'Y'
                                         }
            this.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            var that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.menuFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/menu/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    var parentids = result.data.parentIds;
                    that.addOrUpdateForm.cascaderModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.menu = result.data;
                }
            });
        },
        //父级选择处理
        cascaderChange(val){
            var that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.menu.level = val.length + 1;
                that.addOrUpdateForm.menu.parentId = val[val.length - 1];
                that.addOrUpdateForm.menu.parentIds = val.join(',');
            }else {
                that.addOrUpdateForm.menu.level = 1;
                that.addOrUpdateForm.menu.parentId = null;
                that.addOrUpdateForm.menu.parentIds = null;
            }
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.menu.id == null ? "sys/menu/add" : "sys/menu/update";
                    var type = that.addOrUpdateForm.menu.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.menu)}, function (r) {
                        that.myQuery();
                        that.initTree();
                        that.addOrUpdateForm.menuFormVisible = false;
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
                httpUtil.del({url: "sys/menu/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    that.initTree();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            var that = this;
            httpUtil.get({url: "sys/menu/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
    , watch: {
        filterText(val) {
            this.$refs['menuTree'].filter(val);
        }
    }
});
