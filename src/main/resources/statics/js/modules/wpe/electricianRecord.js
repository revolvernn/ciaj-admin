Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);
Vue.component('myBtn', myBtnT);

let electricianRecordapp = new Vue({
    el: '#electricianRecordapp',
    data() {
        return {
            queryForm: {
                orderBy: 'update_at-asc',
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
                    name: 'workday',
                    label: '工作日',
                    date: 'yyyy-MM-dd'
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
                    name: 'createTime',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
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
                    status: 'Y'
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
                                             status: null
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
                }
            });
        }
    }
});
