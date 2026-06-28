Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('myBtn', myBtnT);
let configapp = new Vue({
    el: '#configapp',
    data() {
        return {
            rowParams: '',
            rowDialogVisible: false,
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                status: null,
                keyword: null
            },
            tableColumns: [
                {
                    name: 'status',
                    dict: 'status',
                    label: '启用状态'
                },
                {
                    name: 'configKey',
                    label: 'KEY'
                },
                {
                    name: 'configValue',
                    label: 'VALUE'
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
                    fixed: 'right',
                    width: '180px',
                    buttons: [
                        {
                            label: '详情',
                            icon: 'el-icon-view',
                            click: this.rowParamsInfo,
                            type: 'success'
                        },
                        {
                            auth: 'sys:config:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth:'sys:oss:config:save',
                            label: '配置',
                            icon: 'el-icon-edit',
                            click: this.myConfig,
                            type: 'info'
                        },
                        {
                            auth: 'sys:config:delFlag',
                            icon: 'el-icon-delete',
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
                configFormLabelWidth: '200px',
                configFormVisible: false,
                config:{
                    configKey: null,
                    configValue: null,
                    status: null,
                    remark: null
                }
            },
            rules: {
                configKey: [{required: true, message: '必填', trigger: 'blur'}],
                configValue: [{required: true, message: '必填', trigger: 'blur'}],
                status: [{required: true, message: '必选', trigger: 'change'}]
            },
            ossRules: {
                type: [{required: true, message: '必选', trigger: 'change'}],
                fileQuality: [{required: true, message: '必填', trigger: 'blur'}],
                localFilePath: [{required: true, message: '必填', trigger: 'blur'}],
                localFileMapping: [{required: true, message: '必填', trigger: 'blur'}],
                localFilePrefix: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunDomain: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunPrefix: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunEndPoint: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunAccessKeyId: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunAccessKeySecret: [{required: true, message: '必填', trigger: 'blur'}],
                aliyunBucketName: [{required: true, message: '必填', trigger: 'blur'}],
                qiniuDomain: [{required: true, message: '必填', trigger: 'blur'}],
                qiniuPrefix: [{required: true, message: '必填', trigger: 'blur'}],
                qiniuAccessKey: [{required: true, message: '必填', trigger: 'blur'}],
                qiniuSecretKey: [{required: true, message: '必填', trigger: 'blur'}],
                qiniuBucketName: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudDomain: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudPrefix: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudAppId: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudSecretId: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudSecretKey: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudBucketName: [{required: true, message: '必填', trigger: 'blur'}],
                qcloudRegion: [{required: true, message: '必填', trigger: 'blur'}],
                fileCompress: [{required: true, message: '必选', trigger: 'change'}],
                status: [{required: true, message: '必选', trigger: 'change'}]
            },
            ossForm: {
                uploadFormVisible: false,
                ossFormVisible: false,
                config: {
                    type: 'LOCAL',
                    status: 'Y',
                    fileCompress: 'true',
                    fileQuality: '0.3',
                    localFilePath: 'C:/upload',
                    localFileMapping: '/oss/file',
                    localFilePrefix: null,
                    aliyunDomain: null,
                    aliyunPrefix: null,
                    aliyunEndPoint: null,
                    aliyunAccessKeyId: null,
                    aliyunAccessKeySecret: null,
                    aliyunBucketName: null,

                    qiniuDomain: null,
                    qiniuPrefix: null,
                    qiniuAccessKey: null,
                    qiniuSecretKey: null,
                    qiniuBucketName: null,

                    qcloudDomain: null,
                    qcloudPrefix: null,
                    qcloudAppId: null,
                    qcloudSecretId: null,
                    qcloudSecretKey: null,
                    qcloudBucketName: null,
                    qcloudRegion: null
                }
            }
        }
    },
    created: function () {
        this.loadData();
    },
    methods: {
        rowParamsInfo (index, row) {
            let that = this;
            that.rowDialogVisible = true;
            let b ={};
            b.p = row.configValue || '-------无参数-------';
            that.rowParams = b.p.replace(/\,/g,',<br/>').replace(/\)/g,')<br/>');
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
        myConfig(index, row) {
            let that = this;
           if(row.configKey==T.cloud_storage_config_key){
               that.ossForm.ossFormVisible = true;
               that.resetForm('ossFormRef');
               httpUtil.get({url: "sys/oss/getConfig"}, function (result) {
                   if (result.code == 0) {
                       that.ossForm.config = result.data;
                   }
               });
           }else {
               that.$message.error('该数据没有可配置项');
           }
        },
        ossUpdate() {
            let that = this;
            that.$refs['ossFormRef'].validate((valid) => {
                if (valid) {
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    httpUtil.post({
                        url: "sys/oss/config/save",
                        data: JSON.stringify(that.ossForm.config)
                    }, function (r) {
                        loading.close();
                        alertMsg(that, r)
                        if (r.code == 0) {
                            that.myQuery();
                            that.ossForm.ossFormVisible = false;
                        }
                    });
                }
            });
        },
        myAdd() {
            let that = this;
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
            let that = this;
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
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    const loading = that.$loading({
                        lock: true,
                        text: 'Loading',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    let url = that.addOrUpdateForm.config.id == null ? "sys/config/add" : "sys/config/update";
                    let type = that.addOrUpdateForm.config.id == null ? "POST" : "PUT";
                    httpUtil.post({url: url, type: type, data: JSON.stringify(that.addOrUpdateForm.config)}, function (r) {
                        loading.close();
                        if(r.code == 0) {
                            that.myQuery();
                            that.addOrUpdateForm.configFormVisible = false;
                        }
                        alertMsg(that, r)
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
                httpUtil.del({url: "sys/config/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/config/list", data: that.queryForm}, function (result) {
                if (result.code == 0) {
                    that.page = result.data;
                    that.page.expand = true;
                }
            });
        }
    }
});
