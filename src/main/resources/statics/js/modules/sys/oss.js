Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
let myDiv = Vue.extend({
    template: '<div></div>'
})
Vue.component('myDiv', myDiv);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('myBtn', myBtnT);
let ossapp = new Vue({
    el: '#ossapp',
    data() {
        return {
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
                    name: 'source',
                    label: '来源'
                },
                {
                    name: 'type',
                    dict: 'upload_file_type',
                    label: '类型'
                },
                {
                    name: 'url',
                    label: '文件地址'
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
                            label: '预览',
                            icon: 'el-icon-view',
                            click: this.myRowView,
                            type: 'info'
                        },
                        {
                            label: '删除',
                            icon: 'el-icon-edit',
                            click: this.myDel,
                            auth: 'sys:oss:delFlag',
                            type: 'danger'
                        }
                    ]
                }
            ],
            page: {},
            uploadFormVisible: false,
            rowView: {
                viewPreVisible: false,
                url: '',
                type: null
            }
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
        uploadSuccess(res, file) {
            let that = this;
            alertMsg(that, res)
            that.myQuery();
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields;
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
        myRowView(index, row) {
            let that = this;
            that.rowView.viewPreVisible = true;
            that.rowView.type = row.type;
            that.rowView.url = row.url || '';
        },
        myDel(index, row) {
            let that = this;
            that.$confirm('此操作将删除该数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                httpUtil.del({url: "sys/oss/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/oss/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
});
