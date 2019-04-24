var baseURL = "../../";
//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

T.p = url;

window.AjaxContentType = {
    'JSON': 'application/json;charset=utf-8',
    'URL': 'application/x-www-form-urlencoded',
    'FORM': 'multipart/form-data',
    'HTML': 'text/xml'
};
window.AjaxType = {'POST': 'POST', 'GET': 'GET', 'DELETE': 'DELETE', 'PUT': 'PUT'};

/**
 * httpUtil
 * ajax调用，get,post,put,delete，返回数据格式为json调用，返回数据格式为json
 *
 * @param url：请求地址
 * @param data：请求参数
 * @param data：1. application/x-www-form-urlencoded   2. application/json;charset=utf-8  3. multipart/form-data 4. text/xml
 * @param successCallback：请求成功（接口返回的状态为“成功”）时调用的函数
 */
window.httpUtil = {
    //get 异步
    get: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {url: null, data: {}};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
        // 返回ajax实例以便可以缓存它
        return $.ajax({
            url: baseURL + option.url || null,
            data: option.data || {},
            type: option.type || AjaxType.GET,
            dataType: option.dataType || 'json',
            cache: option.cache || false,
            success: function (result) {
                (successCallback)(result);
            },
            error: function (e) {
                (errorCallBack)(e);
            },
            complete: function (c) {
                (completeCallBack)(c);
            }
        });
    },
    //同步方法
    asyncGet: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {url: null, data: {}};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
        // 返回ajax实例以便可以缓存它
        return $.ajax({
            url: option.url || null,
            data: option.data || {},
            type: option.type || AjaxType.GET,
            dataType: option.dataType || 'json',
            cache: option.cache || false,
            async: false,
            success: function (result) {
                (successCallback)(result);
            },
            error: function (e) {
                (errorCallBack)(e);
            },
            complete: function (c) {
                (completeCallBack)(c);
            }
        });
    },
    //post  异步
    post: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
        // 返回ajax实例以便可以缓存它
        return $.ajax({
            url: baseURL + option.url || '',
            data: option.data || {},
            type: option.type || AjaxType.POST,
            dataType: option.dataType || 'json',
            cache: option.cache || false,
            contentType: option.contentType || AjaxContentType.JSON,
            success: function (result) {
                (successCallback)(result);
            },
            error: function (e) {
                (errorCallBack)(e);
            },
            complete: function (c) {
                (completeCallBack)(c);
            }
        });
    },
    //put 异步
    put: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {url: null, data: {}};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
        // 返回ajax实例以便可以缓存它
        return $.ajax({
            url: baseURL + option.url || null,
            data: option.data || {},
            type: option.type || AjaxType.PUT,
            dataType: option.dataType || 'json',
            cache: option.cache || false,
            contentType: option.contentType || AjaxContentType.JSON,
            success: function (result) {
                (successCallback)(result);
            },
            error: function (e) {
                (errorCallBack)(e);
            },
            complete: function (c) {
                (completeCallBack)(c);
            }
        });
    },
    //delete  异步
    del: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {url: null, data: {}};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
        // 返回ajax实例以便可以缓存它
        return $.ajax({
            url: baseURL + option.url || null,
            data: option.data || {},
            type: option.type || AjaxType.DELETE,
            dataType: option.dataType || 'json',
            cache: option.cache || false,
            success: function (result) {
                (successCallback)(result);
            },
            error: function (e) {
                (errorCallBack)(e);
            },
            complete: function (c) {
                (completeCallBack)(c);
            }
        });
    }
}
/**
 *
 * @type {{vueTree: vueTree}}
 */
