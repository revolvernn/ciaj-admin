Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('mySearchSelect', mySearchSelectT);
Vue.component('myBtn', myBtnT);

let electricianRecordStatisticsapp = new Vue({
    el: '#electricianRecordStatisticsapp',
    data() {
        return {
            defaultSort: {prop: 'minWorkday', order: 'descending'},
            queryForm: {
                orderBy: 'minWorkday-desc',
                orderByEnabled: true,
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                userId: null,
                projectId: null,
                period: null,
                type: '1'
            },
            tableColumns: [
                {
                    name: 'username',
                    label: '用户'
                },
                {
                    name: 'projectName',
                    label: '工程名称'
                },
                {
                    sortable: 'custom',
                    sortBy: 'minWorkday',
                    name: 'minWorkday',
                    date: 'yyyy-MM-dd',
                    label: '开始日期'
                },
                {
                    sortable: 'custom',
                    sortBy: 'period',
                    name: 'period',
                    label: '周期'
                },
                {
                    name: 'total',
                    label: '工作天数'
                },
                {
                    name: 'projectNum',
                    label: '项目数'
                },
                {
                    name: 'totalLabourCost',
                    cny: 'cny',
                    label: '总工价'
                },
                {
                    name: 'workdays',
                    label: '年月工作日'
                },
                {
                    name: 'workdayDs',
                    label: '工作日明细1'
                },
                {
                    name: 'workdayDDs',
                    label: '工作日明细2'
                }
            ],
            tableLoading: true,
            page: {}
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
        exportStatistics() {
            let that = this;
            let data =  {
                orderBy: that.queryForm.orderBy,
                orderByEnabled: true,
                pageEnabled: false,
                keyword: that.queryForm.keyword,
                userId: that.queryForm.userId,
                projectId: that.queryForm.projectId,
                period: that.queryForm.period,
                type: that.queryForm.type
            }
            httpUtil.fileDownload(that, {url: "wpe/electrician/record/statistics/export",data: data});
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
                    that.page.expand = true;
                }
            });
        }
    }
    , watch: {
        'queryForm.type'(val) {
            let that = this;
             that.queryForm.period = null;
        }
    }
});
