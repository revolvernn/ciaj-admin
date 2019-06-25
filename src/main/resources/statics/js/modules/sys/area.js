Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
let areaapp = new Vue({
    el: '#areaapp',
    data() {
        return {
            treeData: [],
            cascaderModel: [],
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                type: null,
                parentId: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'name',
                    label: '名称'
                },
                {
                    name: 'code',
                    label: '编码'
                },
                {
                    name: 'type',
                    dict: 'area_type',
                    label: '类型'
                },
                {
                    name: 'level',
                    sortable: 'custom',
                    sortBy:'m.level',
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
                    name: 'createTime',
                    sortable: 'custom',
                    width:'140',
                    sortBy:'m.create_time',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    sortable: 'custom',
                    width:'140',
                    sortBy:'m.update_time',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '180px',
                    buttons: [
                        {
                            auth: 'sys:area:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth: 'sys:area:delFlag',
                            label: '删除',
                            icon: 'el-icon-delete',
                            click: this.myDel,
                            type: 'danger'
                        }
                    ]
                }
            ],
            page: {},
            addOrUpdateForm: {
                title: '新增',
                areaFormLabelWidth: '80px',
                areaFormVisible: false,
                area: {
                    name: null,
                    code: null,
                    parentId: null,
                    parentIds: null,
                    type: null,
                    level: null,
                    sequence: 0,
                    enabled: 'Y'
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.initTree();
    },
    methods: {
        sortchange(val){
            let that = this;
            that.queryForm.orderBy=val.sortBy;
            that.myQuery();
        },
        initTree() {
            let that = this;
            httpUtil.get({url: "sys/area/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.treeData = areaUtil.vueTree(result.data.list);
                }
            });
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
            that.cascaderModel = [];
            that.queryForm.parentId = null;
            that.resetForm('queryFormRef');
            that.myQuery();
        },
        myQuery() {
            let that = this;
            that.loadData();
        },
        //父级选择处理
        cascaderQueryChange(val) {
            let that = this;
            if (val.length > 0) {
                that.queryForm.parentId = val[val.length - 1];
            } else {
                that.queryForm.parentId = null;
            }
        },
        pagesizechange(val) {
            let that = this;
            that.queryForm.pageSize = val;
            that.loadData();
        },
        currentpagechange(val) {
            this.queryForm.pageNo = val;
            this.loadData();
        },
        myAdd() {
            let that = this;
            that.addOrUpdateForm.title = '新增';
            that.addOrUpdateForm.areaFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.addOrUpdateForm.area = {
                name: null,
                code: null,
                parentId: null,
                parentIds: null,
                type: null,
                level: null,
                sequence: 0,
                enabled: 'Y'
            }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.areaFormVisible = true;
            that.addOrUpdateForm.cascaderModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/area/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.area = result.data;
                    let parentids = result.data.parentIds;
                    that.addOrUpdateForm.cascaderModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.area = result.data;
                }
            });
        },
        //父级选择处理
        cascaderChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.area.level = val.length + 1;
                that.addOrUpdateForm.area.parentId = val[val.length - 1];
                that.addOrUpdateForm.area.parentIds = val.join(',');
            } else {
                that.addOrUpdateForm.area.level = 1;
                that.addOrUpdateForm.area.parentId = null;
                that.addOrUpdateForm.area.parentIds = null;
            }
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.area.id == null ? "sys/area/add" : "sys/area/update";
                    let type = that.addOrUpdateForm.area.id == null ? "POST" : "PUT";
                    httpUtil.post({
                        url: url,
                        type: type,
                        data: JSON.stringify(that.addOrUpdateForm.area)
                    }, function (r) {
                        that.myQuery();
                        that.initTree();
                        that.addOrUpdateForm.areaFormVisible = false;
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
                httpUtil.del({url: "sys/area/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    that.initTree();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/area/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
    , watch: {
        'addOrUpdateForm.area.name'(val) {
            if(val){
                this.addOrUpdateForm.area.code = pinyin.getFullChars(val);
            }
        }
    }
});
