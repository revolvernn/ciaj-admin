Vue.component('myMenuItem', myMenuItem);
Vue.component('myDictSpan', myDictSpan);
let mainTitle = '主页面', mainUrl = 'main.html';
let vm = new Vue({
    el: '#app',
    data: {
        mainTitle: mainTitle,
        mainJoin: [mainTitle, mainUrl].join(':'),
        isCollapse: true,
        dialogVisible: false,
        userDialogVisible: false,
        rules: {
            password: [{required: true, message: '请输入原密码', trigger: 'blur'}],
            newPassword: [{required: true, message: '请输入新密码', trigger: 'blur'}]
        },
        breadcrumbs: [],
        passwordForm: {password: null, newPassword: null},
        loginUser: {
            username: '',
            role: {
                name: ''
            },
            roles: []
        },
        menuList: {},
        iframeHeight: 500,
        defaultActive: this.mainJoin,
        defaultTabsValue: mainTitle,
        tabs: [{title: mainTitle, name: mainTitle, content: mainUrl, breadcrumbs: []}],
        elAsideStyle: {
            zIndex: '100',
            backgroundColor: '#D3DCE6',
            width: '200px',
            color: '#333',
            left: '0px',
            textAlign: 'left',
            position: 'absolute',
            bottom: '0px',
            top: '60px'
        },
        breadcrumbStyle: {
            backgroundColor: '#E9EEF3',
            color: '#333',
            textAlign: 'center',
            left: '200px',
            right: '0px',
            position: 'absolute',
            lineHeight: '30px'
        },
        elTabsStyle: {
            backgroundColor: '#E9EEF3',
            color: '#333',
            textAlign: 'center',
            top: '90px',
            left: '200px',
            right: '0px',
            position: 'absolute',
            bottom: '30px'
        },
        elFooterStyle: {
            backgroundColor: '#F2F6FC',
            color: '#AFB2BA',
            height: '30px',
            textAlign: 'center',
            bottom: '0px',
            position: 'absolute',
            left: '200px',
            right: '0px',
            paddingTop: '5px'
        }
    },
    mounted(){
        this.getLoginUser();
        this.getMenuList();
        this.iframeResize();
        window.onresize = () => {
            this.iframeResize()
        }
    },
    methods: {
        /**
         * iframe 自适应高度
         */
        iframeResize() {
            this.iframeHeight = document.documentElement.clientHeight  - 145;
        },
        /**
         * 关闭菜单
         * @param targetName
         */
        handleCollapse() {
            this.isCollapse = !this.isCollapse;
        },
        userInfoClick() {
            this.userDialogVisible = true;
        },
        /**
         *
         * @param targetName
         * @param action
         */
        selectTab(targetName, action) {
            let tab = this.tabs[targetName.index];
            this.defaultActive = [tab.name, tab.content].join(':');
            this.breadcrumbs = tab.breadcrumbs;
        },
        /**
         *
         * @param index
         * @param indexPath
         */
        selectMenu(index, indexPath) {
            this.breadcrumbs = this.getIndexPath(index, indexPath);
            let indexs = index.split(':');
            if (indexs.length === 2) {
                //判断tabs是否有 当前选择 url
                let tabs = this.tabs;
                let flag = true;
                tabs.forEach((tab, i) => {
                    if (tab.content === indexs[1]) {
                        let nextTab = tabs[i];
                        if (nextTab) {
                            this.defaultTabsValue = nextTab.name;
                            flag = false;
                            return;
                        }
                    }
                });
                if (flag) {
                    this.tabs.push({
                        title: indexs[0],
                        name: indexs[0],
                        content: indexs[1],
                        breadcrumbs: this.breadcrumbs
                    });
                    this.defaultTabsValue = indexs[0];
                }
            }
        },
        /**
         * 获取路径
         * @param indexPath
         * @returns {Array}
         */
        getIndexPath(index, indexPath) {
            let paths = [];
            if (index.indexOf(this.mainTitle) != -1) return paths;
            indexPath.forEach((v, i) => {
                let path = v.split(':');
                paths.push(path[0]);
            });
            return paths;
        },
        /**
         * 删除tab
         * @param targetName
         */
        removeTab(targetName) {
            let that = this;
            let tabs = that.tabs;
            let activeName = that.defaultTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                            that.breadcrumbs = nextTab.breadcrumbs;
                            that.defaultActive = [nextTab.name, nextTab.content].join(':');
                        }
                    }
                });
            }
            that.defaultTabsValue = activeName;
            that.tabs = tabs.filter(tab => tab.name !== targetName);
        },
        /**
         * 关闭tab
         * @param targetName
         */
        closeTab() {
            let tabs = this.tabs;
            this.defaultTabsValue = this.mainTitle;
            this.defaultActive = this.mainJoin;
            this.breadcrumbs = [];
            this.tabs = tabs.filter(tab => tab.name === this.mainTitle);
        },
        /**
         * 变更角色
         * @param command
         */
        opChangeCommand(command) {
            let that = this;
            if (command == 'command:update:password') {
                that.updatePassword();
            } else if (command == 'command:logout') {
                that.$confirm('确定要退出系统, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    that.logout();
                });
            }else {
                if (that.loginUser.role.id === command) {
                    that.$notify({
                        title: '角色切换提示',
                        message: '单角色无需切换！',
                        type: 'warning',
                        position: 'bottom-right'
                    });
                    return;
                }
                httpUtil.post({
                    url: "sys/user/role/change",
                    data: {roleId: command},
                    contentType: AjaxContentType.URL
                }, function (r) {
                    if (r.code == 0) {
                        that.$alert('已切换角色：' + r.data.role.name, '提示', {
                            confirmButtonText: '确定',
                            callback: action => {
                                parent.location.href = 'index.html';
                            }
                        });
                    } else {
                        that.$message.error("角色切换失败");
                    }
                });
            }
        },
        /**
         * 退出
         */
        logout() {
            window.location.href = 'sys/logout'
        },
        updatePassword() {
            let that = this;
            that.dialogVisible = true;
            that.resetPasswordForm('passwordFormRef')
        },
        getMenuList: function (event) {
            let that = this;
            httpUtil.get({url: "sys/menu/nav?_" + $.now()}, function (r) {
                that.menuList = treeUtil.vueTree(r.data);
            });
        },
        getLoginUser: function () {
            let that = this;
            httpUtil.get({url: "sys/user/info?_" + $.now()}, function (r) {
                that.loginUser = r.data;
            });
        },
        passwordSubmit: function () {
            let that = this;
            that.$refs['passwordFormRef'].validate((valid) => {
                if (valid) {
                    let data = "password=" + that.passwordForm.password + "&newPassword=" + that.passwordForm.newPassword;
                    httpUtil.post({
                        url: "sys/user/password",
                        data: data,
                        contentType: AjaxContentType.URL
                    }, function (result) {
                        if (result.code == 0) {
                            that.$message({message: "修改成功", type: 'success'});
                            that.dialogVisible = false;
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
        resetPasswordForm(formName) {
            try {
                this.$refs[formName].resetFields();
            } catch (e) {
            }
        }
    },
    watch: {
        isCollapse(val) {
            if (!val) {
                this.elAsideStyle.width = '65px';
                this.breadcrumbStyle.left = '65px';
                this.elTabsStyle.left = '65px';
                this.elFooterStyle.left = '65px';
            } else {
                this.elAsideStyle.width = '200px';
                this.breadcrumbStyle.left = '200px';
                this.elTabsStyle.left = '200px';
                this.elFooterStyle.left = '200px';
            }
        }
    }
});