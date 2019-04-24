//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li v-if="item.enabled ===\'Y\'">',
        '	<a v-if="item.type === \'0\'" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === \'0\'" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.children"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === \'1\' && item.parentId === \'0\'" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.type === \'1\' && item.parentId != \'0\'" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

// iframe自适应
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem', menuItem);
new Vue({
    el: '#vueindexapp',
    data() {
        return {
            dialogVisible: false,
            rules: {
                password: [{required: true, message: '请输入原密码', trigger: 'blur'}],
                newPassword: [{required: true, message: '请输入新密码', trigger: 'blur'}]
            },
            passwordForm: {password: null, newPassword: null},
            user: {},
            menuList: {},
            main: "main.html",
            navTitle: "控制台"
        }
    },
    created: function () {
        var that = this;
        that.getMenuList();
        that.getUser();
    },
    updated: function () {
        var that = this;
        //路由
        var router = new Router();
        that.routerList(router, that.menuList);
        router.start();
    },
    methods: {
        roleChangeCommand(command) {
            var that = this;
            if (that.user.role.id === command) {
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
        indexClick() {
            var that = this;
            that.main = 'main.html';
            that.navTitle = '控制台';
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
        getUser: function () {
            var that = this;
            httpUtil.get({url: "sys/user/info?_" + $.now()}, function (r) {
                that.user = r.data;
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
            this.$refs[formName].resetFields();
        },
        routerList(router, menuList) {
            var that = this;
            for (var key in menuList) {
                var menu = menuList[key];
                if (menu.type == 0) {
                    that.routerList(router, menu.children);
                } else if (menu.type == 1) {
                    router.add('#' + menu.url, function () {
                        var url = window.location.hash;
                        //替换iframe的url
                        that.main = url.replace('#', '');
                        //导航菜单展开
                        $(".treeview-menu li").removeClass("active");
                        $("a[href='" + url + "']").parents("li").addClass("active");
                        var titleStr = $("a[href='" + url + "']").parents('ul').prev().text() || "";
                        var titles = titleStr.split(' ').filter(d => d).reverse() || [];
                        titles.push($("a[href='" + url + "']").text())
                        that.navTitle = titles.join("/");
                    });
                }
            }
        }
    }
});