Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

let logapp = new Vue({
    el: '#logapp',
    data() {
        return {
            rowParams: '',
            rowDialogVisible: false,
            defaultSort: {prop: 'createTime', order: 'descending'},
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                type: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'type',
                    dict: 'log_type',
                    label: '日志类型'
                },
                {
                    name: 'username',
                    label: '用户名'
                },
                {
                    name: 'ip',
                    label: '请求IP'
                },
                {
                    name: 'time',
                    label: '请求时长(毫秒)'
                },
                {
                    name: 'operation',
                    label: '用户操作'
                },
                {
                    name: 'method',
                    label: '请求方法'
                },
                {
                    name: 'description',
                    label: '备注'
                },
                {
                    name: 'createTime',
                    sortable: 'custom',
                    width:'140',
                    sortBy:'m.create_time',
                    label: '创建时间'
                },
                {
                    label: '操作',
                    width: '200px',
                    buttons: [
                        {
                            auth:'sys:log:delFlag',
                            label: '删除',
                            click: this.myDel,
                            type: 'danger'
                        },
                        {
                            label: '参数详情',
                            click: this.rowParamsInfo,
                            type: 'success'
                        }
                    ]
                }
            ],
            tableLoading: false,
            page: {}
        }
    },
    created: function () {
    },
    methods: {
        sortchange(val){
            let that = this;
            that.queryForm.orderBy=val.sortBy;
            that.myQuery();
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
                console.log(e);
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
        rowParamsInfo (index, row) {
            let that = this;
            that.rowDialogVisible = true;
            let b ={};
            b.p = row.params|| '';
            that.rowParams = b.p.replace(/\,/g,',<br/>').replace(/\)/g,')<br/>');
        },
        myDel(index, row) {
            let that = this;
            that.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                httpUtil.del({url: "sys/log/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/log/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
});
