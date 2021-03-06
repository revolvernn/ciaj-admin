Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
let dictapp = new Vue({
    el: '#dictapp',
    data() {
        return {
            filterText: '',
            dictData: [],
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
                parentName: null,
                parentId: null
            },
            tableColumns: [
                {
                    name: 'code',
                    icon: true,
                    label: '图标'
                },
                {
                    name: 'name',
                    label: '字典名称'
                },
                {
                    name: 'code',
                    label: '字典编码'
                },
                {
                    name: 'type',
                    label: '类型'
                },
                {
                    name: 'level',
                    label: '级别'
                },
                {
                    name: 'sequence',
                    label: '排序'
                },
                {
                    name: 'enabled',
                    dict: 'enabled',
                    label: '启用'
                },
                {
                    name: 'description',
                    label: '描述'
                },
                {
                    name: 'createTime',
                    sortable: 'custom',
                    width: '140',
                    sortBy: 'm.create_time',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    sortable: 'custom',
                    width: '140',
                    sortBy: 'm.update_time',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    fixed: 'right',
                    width: '180px',
                    buttons: [
                        {
                            auth: 'sys:dict:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth: 'sys:dict:delFlag',
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
                dictFormLabelWidth: '80px',
                dictFormVisible: false,
                parentIds: [],
                dict: {
                    name: null,
                    code: null,
                    parentId: null,
                    parentIds: null,
                    type: null,
                    sequence: null,
                    description: null,
                    enabled: 'Y'
                }
            },
            rules: {
                name: [{required: true, message: '必填', trigger: 'blur'}],
                code: [{required: true, message: '必填', trigger: 'blur'}],
                type: [{required: true, message: '必填', trigger: 'blur'}],
                enabled: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.initTree();
    },
    methods: {
        sortchange(val) {
            let that = this;
            that.queryForm.orderBy = val.sortBy;
            that.myQuery();
        },
        initTree() {
            let that = this;
            httpUtil.get({url: "sys/dict/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.dictData = treeUtil.vueTree(result.data.list);
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
        myAdd() {
            this.addOrUpdateForm.title = '新增';
            this.addOrUpdateForm.dictFormVisible = true;
            this.addOrUpdateForm.cascaderModel = [];
            this.addOrUpdateForm.dict = {
                name: null,
                code: null,
                parentId: null,
                type: null,
                description: null,
                sequence: 0,
                enabled: 'Y'
            };
            this.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.dictFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/dict/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    let parentids = result.data.parentIds;
                    that.addOrUpdateForm.cascaderModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.dict = result.data;
                    that.dictData = treeUtil.updateTreeNodeDisable(that.dictData, that.addOrUpdateForm.dict.id, true);
                }
            });
        },
        //父级选择处理
        cascaderChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.dict.level = val.length + 1;
                that.addOrUpdateForm.dict.parentId = val[val.length - 1];
                that.addOrUpdateForm.dict.parentIds = val.join(',');
            } else {
                that.addOrUpdateForm.dict.level = 1;
                that.addOrUpdateForm.dict.parentId = null;
                that.addOrUpdateForm.dict.parentIds = null;
            }
        },
        //添加或更新
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.dict.id == null ? "sys/dict/add" : "sys/dict/update";
                    let type = that.addOrUpdateForm.dict.id == null ? "POST" : "PUT";
                    httpUtil.post({
                        url: url,
                        type: type,
                        data: JSON.stringify(that.addOrUpdateForm.dict)
                    }, function (r) {
                        localStorageExports.remove(T.local_key.dict_key_prefix + that.addOrUpdateForm.dict.type);
                        that.myQuery();
                        that.initTree();
                        that.addOrUpdateForm.dictFormVisible = false;
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
                httpUtil.del({url: "sys/dict/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    that.initTree();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/dict/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                    that.page.expand = true;
                }
            });
        },
    }
    , watch: {
        filterText(val) {
            this.$refs['dictTree'].filter(val);
        },
        'addOrUpdateForm.dictFormVisible'(val) {
            let that = this;
            if(!val && that.addOrUpdateForm.dict.id != null){
                that.dictData = treeUtil.updateTreeNodeDisable(that.dictData, that.addOrUpdateForm.dict.id, false);
            }
        }
    }
});
