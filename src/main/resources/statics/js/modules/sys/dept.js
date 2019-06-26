Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
let deptapp = new Vue({
    el: '#deptapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            deptData: [],
            areaData: [],
            areaModel: [],
            deptModel: [],
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                parentId: null,
                areaId: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'sysArea.name',
                    sortable: 'custom',
                    sortBy:'a.name',
                    label: '城市'
                },
                {
                    name: 'name',
                    label: '部门名称'
                },
                {
                    name: 'code',
                    label: '部门编码'
                },
                {
                    name: 'level',
                    sortable: 'custom',
                    sortBy:'m.level',
                    label: '级别'
                },
                {
                    name: 'type',
                    dict: 'dept_type',
                    label: '类型'
                },
                {
                    name: 'sequence',
                    sortable: 'custom',
                    sortBy:'m.sequence',
                    label: '排序'
                },
                {
                    name: 'enabled',
                    dict: 'enabled',
                    label: '是否可用'
                },
                {
                    name: 'description',
                    label: '描述'
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
                            auth:'sys:dept:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:dept:delFlag',
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
                deptFormLabelWidth: '80px',
                deptFormVisible: false,
                dept:{
                    name: null,
                    areaId: null,
                    level: null,
                    type: null,
                    parentId: null,
                    parentIds: null,
                    sequence: null,
                    description: null,
                    enabled: null
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.initTree();
        this.initArea();
    },
    methods: {
        sortchange(val){
            let that = this;
            that.queryForm.orderBy=val.sortBy;
            that.myQuery();
        },
        initTree() {
            let that = this;
            httpUtil.get({url: "sys/dept/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.deptData = treeUtil.vueTree(result.data.list);
                }
            });
        },
        initArea() {
            let that = this;
            httpUtil.get({url: "sys/area/list", data: {level:3}}, function (result) {
                if (result.code == 0) {
                    that.areaData = areaUtil.vueTree(result.data.list);
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
            that.eareModel = [];
            that.deptModel = [];
            that.queryForm.parentId = null;
            that.queryForm.areaId = null;
            that.resetForm('queryFormRef');
            that.myQuery();
        },
        myQuery() {
            let that = this;
            that.loadData();
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
        //父级选择处理
        areaModelQueryChange(val) {
            let that = this;
            if (val.length > 0) {
                that.queryForm.areaId = val[val.length - 1];
            } else {
                that.queryForm.areaId = null;
            }
        },
        //父级选择处理
        deptModelQueryChange(val) {
            let that = this;
            if (val.length > 0) {
                that.queryForm.parentId = val[val.length - 1];
            } else {
                that.queryForm.parentId = null;
            }
        },
        myAdd() {
            let that = this;
            that.addOrUpdateForm.title = '新增';
            that.addOrUpdateForm.deptFormVisible = true;
            that.addOrUpdateForm.deptModel = [];
            that.addOrUpdateForm.areaModel = [];
            that.addOrUpdateForm.dept = {
                name: null,
                areaId: null,
                level: null,
                type: null,
                parentId: null,
                parentIds: null,
                sequence: null,
                description: null,
                enabled: null
            }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.deptFormVisible = true;
            that.addOrUpdateForm.deptModel = [];
            that.addOrUpdateForm.areaModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/dept/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    let parentids = result.data.parentIds;
                    that.addOrUpdateForm.areaModel = result.data.sysArea ? result.data.sysArea.parentIds.split(',') : [];
                    if(that.addOrUpdateForm.areaModel.length>0){
                        that.addOrUpdateForm.areaModel.push(result.data.areaId);
                    }
                    that.addOrUpdateForm.deptModel = parentids ? parentids.split(',') : [];
                    that.addOrUpdateForm.dept = result.data;
                    that.deptData = treeUtil.updateTreeNodeDisable(that.deptData, row.id, true);
                }
            });
        },
        //父级选择处理
        deptModelFormChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.dept.level = val.length + 1;
                that.addOrUpdateForm.dept.parentId = val[val.length - 1];
                that.addOrUpdateForm.dept.parentIds = val.join(',');
            } else {
                that.addOrUpdateForm.dept.level = 1;
                that.addOrUpdateForm.dept.parentId = null;
                that.addOrUpdateForm.dept.parentIds = null;
            }
        },
        //父级选择处理
        areaModelFormChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.dept.areaId = val[val.length - 1];
            } else {
                that.addOrUpdateForm.dept.areaId = null;
            }
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.dept.id == null ? "sys/dept/add" : "sys/dept/update";
                    let type = that.addOrUpdateForm.dept.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.dept)}, function (r) {
                        that.myQuery();
                        that.initTree();
                        that.addOrUpdateForm.deptFormVisible = false;
                        alertMsg(that, r);
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
                httpUtil.del({url: "sys/dept/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/dept/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                }
            });
        }
    },
    watch: {
        'addOrUpdateForm.deptFormVisible'(val) {
            let that = this;
            if(!val && that.addOrUpdateForm.dept.id != null){
                that.deptData = treeUtil.updateTreeNodeDisable(that.deptData, that.addOrUpdateForm.dept.id, false);
            }
        }
    }
});
