var myMenuItem = Vue.extend({
    name: 'my-menu-item',
    props: {item: {}},
    methods: {
        getIndex(name, url) {
            return [name, url].join(':');
        }
    },
    template: [
        '<el-submenu :index="item.name">',
        '   <template slot="title"><i v-if="item.icon" :class="item.icon"></i><i v-else class="el-icon-setting"></i><span slot="title">{{item.name}}</span></template>',
        '   <el-menu-item-group v-if="item.children"  v-for="children in item.children">',
        '       <el-menu-item v-if="children.type==\'1\'" :index="getIndex(children.name,children.url)" :disabled="children.disabled">',
        '           <i v-if="children.icon" :class="children.icon"></i>',
        '           <i v-else class="el-icon-setting"></i>',
        '           <span slot="title">{{children.name}}</span>',
        '       </el-menu-item>',
        '       <my-menu-item v-if="children.type==\'0\'" :item="children"></my-menu-item>',
        '   </el-menu-item-group>',
        '</el-submenu>'
    ].join('')
});
Vue.component('myMenuItem', myMenuItem);
var vm = new Vue({
    el: '#app',
    created: function () {
        this.getLoginUser();
        this.getMenuList();
        this.iframeResize();
    },
    data: {
        isCollapse: false,
        dialogVisible: false,
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
        defaultActive: '首页:main.html',
        defaultTabsValue: '首页',
        tabs: [{title: '首页', name: '首页', content: 'main.html', breadcrumbs: []}],
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
            color: '#606266',
            height: '30px',
            textAlign: 'center',
            bottom: '0px',
            position: 'absolute',
            left: '200px',
            right: '0px',
            paddingTop: '5px'
        }
    },
    methods: {
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
            this.breadcrumbs = this.getIndexPath(indexPath);
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
        getIndexPath(indexPath) {
            let paths = [];
            indexPath.forEach((v, i) => {
                let path = v.split(':');
                paths.push(path[0]);
            });
            return paths;
        },
        getMenuList: function () {
            var that = this;
            httpUtil.get({url: "/sys/menu/nav?_" + $.now()}, function (r) {
                that.menuList = treeUtil.vueTree(r.data)
                console.log(that.menuList);
            });
        },
        /**
         * iframe 自适应高度
         */
        iframeResize() {
            let clientHeight = document.documentElement.clientHeight;
            this.iframeHeight = clientHeight - 145;
        },
        /**
         * 删除tab
         * @param targetName
         */
        removeTab(targetName) {
            let tabs = this.tabs;
            let activeName = this.defaultTabsValue;
            if (activeName === targetName) {
                tabs.forEach((tab, index) => {
                    if (tab.name === targetName) {
                        let nextTab = tabs[index + 1] || tabs[index - 1];
                        if (nextTab) {
                            activeName = nextTab.name;
                        }
                    }
                });
            }
            this.defaultTabsValue = activeName;
            this.tabs = tabs.filter(tab => tab.name !== targetName);
        },
        /**
         * 变更角色
         * @param command
         */
        roleChangeCommand(command) {
            var that = this;
            if (that.loginUser.role.id === command) {
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
        },
        /**
         * 操作
         * @param command
         */
        opChangeCommand(command) {
            if (command == 'updatePassword') {
                this.updatePassword();
            } else if (command == 'logout') {
                this.logout();
            }
        },
        /**
         * 退出
         */
        logout() {
            window.location.href = '/sys/logout'
        },
        updatePassword() {
            var that = this;
            that.dialogVisible = true;
            that.resetPasswordForm('passwordFormRef')
        },
        getMenuList: function (event) {
            var that = this;
            httpUtil.get({url: "/sys/menu/nav?_" + $.now()}, function (r) {
                that.menuList = treeUtil.vueTree(r.data);
            });
        },
        getLoginUser: function () {
            var that = this;
            httpUtil.get({url: "sys/user/info?_" + $.now()}, function (r) {
                that.loginUser = r.data;
            });
        },
        passwordSubmit: function () {
            var that = this;
            that.$refs['passwordFormRef'].validate((valid) => {
                if (valid) {
                    var data = "password=" + that.passwordForm.password + "&newPassword=" + that.passwordForm.newPassword;
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
            if (val) {
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
window.onresize = vm.iframeResize;