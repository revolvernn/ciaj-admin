Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myBtn', myBtnT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);

let familyapp = new Vue({
    el: '#familyapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            areaData: [],
            areaModel: [],
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                enabled: null,
                areaId: null
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
                    label: '家庭名称'
                },
                {
                    name: 'code',
                    label: '家庭编码'
                },
                {
                    name: 'enabled',
                    dict: 'enabled',
                    label: '是否可用'
                },
                {
                    name: 'addr',
                    label: '家庭详细地址'
                },
                {
                    name: 'description',
                    label: '家庭描述'
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
                            auth:'my:family:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'my:family:delFlag',
                            label: '删除',
                            icon: 'el-icon-delete',
                            click: this.myDel,
                            type: 'danger'
                        }
                    ]
                }
            ],
            tableLoading: false,
            page: {},
            addOrUpdateForm: {
                title: '新增',
                familyFormLabelWidth: '200px',
                familyFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                family:{
                    name: null,
                    code: null,
                    enabled: null,
                    areaId: null,
                    addr: null,
                    description: null
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
        this.initArea();
    },
    methods: {
        initArea() {
            let that = this;
            httpUtil.get({url: "sys/area/list", data: {level:3}}, function (result) {
                if (result.code == 0) {
                    that.areaData = areaUtil.vueTree(result.data.list);
                }
            });
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
        areaModelFormChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.family.areaId = val[val.length - 1];
            } else {
                that.addOrUpdateForm.family.areaId = null;
            }
        },
        sortchange(val) {
            let that = this;
            that.queryForm.orderBy = val.sortBy;
            that.myQuery();
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
            }
        },
        myQueryReset() {
            let that = this;
            that.areaModel = [];
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
        myAdd() {
            let that = this;
            that.addOrUpdateForm.title = '新增';
            that.addOrUpdateForm.familyFormVisible = true;
            that.addOrUpdateForm.areaModel = [];
            that.addOrUpdateForm.family = {
                                             name: null,
                                             code: null,
                                             enabled: null,
                                             areaId: null,
                                             addr: null,
                                             description: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.familyFormVisible = true;
            that.addOrUpdateForm.areaModel = [];
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "my/family/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.family = result.data;
                      that.addOrUpdateForm.areaModel = result.data.sysArea ? result.data.sysArea.parentIds.split(',') : [];
                      if(that.addOrUpdateForm.areaModel.length>0){
                          that.addOrUpdateForm.areaModel.push(result.data.areaId);
                      }
                  }
            });
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    const loading = that.$loading({
                                            lock: true,
                                            text: 'Loading',
                                            spinner: 'el-icon-loading',
                                            background: 'rgba(0, 0, 0, 0.7)'
                    });
                    let url = that.addOrUpdateForm.family.id == null ? "my/family/add" : "my/family/update";
                    let type = that.addOrUpdateForm.family.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.family)}, function (r) {
                        loading.close();
                        if (r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.familyFormVisible = false;
                        }
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
                httpUtil.del({url: "my/family/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "my/family/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                    that.page.expand = true;
                }
            });
        },
        listExport() {
            let that = this;
            let data =  {
                orderBy: that.queryForm.orderBy,
                orderByEnabled: true,
                pageEnabled: false,
                keyword: that.queryForm.keyword
            }
            httpUtil.fileDownload(that, {url: "my/family/list/export",data: data});
        }
    }
});
