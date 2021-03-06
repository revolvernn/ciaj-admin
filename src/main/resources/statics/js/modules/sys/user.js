Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
Vue.component('myDictSelect', myDictSelectT);

Vue.component('myBtn', myBtnT);
let userapp = new Vue({
    el: '#userapp',
    data() {
        return {
            deptData: [],
            areaData: [],
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
                    width: '100',
                    label: '账号'
                },
                {
                    name: 'dept.name',
                    label: '部门'
                },
                {
                    name: 'username',
                    width: '100',
                    label: '用户名'
                },
                {
                    name: 'nickname',
                    width: '100',
                    label: '昵称'
                },
                {
                    name: 'email',
                    width: '150',
                    label: '邮箱'
                },
                {
                    name: 'mobile',
                    width: '100',
                    label: '手机号'
                },
                {
                    name: 'sex',
                    dict: 'sex',
                    label: '性别'
                },
                {
                    name: 'birthday',
                    date: 'yyyy-MM-dd',
                    width: '100',
                    label: '生日'
                },
                {
                    name: 'locked',
                    dict: 'user_locked',
                    label: '状态'
                },
                {
                    name: 'sysProvince.name',
                    label: '省份'
                },
                {
                    name: 'sysCity.name',
                    label: '城市'
                },
                {
                    name: 'sysDistrict.name',
                    label: '区县'
                },
                {
                    name: 'addr',
                    label: '地址'
                },
                {
                    name: 'createTime',
                    width: '140',
                    label: '创建时间'
                },
                {
                    name: 'updateTime',
                    width: '140',
                    label: '更新时间'
                },
                {
                    label: '操作',
                    fixed: 'right',
                    width: '180px',
                    buttons: [
                        {
                            auth: 'sys:user:update',
                            label: '修改',
                            icon: 'el-icon-edit',
                            click: this.myUpdate,
                            type: 'success'
                        },
                        {
                            auth: 'sys:user:role:rel:adds',
                            icon: 'el-icon-edit',
                            label: '分配角色',
                            click: this.myRoles,
                            type: 'success'
                        },
                        {
                            auth: 'sys:user:password:update',
                            icon: 'el-icon-edit',
                            label: '修改密码',
                            click: this.myUpdatePassword,
                            type: 'success'
                        },
                        {
                            auth: 'sys:user:delFlag',
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
                areaModel: [],
                deptModel: [],
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
                    province: null,
                    city: null,
                    district: null,
                    addr: null,
                    deptId: null,
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
                mobile: [{
                    required: true,
                    pattern: /^1[3|4|5|6|7|8][0-9]\d{8}$/,
                    message: '请填写正确手机号',
                    trigger: 'blur'
                }]
            },
            passwordFormRules: {
                newPassword: [{required: true, message: '必填', trigger: 'blur'}],
            },
            pd: {
                dialogVisible: false,
                passwordForm: {
                    newPassword: null,
                    userName: '',
                    id: null
                }
            }
        }
    },
    created: function () {
        this.loadData();
    },
    methods: {
        initTree() {
            let that = this;
            if (that.deptData.length == 0) {
                httpUtil.get({url: "sys/dept/list", data: {}}, function (result) {
                    if (result.code == 0) {
                        that.deptData = treeUtil.vueTree(result.data.list);
                    }
                });
            }
            if (that.areaData.length == 0) {
                httpUtil.get({url: "sys/area/list", data: {level: 4}}, function (result) {
                    if (result.code == 0) {
                        that.areaData = areaUtil.vueTree(result.data.list);
                    }
                });
            }
        },
        exportUser() {
            httpUtil.fileDownload(this, {url: "/sys/export/users"});
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
            let that = this;
            try {
                that.eareModel = [];
                that.deptModel = [];
                that.queryForm.deptId = null;
                that.queryForm.areaId = null;
                that.$refs[formName].resetFields();
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
            let that = this;
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
            let that = this;
            that.initRoles();
            that.addOrUpdateForm.roleVisible = true;
            that.addOrUpdateForm.roleDialogTitle = '分配角色：[ ' + row.username + ' ]';
            that.addOrUpdateForm.userRole.userId = row.id;

        },
        saveUserRole() {
            let that = this;
            let roles = that.addOrUpdateForm.userRole.roles;
            let datas = roles.length == 0 ? [{userId: that.addOrUpdateForm.userRole.userId}] : [];
            for (let v in roles) {
                let data = {};
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
            let that = this;
            that.initTree();
            that.addOrUpdateForm.userFormVisible = true;
            that.addOrUpdateForm.deptModel = [];
            that.addOrUpdateForm.areaModel = [];
            that.addOrUpdateForm.user = {
                account: null,
                username: null,
                nickname: null,
                sex: null,
                email: null,
                mobile: null,
                birthday: new Date(),
                picUrl: '',
                source: 'pc',
                province: null,
                city: null,
                district: null,
                addr: null,
                deptId: null,
                locked: 'N'
            };
            that.resetForm('addOrUpdateFormRef');
        },
        myUpdate(index, row) {
            let that = this;
            that.initTree();
            that.addOrUpdateForm.title = '修改';
            // that.resetForm('addOrUpdateFormRef');
            httpUtil.get({url: "sys/user/getById/" + row.id}, function (result) {
                if (result.code == 0) {
                    that.addOrUpdateForm.user = result.data;
                    that.addOrUpdateForm.userFormVisible = true;
                    that.addOrUpdateForm.deptModel = result.data.dept ? result.data.dept.parentIds.split(',') : [];
                    if (that.addOrUpdateForm.deptModel.length > 0) {
                        that.addOrUpdateForm.deptModel.push(result.data.deptId);
                    }
                    if (result.data.sysDistrict) {
                        that.addOrUpdateForm.areaModel = result.data.sysDistrict.parentIds.split(',');
                        that.addOrUpdateForm.areaModel.push(result.data.district);
                    } else if (result.data.sysCity) {
                        that.addOrUpdateForm.areaModel = result.data.sysCity.parentIds.split(',');
                        that.addOrUpdateForm.areaModel.push(result.data.city);
                    } else if (result.data.sysProvince) {
                        that.addOrUpdateForm.areaModel = result.data.sysProvince.parentIds.split(',');
                        that.addOrUpdateForm.areaModel.push(result.data.province);
                    } else {
                        that.addOrUpdateForm.areaModel = [];
                    }
                }
            });
        },
        //父级选择处理
        deptModelFormChange(val) {
            let that = this;
            if (val.length > 0) {
                that.addOrUpdateForm.user.deptId = val[val.length - 1];
                console.log(val);
            } else {
                that.addOrUpdateForm.user.deptId = null;
            }
        },
        //父级选择处理 province city district
        areaModelFormChange(val) {
            let that = this;
            if (val.length === 2) {
                that.addOrUpdateForm.user.province = val[1];
            } else if (val.length === 3) {
                that.addOrUpdateForm.user.province = val[1];
                that.addOrUpdateForm.user.city = val[2];
            } else if (val.length === 4) {
                that.addOrUpdateForm.user.province = val[1];
                that.addOrUpdateForm.user.city = val[2];
                that.addOrUpdateForm.user.district = val[3];
            } else {
                that.addOrUpdateForm.user.province = null;
                that.addOrUpdateForm.user.city = null;
                that.addOrUpdateForm.user.district = null;
            }
        },
        saveOrUpdate() {
            let that = this;
            that.$refs['addOrUpdateFormRef'].validate((valid) => {
                if (valid) {
                    let url = that.addOrUpdateForm.user.id == null ? "/sys/pc/register" : "sys/user/update";
                    let type = that.addOrUpdateForm.user.id == null ? "POST" : "PUT";
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
            let that = this;
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
        myUpdatePassword(index, row) {
            let that = this;
            that.pd.dialogVisible = true;
            that.pd.passwordForm.id = row.id;
            that.pd.passwordForm.userName = row.username;
            that.resetForm('passwordFormRef')
        },
        passwordSubmit: function () {
            let that = this;
            that.$refs['passwordFormRef'].validate((valid) => {
                if (valid) {
                    let data = "userId=" + that.pd.passwordForm.id + "&newPassword=" + that.pd.passwordForm.newPassword;
                    httpUtil.post({
                        url: "sys/user/password/update",
                        data: data,
                        contentType: AjaxContentType.URL
                    }, function (result) {
                        if (result.code == 0) {
                            that.$message({message: "修改成功", type: 'success'});
                            that.pd.dialogVisible = false;
                        } else {
                            that.$message.error(result.msg);
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        loadData() {
            let that = this;
            httpUtil.get({url: "sys/user/list", data: that.queryForm}, function (r) {
                if (r.code == 0) {
                    that.page = r.data;
                    that.page.expand = true;
                }
            });
        }
    }
});