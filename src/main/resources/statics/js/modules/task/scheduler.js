Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myBtn', myBtnT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);

let schedulerapp = new Vue({
    el: '#schedulerapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                type: null,
                status: null
            },
            tableColumns: [
                {
                    name: 'id',
                    label: '主键'
                },
                {
                    name: 'type',
                    dict: 'taskType',
                    label: '类型'
                },
                {
                    name: 'name',
                    label: '名称'
                },
                {
                    name: 'remark',
                    label: '描述'
                },
                {
                    name: 'beanName',
                    label: 'bean名称'
                },
                {
                    name: 'cron',
                    label: 'cron表达式'
                },
                {
                    name: 'delay',
                    label: '间隔毫秒'
                },
                {
                    name: 'delay',
                    msfmt: 'msfmt',
                    label: '间隔时间'
                },
                {
                    name: 'status',
                    dict: 'taskStatus',
                    label: '状态'
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
                            auth:'task:scheduler:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'task:scheduler:delFlag',
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
                schedulerFormLabelWidth: '200px',
                schedulerFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                scheduler:{
                    type: null,
                    name: null,
                    remark: null,
                    beanName: null,
                    cron: null,
                    delay: null,
                    status: null
                }
            },
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            },
            delayFmt: ''
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
            that.addOrUpdateForm.schedulerFormVisible = true;
            that.addOrUpdateForm.scheduler = {
                                             type: null,
                                             name: null,
                                             remark: null,
                                             beanName: null,
                                             cron: null,
                                             delay: null,
                                             status: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.schedulerFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "task/scheduler/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.scheduler = result.data;
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
                    let url = that.addOrUpdateForm.scheduler.id == null ? "task/scheduler/add" : "task/scheduler/update";
                    let type = that.addOrUpdateForm.scheduler.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.scheduler)}, function (r) {
                        loading.close();
                        if (r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.schedulerFormVisible = false;
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
                httpUtil.del({url: "task/scheduler/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "task/scheduler/list", data: that.queryForm}, function (result) {
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
            httpUtil.fileDownload(that, {url: "task/scheduler/list/export",data: data});
        }
    },
    watch: {
        'addOrUpdateForm.scheduler.delay'(val) {
            if(val){
                this.delayFmt = T.millisecondsToHMS(val) || '-';
                console.log(this.delayFmt)
            }
        }
    }
});