window.treeUtil = {
    /**
     * 获取IDs
     * @param arr
     * @returns {Array}
     */
    ids: function (arr) {
        if (!arr || arr.length == 0) return [];
        var rooArr = [];
        arr.forEach(function (v) {
            if (!v.disabled) {
                rooArr.push(v.id);
            }
        });
        return rooArr;
    },
    /**
     * 获取树IDs
     * @param arr
     * @returns {Array}
     */
    treeToIds: function (arr) {
        if (!arr || arr.length == 0) return [];
        var rooArr = [];
        arr.forEach(function (v) {
            if (!v.disabled) {
                rooArr.push(v.id);
            }
            if (v.children.length > 0) {
                appenChildren(v.children, rooArr);
            }
        });

        function appenChildren(childrens, arr) {
            childrens.forEach(function (v) {
                if (!v.disabled) {
                    rooArr.push(v.id);
                }
                if (v.children.length > 0) {
                    appenChildren(v.children, arr);
                }
            });
        }

        return rooArr;
    },
    /**
     * 获取权限ID
     * @param arr
     * @returns {Array}
     */
    permissionIds: function (arr) {
        if (!arr || arr.length == 0) return [];
        var rooArr = [];
        arr.forEach(function (v) {
            if (v.permissionId != null) {
                rooArr.push(v.permissionId);
            }
        });
        return rooArr;
    },
    /**
     * 获取角色ID
     * @param arr
     * @returns {Array}
     */
    roleIds: function (arr) {
        if (!arr || arr.length == 0) return [];
        var rooArr = [];
        arr.forEach(function (v) {
            if (v.roleId != null) {
                rooArr.push(v.roleId);
            }
        });
        return rooArr;
    },
    /**
     * 角色处理
     * @param arr
     * @returns {*}
     */
    roles: function (roleArr) {
        if (!roleArr || roleArr.length == 0) return [];
        roleArr.forEach(function (v) {
            if ((v.enabled && v.enabled == 'N') || (v.available && v.available == 'N')) {
                v.disabled = true;
            }
        });
        return roleArr;
    },
    /**
     * vue vueTree
     * @param arr
     * @returns {Array}
     */

    vueTree: function (arr) {
        if (!arr || arr.length == 0) return [];
        var sourceArr = arr || [];
        var rooArr = [];
        sourceArr.forEach(function (v, index, arr) {
            if (v.parentId == null || v.parentId == '0') {
                rooArr.push(v);
            }
        });
        rooArr.forEach(function (v, index, arr) {
            v.label = v.name
            v.value = v.id
            if (v.parentIds) {
                v.parentIds = v.parentIds.split(",");
            } else {
                v.parentIds = []
            }
            if ((v.enabled && v.enabled == 'N') || (v.available && v.available == 'N')) {
                v.disabled = true;
            }
            v.children = getChildren(v, sourceArr);
        })

        function getChildren(root, arr) {
            var childrenArr = [];
            sourceArr.forEach(function (v, index, arr) {
                if (v.parentId == root.id) {
                    childrenArr.push(v);
                }
            });
            childrenArr.forEach(function (v, index, arr) {
                v.label = v.name
                v.value = v.id
                if (v.parentIds) {
                    v.parentIds = v.parentIds.split(",");
                } else {
                    v.parentIds = []
                }
                if ((v.enabled && v.enabled == 'N') || (v.available && v.available == 'N') || root.disabled) {
                    v.disabled = true;
                }
                v.children = getChildren(v, sourceArr);
            });
            if (childrenArr.length == 0) {
                return null;
            }
            return childrenArr;
        }

        return rooArr;
    }
}

/**
 *
 * @type {{vueTree: vueTree}}
 */
