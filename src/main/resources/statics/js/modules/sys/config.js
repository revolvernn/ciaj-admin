Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

var configapp = new Vue({
    el: '#configapp',
    data() {
        return {
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                status: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'configKey',
                    label: 'KEY'
                },
                {
                    name: 'configValue',
                    label: 'VALUE'
                },
                {
                    name: 'status',
                    dict: 'status',
                    label: '状态'
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
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '150px',
                    buttons: [
                        {
                            label: '修改',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
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
                configFormLabelWidth: '80px',
                configFormVisible: false,
                config:{
                    configKey: null,
                    configValue: null,
                    status: null,
                    remark: null
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
                console.log(e);
            }
        },
        myQueryReset() {
            var that = this;
            that.resetForm('queryFormRef');
            that.myQuery();
        },
        myQuery() {
            var that = this;
            that.loadData();
        },
        pagesizechange(val) {
            var that = this;
            that.queryForm.pageSize = val;
            that.loadData();
        },
        currentpagechange(val) {
            this.queryForm.pageNo = val;
            this.loadData();
        },
        myAdd() {
            var that = this;
            that.addOrUpdateForm.title = '新增';
            that.addOrUpdateForm.configFormVisible = true;
            that.addOrUpdateForm.config = {
                                             configKey: null,
                                             configValue: null,
                                             status: null,
                                             remark: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            var that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.configFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/config/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.config = result.data;
                  }
            });
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.config.id == null ? "sys/config/add" : "sys/config/update";
                    var type = that.addOrUpdateForm.config.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.config)}, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.configFormVisible = false;
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
                httpUtil.del({url: "sys/config/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            var that = this;
            httpUtil.get({url: "sys/config/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
});
