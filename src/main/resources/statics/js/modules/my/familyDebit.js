Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myBtn', myBtnT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);

let familyDebitapp = new Vue({
    el: '#familyDebitapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                userId: null,
                type: null
            },
            tableColumns: [
                {
                    name: 'user.nickname',
                    label: '用户昵称'
                },
                {
                    name: 'type',
                    dict: 'debitType',
                    label: '类型'
                },
                {
                    name: 'day',
                    date: 'yyyy-MM-dd',
                    label: '日期'
                },
                {
                    name: 'money',
                    ny: 'cny',
                    sum: 'cny',
                    label: '款项'
                },
                {
                    name: 'addr',
                    label: '地址'
                },
                {
                    name: 'remark',
                    label: '备注'
                },
                {
                    name: 'createTime',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    sortable: 'custom',
                    sortBy: 'm.update_time',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '180px',
                    buttons: [
                        {
                            auth:'my:family:debit:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'my:family:debit:delFlag',
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
                familyDebitFormLabelWidth: '200px',
                familyDebitFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                familyDebit:{
                    userId: null,
                    type: null,
                    day: null,
                    money: null,
                    addr: null,
                    remark: null
                }
            },
            rules: {
                userId: [{required: true, message: '必选', trigger: 'change'}],
                type: [{required: true, message: '必选', trigger: 'change'}],
                day: [{required: true, message: '必选', trigger: 'change'}],
                money: [{required: true, message: '必填', trigger: 'blur'}]
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
            that.addOrUpdateForm.familyDebitFormVisible = true;
            that.addOrUpdateForm.familyDebit = {
                                             id: null,
                                             userId: null,
                                             type: null,
                                             day: null,
                                             money: null,
                                             addr: null,
                                             remark: null,
                                             createAt: null,
                                             createTime: null,
                                             updateAt: null,
                                             updateTime: null,
                                             delFlag: null,
                                             version: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.familyDebitFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "my/family/debit/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.familyDebit = result.data;
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
                    let url = that.addOrUpdateForm.familyDebit.id == null ? "my/family/debit/add" : "my/family/debit/update";
                    let type = that.addOrUpdateForm.familyDebit.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.familyDebit)}, function (r) {
                        loading.close();
                        if (r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.familyDebitFormVisible = false;
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
                httpUtil.del({url: "my/family/debit/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "my/family/debit/list", data: that.queryForm}, function (result) {
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
            httpUtil.fileDownload(that, {url: "my/family/debit/list/export",data: data});
        }
    }
});
