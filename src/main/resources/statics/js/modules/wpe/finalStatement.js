Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myBtn', myBtnT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);

let finalStatementapp = new Vue({
    el: '#finalStatementapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                userId: null
            },
            stat: {
                final:{
                    type: 'final',
                    money: 100
                },
                record: {
                    type: 'record',
                    money: 300
                }
            },
            tableColumns: [
                {
                    name: 'user.username',
                    label: '用户'
                },
                {
                    sortable: 'custom',
                    sortBy: 'm.workday',
                    name: 'day',
                    label: '结算日'
                },
                {
                    name: 'money',
                    label: '款项金额'
                },
                {
                    name: 'remark',
                    label: '备注'
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
                            auth:'sys:final:statement:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:final:statement:delFlag',
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
                finalStatementFormLabelWidth: '200px',
                finalStatementFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                finalStatement:{
                    userId: null,
                    day: null,
                    money: null,
                    remark: null
                }
            },
            rules: {
                userId: [{required: true, message: '请选择用户', trigger: 'blur'}],
                day: [{required: true, message: '请选择结算日', trigger: 'blur'}],
                money: [{required: true, message: '请填写款项金额', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
        this.loadStat();
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
            that.addOrUpdateForm.finalStatementFormVisible = true;
            that.addOrUpdateForm.finalStatement = {
                                             userId: null,
                                             day: null,
                                             money: null,
                                             remark: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.finalStatementFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "wpe/final/statement/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.finalStatement = result.data;
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
                    let url = that.addOrUpdateForm.finalStatement.id == null ? "wpe/final/statement/add" : "wpe/final/statement/update";
                    let type = that.addOrUpdateForm.finalStatement.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.finalStatement)}, function (r) {
                        loading.close();
                        if (r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.finalStatementFormVisible = false;
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
                httpUtil.del({url: "wpe/final/statement/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "wpe/final/statement/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                    that.page.expand = true;
                }
            });
        },
        loadStat() {
            let that = this;
            httpUtil.get({url: "wpe/final/statement/stat", data: that.queryForm}, function (result) {
                if (result.code == 0 && result.data) {
                        result.data.forEach(function (v) {
                            if (v.type === 'final') {
                                that.stat.final.money = v.money;
                            }else {
                                that.stat.record.money = v.money;
                            }
                        });
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
                userId: that.queryForm.userId
            }
            httpUtil.fileDownload(that, {url: "wpe/final/statement/list/export",data: data});
        }
    }
});
