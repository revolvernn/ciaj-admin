Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);
Vue.component('myBtn', myBtnT);

let electricianRecordStatisticsapp = new Vue({
    el: '#electricianRecordStatisticsapp',
    data() {
        return {
            queryForm: {
                // orderBy: 'workday-asc',
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                userId: null,
                projectId: null,
                month: null
            },
            tableColumns: [
                {
                    name: 'month',
                    label: '年月'
                },
                {
                    name: 'total',
                    label: '工作天数'
                },
                {
                    name: 'workdays',
                    label: '年月工作日'
                },
                {
                    name: 'projectName',
                    label: '工程名称'
                },
                {
                    name: 'addr',
                    label: '地址'
                },
                {
                    name: 'username',
                    label: '用户'
                }
                // ,
                // {
                //     label: '操作',
                //     width: '180px',
                //     buttons: [
                //         {
                //             auth:'sys:electricianRecord:update',
                //             label: '修改',
                //             icon: 'el-icon-edit',
                //             click: this.myUpdate,
                //             type: 'success'
                //         },
                //         {
                //             auth:'sys:electricianRecord:delFlag',
                //             label: '删除',
                //             icon: 'el-icon-delete',
                //             click: this.myDel,
                //             type: 'danger'
                //         }
                //     ]
                // }
            ],
            tableLoading: false,
            page: {},
            rules: {
                //username: [{required: true, message: '必填', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
    },
    methods: {
        exportStatistics() {
            let that = this;
            httpUtil.fileDownload(that, {url: "wpe/electrician/record/statistics/export",data: that.queryForm});
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
        loadData() {
            let that = this;
            httpUtil.get({url: "wpe/electrician/record/statistics", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                }
            });
        }
    }
});
