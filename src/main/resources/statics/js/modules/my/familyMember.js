Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myBtn', myBtnT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);

let familyMemberapp = new Vue({
    el: '#familyMemberapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'familyId',
                    label: '家庭ID'
                },
                {
                    name: 'userId',
                    label: '用户ID'
                },
                {
                    name: 'type',
                    label: '成员类型'
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
                            auth:'my:family:member:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'my:family:member:delFlag',
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
                familyMemberFormLabelWidth: '200px',
                familyMemberFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                familyMember:{
                    familyId: null,
                    userId: null,
                    type: null
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
            that.addOrUpdateForm.familyMemberFormVisible = true;
            that.addOrUpdateForm.familyMember = {
                                             familyId: null,
                                             userId: null,
                                             type: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.familyMemberFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "my/family/member/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.familyMember = result.data;
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
                    let url = that.addOrUpdateForm.familyMember.id == null ? "my/family/member/add" : "my/family/member/update";
                    let type = that.addOrUpdateForm.familyMember.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.familyMember)}, function (r) {
                        loading.close();
                        if (r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.familyMemberFormVisible = false;
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
                httpUtil.del({url: "my/family/member/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "my/family/member/list", data: that.queryForm}, function (result) {
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
            httpUtil.fileDownload(that, {url: "my/family/member/list/export",data: data});
        }
    }
});
