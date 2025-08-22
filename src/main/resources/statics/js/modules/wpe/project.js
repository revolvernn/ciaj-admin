Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('myBtn', myBtnT);

let projectapp = new Vue({
    el: '#projectapp',
    data() {
        return {
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                houseType: null,
                decorationType: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'id',
                    label: '主键'
                },
                {
                    name: 'projectName',
                    label: '工程项目名称'
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
                    name: 'houseType',
                    dict: 'houseType',
                    label: '项目户型'
                },
                {
                    name: 'decorationType',
                    dict: 'decorationType',
                    label: '装修类型'
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
                            auth:'sys:project:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:project:delFlag',
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
                projectFormLabelWidth: '200px',
                projectFormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                project:{
                    id: null,
                    projectName: null,
                    addr: null,
                    remark: null,
                    houseType: null,
                    decorationType: null
                }
            },
            rules: {
                projectName: [{required: true, message: '请填写工程项目名称', trigger: 'blur'}],
                addr: [{required: true, message: '请填写地址', trigger: 'blur'}],
                houseType: [{required: true, message: '请选择项目户型', trigger: 'blur'}],
                decorationType: [{required: true, message: '请选择装修类型', trigger: 'blur'}]
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
            that.addOrUpdateForm.projectFormVisible = true;
            that.addOrUpdateForm.project = {
                                             id: null,
                                             projectName: null,
                                             addr: null,
                                             remark: null,
                                             houseType: null,
                                             decorationType: null
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.projectFormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "wpe/project/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.project = result.data;
                  }
            });
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.project.id == null ? "wpe/project/add" : "wpe/project/update";
                    let type = that.addOrUpdateForm.project.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.project)}, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.projectFormVisible = false;
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
                httpUtil.del({url: "wpe/project/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r);
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "wpe/project/list", data: that.queryForm}, function (result) {
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
                houseType: that.queryForm.houseType,
                decorationType: that.queryForm.decorationType
            }
            httpUtil.fileDownload(that, {url: "wpe/project/list/export",data: data});
        }
    }
});