window.areaUtil = {
    /**
     * vue vueTree
     * @param arr
     * @returns {Array}
     */

    vueTree: function (arr) {
        if (!arr || arr.length == 0) return [];
        var sourceArr = arr || [];
        var rooArr = [];
        sourceArr.forEach(function (v, index, arr) {
            if (v.parentId == null || v.parentId == '0') {
                rooArr.push(v);
            }
        });
        rooArr.forEach(function (v, index, arr) {
            v.label = v.name
            v.value = v.id
            if (v.parentIds) {
                v.parentIds = v.parentIds.split(",");
            } else {
                v.parentIds = []
            }

            if ((v.enabled && v.enabled == 'N') || (v.available && v.available == 'N')) {
                v.disabled = true;
            }
            var c = getChildren(v, sourceArr)
            if(c.length>0){
                v.children = c;
            }
        })

        function getChildren(root, arr) {
            var childrenArr = [];
            sourceArr.forEach(function (v, index, arr) {
                if (v.parentId == root.id) {
                    childrenArr.push(v);
                }
            });
            childrenArr.forEach(function (v, index, arr) {
                v.label = v.name
                v.value = v.id
                if (v.parentIds) {
                    v.parentIds = v.parentIds.split(",");
                } else {
                    v.parentIds = []
                }

                if ((v.enabled && v.enabled == 'N') || (v.available && v.available == 'N') || root.disabled) {
                    v.disabled = true;
                }
                var c = getChildren(v, sourceArr)
                if(c.length>0){
                    v.children = c;
                }
            });
            if (childrenArr.length == 0) {
                return [];
            }
            return childrenArr;
        }

        return rooArr;
    }
}

window.dictUtil = {
    /**
     * 根据类型获取字典
     * @param type
     * @returns {Array}
     */
    getDictsByType: function (type) {
        var local_dict_key_type = 'local_dict_key_' + type;
        var dicts = [];
        var dictArr = localStorage.getItem(local_dict_key_type);
        // var dictArr = null;
        if (dictArr != null) {
            return JSON.parse(dictArr);
        } else {
            dicts = this.getDicts({type: type});
            localStorage.setItem(local_dict_key_type, JSON.stringify(dicts));
        }
        return dicts;
    },
    /**
     * 根据CODE获取字典NAME
     * @param type
     * @returns {Array}
     */
    getNameByTypeAndCode: function (type, code) {
        var local_dict_key_type = 'local_dict_key_' + type;
        var name = code;
        var dictArr = localStorage.getItem(local_dict_key_type);
        // var dictArr = null;
        if (dictArr != null) {
            var dicts = JSON.parse(dictArr);
            dicts.forEach(function (v, index, arr) {
                if (v.code == code) {
                    name = v.name;
                    return;
                }
            });
        } else {
            var dicts = this.getDicts({type: type});
            dicts.forEach(function (v, index, arr) {
                if (v.code == code) {
                    name = v.name;
                    return;
                }
            });
            localStorage.setItem(local_dict_key_type, JSON.stringify(dicts));
        }
        return name;
    },
    /**
     * 获取字典
     * @param param
     * @returns {Array}
     */
    getDicts: function (param) {
        var dicts = [];
        param.orderBy = 'sequence-asc';
        httpUtil.asyncGet({url: baseURL + 'sys/dict/list', data: param}, function (r) {
            dicts = r.data.list;
            dicts.forEach(function (v, index, arr) {
                if (v.enabled == 'N') {
                    v.disabled = true;
                }
            });
        })
        return dicts;
    }
}

