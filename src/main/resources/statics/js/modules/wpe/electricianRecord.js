Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);
Vue.component('myBtn', myBtnT);

let electricianRecordapp = new Vue({
    el: '#electricianRecordapp',
    data() {
        return {
            defaultSort: {prop: 'workday', order: 'descending'},
            queryForm: {
                //orderBy: 'm.update_time-desc',
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                userId: null,
                projectId: null,
                status: null
            },
            tableColumns: [
                {
                    name: 'user.username',
                    label: '用户'
                },
                {
                    name: 'project.projectName',
                    label: '工程名称'
                },
                {
                    name: 'project.addr',
                    label: '地址'
                },
                {
                    width: '140',
                    name: 'workday',
                    date: 'yyyy-MM-dd',
                    sortable: 'custom',
                    sortBy: 'm.workday',
                    label: '工作日'
                },
                {
                    name: 'workStart',
                    label: '工作开始时间'
                },
                {
                    name: 'workEnd',
                    label: '工作日结束时间'
                },
                {
                    name: 'remark',
                    label: '备注'
                },
                {
                    name: 'status',
                    dict: 'status',
                    label: '工作状态'
                },
                {
                    name: 'labourCost',
                    label: '工价'
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
                    width: '180px',
                    buttons: [
                        {
                            auth:'sys:electricianRecord:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:electricianRecord:delFlag',
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
                electricianRecordFormLabelWidth: '200px',
                electricianRecordFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                electricianRecord:{
                    userId: null,
                    projectId: null,
                    workday: null,
                    workStart: null,
                    workEnd: null,
                    remark: null,
                    status: 'Y',
                    labourCost: 150
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
            that.addOrUpdateForm.electricianRecordFormVisible = true;
            that.addOrUpdateForm.electricianRecord = {
                                             userId: null,
                                             projectId: null,
                                             workday: null,
                                             workStart: null,
                                             workEnd: null,
                                             remark: null,
                                             status: null,
                                             labour_cost: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.electricianRecordFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "wpe/electrician/record/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.electricianRecord = result.data;
                  }
            });
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.electricianRecord.id == null ? "wpe/electrician/record/add" : "wpe/electrician/record/update";
                    let type = that.addOrUpdateForm.electricianRecord.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.electricianRecord)}, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.electricianRecordFormVisible = false;
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
                httpUtil.del({url: "wpe/electrician/record/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "wpe/electrician/record/list", data: that.queryForm}, function (result) {
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
                keyword: that.queryForm.keyword,
                userId: that.queryForm.userId,
                projectId: that.queryForm.projectId,
                status: that.queryForm.status
            }
            httpUtil.fileDownload(that, {url: "wpe/electrician/record/list/export",data: data});
        }
    }
});
