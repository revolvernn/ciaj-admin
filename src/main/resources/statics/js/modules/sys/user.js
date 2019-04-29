Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
var userapp = new Vue({
    el: '#userapp',
    data() {
        return {
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10,
                keyword: null,
                locked: null
            },
            tableColumns: [
                {
                    name: 'picUrl',
                    image: true,
                    label: '头像'
                },
                {
                    name: 'account',
                    label: '账号'
                },
                {
                    name: 'username',
                    label: '用户名'
                },
                {
                    name: 'nickname',
                    label: '昵称'
                },
                {
                    name: 'email',
                    label: '邮箱'
                },
                {
                    name: 'mobile',
                    label: '手机号'
                },
                {
                    name: 'sex',
                    dict: 'sex',
                    label: '性别'
                },
                {
                    name: 'birthday',
                    label: '生日'
                },
                {
                    name: 'locked',
                    dict: 'user_locked',
                    label: '状态'
                },
                {
                    name: 'createTime',
                    width:'140',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    width:'140',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    width: '250px',
                    fixed: "right",
                    buttons: [
                        {
                            auth: 'sys:user:update',
                            label: '修改',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth: 'sys:user:role:rel:adds',
                            label: '分配角色',
                            click: this.myRoles,
                            type: 'success'
                        },
                        {
                            auth: 'sys:user:delFlag',
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
                roleDialogTitle: '新增',
                userFormLabelWidth: '80px',
                userFormVisible: false,
                roleVisible: false,
                pickerOptions: {
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                user: {
                    account: null,
                    username: null,
                    nickname: null,
                    sex: '0',
                    email: null,
                    mobile: null,
                    birthday: null,
                    picUrl: '',
                    locked: 'N'
                },
                userRole: {
                    roleData: [],
                    roles: [],
                    userId: null
                }
            },
            rules: {
                account: [{required: true, message: '必填', trigger: 'blur'}],
                username: [{required: true, message: '必填', trigger: 'blur'}],
                mobile: [{required: true, pattern: /^1[3|4|5|6|7|8][0-9]\d{8}$/, message: '请填写正确手机号', trigger: 'blur'}]
            }
        }
    },
    created: function () {
        this.loadData();
    },
    methods: {
        exportUser() {
            httpUtil.fileDownload(this,{url: "/sys/export/users"});
        },
        handleAvatarSuccess(res, file) {
            // this.addOrUpdateForm.user.picUrl = URL.createObjectURL(file.raw);
            this.addOrUpdateForm.user.picUrl = res.data;
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
        resetForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
                console.log(e);
            }
        },
        myQuery() {
            this.loadData();
        },
        pagesizechange(val) {
            this.queryForm.pageSize = val;
            this.loadData();
        },
        currentpagechange(val) {
            this.queryForm.pageNo = val;
            this.loadData();
        },
        initRoles() {
            var that = this;
            httpUtil.get({url: "sys/role/list", data: {}}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.userRole.roleData = treeUtil.roles(result.data.list);
                    //回显
                    httpUtil.get({
                        url: "sys/user/role/rel/list",
                        data: {userId: that.addOrUpdateForm.userRole.userId}
                    }, function (r) {
                        if (r.code == 0) {
                            that.addOrUpdateForm.userRole.roles = treeUtil.roleIds(r.data.list);
                        }
                    });
                }
            });
        },
        myRoles(index, row) {
            var that = this;
            that.initRoles();
            that.addOrUpdateForm.roleVisible = true;
            that.addOrUpdateForm.roleDialogTitle = '分配角色：[ ' + row.username + ' ]';
            that.addOrUpdateForm.userRole.userId = row.id;

        },
        saveUserRole() {
            var that = this;
            var roles = that.addOrUpdateForm.userRole.roles;
            var datas = roles.length == 0 ? [{userId: that.addOrUpdateForm.userRole.userId}] : [];
            for (var v in roles) {
                var data = {};
                data.userId = that.addOrUpdateForm.userRole.userId;
                data.roleId = roles[v];
                datas.push(data)
            }
            httpUtil.post({
                url: 'sys/user/role/rel/adds',
                data: JSON.stringify(datas)
            }, function (r) {
                that.addOrUpdateForm.roleVisible = false;
                alertMsg(that, r)
            });
        },
        myAdd() {
            this.addOrUpdateForm.userFormVisible = true;
            this.addOrUpdateForm.user = {
                account: null,
                username: null,
                nickname: null,
                sex: null,
                email: null,
                mobile: null,
                birthday: new Date(),
                picUrl: '',
                locked: 'N'
            };
            this.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            var that = this;
            that.addOrUpdateForm.title = '修改';
            // that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/user/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.user = result.data;
                    that.addOrUpdateForm.userFormVisible = true;
                }
            });
        },
        saveOrUpdate() {
            var that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    var url = that.addOrUpdateForm.user.id == null ? "/sys/pc/register" : "sys/user/update";
                    var type = that.addOrUpdateForm.user.id == null ? "POST" : "PUT";
                    httpUtil.post({
                        url: url,
                        type: type,
                        data: JSON.stringify(that.addOrUpdateForm.user)
                    }, function (r) {
                        that.myQuery();
                        that.addOrUpdateForm.userFormVisible = false;
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
                httpUtil.del({url: "sys/user/delFlag/" + row.id}, function (r) {
                    that.myQuery();
                    alertMsg(that, r)
                });
            });
        },
        loadData() {
            var that = this;
            httpUtil.get({url: "sys/user/list", data: that.queryForm}, function (r) {
                if (r.code == 0) {
                    that.page = r.data

                }
            });
        }
    }
});