//重写alert
window.alertMsg = function (that, result) {
    if (!result.msg) return;
    if (result.code == 0) {
        that.$message({
            type: 'success',
            message: result.msg
        });
    } else {
        that.$message.error(result.msg);
    }
}
window.checkUtil = {
    //校验是否为空(先删除二边空格再验证)
    isNull: function(str) {
        if (null == str || "" == str.trim()) {
            return true;
        } else {
            return false;
        }
    },
    //校验是否全是数字
    isDigit: function(str) {
        var patrn = /^\d+$/;
        return patrn.test(str);
    },
    //校验是否是整数
    isInteger: function(str) {
        var patrn = /^([+-]?)(\d+)$/;
        return patrn.test(str);
    },
    //非负整数
    nonNegative: function(str) {
        if(str === 0 || str === "0"){
            return true;
        }else{
            var patrn = /^[1-9]\d*$/;
            return patrn.test(str);
        }
    },
    //校验是否为正整数
    isPlusInteger: function(str) {
        //var patrn = /^([+]?)(\d+)$/;
        var patrn = /^[1-9]\d*$/;
        return patrn.test(str);
    },
    //校验是否为负整数
    isMinusInteger : function(str) {
        var patrn = /^-(\d+)$/;
        return patrn.test(str);
    },
    //校验是否为浮点数或整数
    isFloat: function(str) {
        var patrn = /^([+-]?)\d*(\.\d{1,5})?$/;
        return patrn.test(str);
    },
    //校验是否为正浮点数或整数
    isPosFloat: function(str) {
        var patrn = /^([+]?)\d*(\.\d{1,5})?$/;
        return patrn.test(str);
    },
    //校验是否为正浮点数或整数，小数电后保留3位
    isPlusFloat: function(str) {
        var patrn = /^([+]?)\d*(\.\d{1,3})?$/;
        return patrn.test(str);
    },
    //校验是否为负浮点数
    isMinusFloat: function(str) {
        var patrn = /^-\d*\.\d+$/;
        return patrn.test(str);
    },
    //校验是否仅中文
    isChinese: function(str) {
        var patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]{1,20}$/;
        return patrn.test(str);
    },
    //校验是否仅ACSII字符
    isAcsii: function(str) {
        var patrn = /^[\x00-\xFF]+$/;
        return patrn.test(str);
    },
    //校验手机号码
    isMobile: function(str) {
        //var patrn = /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
        var patrn = /^0?1(((3|5|8)[0-9]{1})){1}[0-9]{8}$/;
        return patrn.test(str);
    },
    //校验电话号码
    isPhone: function(str) {
        var patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
        return patrn.test(str);
    },
    // 简单的校验手机号
    isSimpleMobile: function(str) {
        var pattern = /^[0-9]{11}$/;
        return pattern.test(str);
    },
    //校验URL地址
    isUrl: function(str) {
        var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
        return patrn.test(str);
    },
    //校验电邮地址
    isEmail: function(str) {
        var patrn = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;///^[\w-]+@[\w-]+(\.[\w-]+)+$/;
        return patrn.test(str);
    },
    //校验邮编
    isZipCode: function(str) {
        var patrn = /^\d{6}$/;
        return patrn.test(str);
    },
    //校验合法时间
    isDate: function(str) {
        if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
            return false;
        }
        var r = str.match(/\d{1,4}/g);
        if (r == null) {
            return false;
        }
        var d = new Date(r[0], r[1] - 1, r[2]);
        return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d
            .getDate() == r[2]);
    },
    //校验字符串：只能输入1-20个字母、数字、下划线(常用手校验用户名和密码)
    isString1_20: function(str) {
        var patrn = /^(\w){1,20}$/;
        return patrn.test(str);
    },
    //校验非空白字符
    isNotNull1_20: function(str) {
        var patrn = /^(\S){1,20}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入1-6个中文、字母、数字、下划线
    isAllString1_20: function(str) {
        var patrn = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入个中文、字母、数字、下划线
    isAllString1: function(str) {
        var patrn = /^(?!_)(?!.*?_$)[a-zA-Z0-9\s_\u4e00-\u9fa5]{1,100}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入个字母、数字
    isAllString2: function(str) {
        var patrn = /^[A-Za-z0-9]+$/;
        return patrn.test(str);
    },
    //校验是否有特殊字符
    containtSpecialChar: function(str){
        var pattern = /[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
        return pattern.test(str);
    },
    //校验是否有中文字符
    containtChineneChars: function(str){
        var pattern =/.*[\u4e00-\u9fa5]+.*$/;
        return pattern.test(str);
    },
    //校验是否有特殊字符(%'&^)
    isSpecialChar: function(str){

        return str.search(/[%'&^]/) != -1;
    }
}
