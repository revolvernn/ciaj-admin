Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);

var ${jsName}app = new Vue({
    el: '<#noparse>#</#noparse>${jsName}app',
    data() {
        return {
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null
            },
            tableColumns: [
            <#if tableClass.allFields??>
            <#list tableClass.allFields as field>
                {
                    name: '${field.fieldName}',
                    label: '<#if field.remarks??>${field.remarks}</#if>'
                },
            </#list>
            </#if>
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
            tableLoading: false,
            page: {},
            addOrUpdateForm: {
                title: '新增',
                ${jsName}FormLabelWidth: '80px',
                ${jsName}FormVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                ${jsName}:{
                <#if tableClass.allFields??>
                <#list tableClass.allFields as field>
                    ${field.fieldName}: null<#if field_has_next>,</#if>
                </#list></#if>
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
            that.addOrUpdateForm.${jsName}FormVisible = true;
            that.addOrUpdateForm.${jsName} = {
                                         <#if tableClass.allFields??>
                                         <#list tableClass.allFields as field>
                                             ${field.fieldName}: null<#if field_has_next>,</#if>
                                         </#list></#if>
                                         }
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            var that = this;
            that.addOrUpdateForm.title = '修改';
            that.addOrUpdateForm.${jsName}FormVisible = true;
            that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "${mvcUrl}/getById/" + row.id}, function (result) {
                  if (result.code == 0) {
                      that.addOrUpdateForm.${jsName} = result.data;
                  }
            });
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.${jsName}.id == null ? "${mvcUrl}/add" : "${mvcUrl}/update";
                    var type = that.addOrUpdateForm.${jsName}.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.${jsName})}, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.${jsName}FormVisible = false;
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
                httpUtil.del({url: "${mvcUrl}/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            var that = this;
            httpUtil.get({url: "${mvcUrl}/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data
                }
            });
        }
    }
});