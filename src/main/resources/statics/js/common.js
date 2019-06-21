let baseURL = "../../";
//工具集合Tools
window.T = {
    cloud_storage_config_key: 'CLOUD_STORAGE_CONFIG_KEY',
    local_key: {
        dict_key_prefix: 'local_dict_key_'
    },
    local_storage_age: 24 * 60 * 60 * 1000  // 一天
    ,
    getBtnAuthCodes: function (tableColumns) {
        let btns = tableColumns[tableColumns.length - 1].buttons;
        let codes = [];
        if (btns) {
            for (let b in btns) {
                if (btns[b].auth) {
                    codes.push(btns[b].auth);
                }
            }
        }
        return codes.join(',');
    },
    getDictTypes: function (tableColumns) {
        let codes = [];
        if (tableColumns) {
            for (let b in tableColumns) {
                if (tableColumns[b].dict) {
                    codes.push(tableColumns[b].dict);
                }
            }
        }
        return codes.join(',');
    }
};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
T.p = function (name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

//日期格式化
T.dateFmt = function (fmt, date) {
    fmt = fmt || 'yyyy-MM-dd hh:mm:ss';
    let o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (let k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

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
    syncGet: function (option, successCallback, errorCallBack, completeCallBack) {
        option = option || {url: null, data: {}};
        errorCallBack = errorCallBack || function (e) {
        }
        completeCallBack = completeCallBack || function (c) {
        }
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
    },
    //fileDownload  异步
    fileDownload: function (that, option, prepareCallback, successCallback, failCallback) {
        option = option || {url: null, data: {}};
        let loading;
        prepareCallback = prepareCallback || function () {
            loading = that.$loading({
                lock: true,
                text: '导出中...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        }
        successCallback = successCallback || function () {
            that.$message({
                type: 'success',
                message: '导出成功'
            });
            loading.close();
        }
        failCallback = failCallback || function () {
            that.$message.error('导出失败');
            loading.close();
        }

        return $.fileDownload(baseURL + option.url || null, {
            data: option.data || {},
            prepareCallback: function () {
                console.log('export prepareCallback');
                (prepareCallback)();
            },
            successCallback: function () {
                console.log('export successCallback');
                (successCallback)();
            },
            failCallback: function (html, url) {
                console.log('export failCallback');
                (failCallback)(html, url);
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
        let rooArr = [];
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
        let rooArr = [];
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
        let rooArr = [];
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
        let rooArr = [];
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
        let sourceArr = arr || [];
        let rooArr = [];
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
            let childrenArr = [];
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
            childrenArr.sort(function (a,b) {
                if(a.sequence<b.sequence){
                    return -1;
                }
                if(a.sequence>b.sequence){
                    return 1;
                }
                return 0;
            })
            return childrenArr;
        }
        rooArr.sort(function (a,b) {
            if(a.sequence<b.sequence){
                return -1;
            }
            if(a.sequence>b.sequence){
                return 1;
            }
            return 0;
        })
        return rooArr;
    },

    vueTreeObj: function (arr) {
        let obj ={}
        if (!arr || arr.length == 0) return [];
        let sourceArr = arr || [];
        let rooArr = [];
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
            let childrenArr = [];
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
        obj.data=arr;
        obj.treeData=rooArr;
        return obj;
    },
    /**
     *
     * @param arr
     * @param id
     */
    getTreeNode: function (sourceArr, sourceId) {
        let value = null;

        for (let i = 0; i < sourceArr.length; i++) {
            if (sourceArr[i].id === sourceId) {
                value = sourceArr[i];
                break;
            }
            if (sourceArr[i].children != null && sourceArr[i].children.length > 0) {
                value = getChildren(sourceArr[i].children, sourceId);
            }
            if (value != null) break;
        }

        function getChildren(root, id) {
            let val = null;
            for (let i = 0; i < root.length; i++) {
                if (root[i].id === id) {
                    val = root[i];
                    break;
                }
                if (root[i].children != null && root[i].children.length > 0) {
                    val = getChildren(root[i].children, id);
                }
                if (val != null) break;
            }
            return val;
        }

        return value;
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
        let sourceArr = arr || [];
        let rooArr = [];
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
            let c = getChildren(v, sourceArr)
            if (c.length > 0) {
                v.children = c;
            }
        })

        function getChildren(root, arr) {
            let childrenArr = [];
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
                let c = getChildren(v, sourceArr)
                if (c.length > 0) {
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
        let local_dict_key_type = T.local_key.dict_key_prefix + type;
        let dicts = [];
        let dictArr = localStorageExports.get(local_dict_key_type);
        if (dictArr != null && dictArr != undefined) {
            return dictArr;
        } else {
            dicts = this.getDicts({type: type});
            localStorageExports.setAge(T.local_storage_age).set(local_dict_key_type, dicts);
        }
        return dicts;
    },
    /**
     * 根据CODE获取字典NAME
     * @param type
     * @returns {Array}
     */
    getNameByTypeAndCode: function (type, code) {
        let local_dict_key_type = T.local_key.dict_key_prefix + type;
        let name = code;
        let dictArr = localStorageExports.get(local_dict_key_type);
        if (dictArr != null) {
            dictArr.forEach(function (v, index, arr) {
                if (v.code == code) {
                    name = v.name;
                    return;
                }
            });
        } else {
            let dicts = this.getDicts({type: type});
            dicts.forEach(function (v, index, arr) {
                if (v.code == code) {
                    name = v.name;
                    return;
                }
            });
            localStorageExports.setAge(T.local_storage_age).set(local_dict_key_type, dicts)
        }
        return name;
    },
    /**
     * 获取字典
     * @param param
     * @returns {Array}
     */
    getDicts: function (param) {
        let dicts = [];
        param.orderBy = 'sequence-asc';
        httpUtil.syncGet({url: baseURL + 'sys/dict/list', data: param}, function (r) {
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
    isNull: function (str) {
        if (null == str || "" == str.trim()) {
            return true;
        } else {
            return false;
        }
    },
    //校验是否全是数字
    isDigit: function (str) {
        let patrn = /^\d+$/;
        return patrn.test(str);
    },
    //校验是否是整数
    isInteger: function (str) {
        let patrn = /^([+-]?)(\d+)$/;
        return patrn.test(str);
    },
    //非负整数
    nonNegative: function (str) {
        if (str === 0 || str === "0") {
            return true;
        } else {
            let patrn = /^[1-9]\d*$/;
            return patrn.test(str);
        }
    },
    //校验是否为正整数
    isPlusInteger: function (str) {
        //let patrn = /^([+]?)(\d+)$/;
        let patrn = /^[1-9]\d*$/;
        return patrn.test(str);
    },
    //校验是否为负整数
    isMinusInteger: function (str) {
        let patrn = /^-(\d+)$/;
        return patrn.test(str);
    },
    //校验是否为浮点数或整数
    isFloat: function (str) {
        let patrn = /^([+-]?)\d*(\.\d{1,5})?$/;
        return patrn.test(str);
    },
    //校验是否为正浮点数或整数
    isPosFloat: function (str) {
        let patrn = /^([+]?)\d*(\.\d{1,5})?$/;
        return patrn.test(str);
    },
    //校验是否为正浮点数或整数，小数电后保留3位
    isPlusFloat: function (str) {
        let patrn = /^([+]?)\d*(\.\d{1,3})?$/;
        return patrn.test(str);
    },
    //校验是否为负浮点数
    isMinusFloat: function (str) {
        let patrn = /^-\d*\.\d+$/;
        return patrn.test(str);
    },
    //校验是否仅中文
    isChinese: function (str) {
        let patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]{1,20}$/;
        return patrn.test(str);
    },
    //校验是否仅ACSII字符
    isAcsii: function (str) {
        let patrn = /^[\x00-\xFF]+$/;
        return patrn.test(str);
    },
    //校验手机号码
    isMobile: function (str) {
        //let patrn = /^0?1((3[0-9]{1})|(59)){1}[0-9]{8}$/;
        let patrn = /^0?1(((3|5|8)[0-9]{1})){1}[0-9]{8}$/;
        return patrn.test(str);
    },
    //校验电话号码
    isPhone: function (str) {
        let patrn = /^(0[\d]{2,3}-)?\d{6,8}(-\d{3,4})?$/;
        return patrn.test(str);
    },
    // 简单的校验手机号
    isSimpleMobile: function (str) {
        let pattern = /^[0-9]{11}$/;
        return pattern.test(str);
    },
    //校验URL地址
    isUrl: function (str) {
        let patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
        return patrn.test(str);
    },
    //校验电邮地址
    isEmail: function (str) {
        let patrn = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;///^[\w-]+@[\w-]+(\.[\w-]+)+$/;
        return patrn.test(str);
    },
    //校验邮编
    isZipCode: function (str) {
        let patrn = /^\d{6}$/;
        return patrn.test(str);
    },
    //校验合法时间
    isDate: function (str) {
        if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
            return false;
        }
        let r = str.match(/\d{1,4}/g);
        if (r == null) {
            return false;
        }
        let d = new Date(r[0], r[1] - 1, r[2]);
        return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d
            .getDate() == r[2]);
    },
    //校验字符串：只能输入1-20个字母、数字、下划线(常用手校验用户名和密码)
    isString1_20: function (str) {
        let patrn = /^(\w){1,20}$/;
        return patrn.test(str);
    },
    //校验非空白字符
    isNotNull1_20: function (str) {
        let patrn = /^(\S){1,20}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入1-6个中文、字母、数字、下划线
    isAllString1_20: function (str) {
        let patrn = /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]{1,20}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入个中文、字母、数字、下划线
    isAllString1: function (str) {
        let patrn = /^(?!_)(?!.*?_$)[a-zA-Z0-9\s_\u4e00-\u9fa5]{1,100}$/;
        return patrn.test(str);
    },
    //校验字符串：只能输入个字母、数字
    isAllString2: function (str) {
        let patrn = /^[A-Za-z0-9]+$/;
        return patrn.test(str);
    },
    //校验是否有特殊字符
    containtSpecialChar: function (str) {
        let pattern = /[(\ )(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\-)(\_)(\+)(\=)(\[)(\])(\{)(\})(\|)(\\)(\;)(\:)(\')(\")(\,)(\.)(\/)(\<)(\>)(\?)(\)]+/;
        return pattern.test(str);
    },
    //校验是否有中文字符
    containtChineneChars: function (str) {
        let pattern = /.*[\u4e00-\u9fa5]+.*$/;
        return pattern.test(str);
    },
    //校验是否有特殊字符(%'&^)
    isSpecialChar: function (str) {
        return str.search(/[%'&^]/) != -1;
    }
}
/**
 *  设置本地缓存
 * @type {{set: (function(*=, *): Window.localStorageExports), get: (function(*=): *), isExpire: (function(*=): boolean), age: number, remove: Window.localStorageExports.remove, setAge: (function(*): Window.localStorageExports)}}
 */
window.localStorageExports = {
    // 过期时间，默认30天
    age: 30 * 24 * 60 * 60 * 1000,
    /**
     * 设置过期时间
     * @param age
     * @returns {exports}
     */
    setAge: function (age) {
        this.age = age;
        return this;
    },
    /**
     * 设置 localStorage
     * @param key
     * @param value
     */
    set: function (key, value) {
        localStorage.removeItem(key);
        let _time = new Date().getTime()
            , _age = this.age;

        // 如果不是对象，新建一个对象把 value 存起来
        let obj = {};
        obj._value = value;
        // 加入时间
        obj._time = _time;
        // 过期时间
        obj._age = _time + _age;
        localStorage.setItem(key, JSON.stringify(obj));
        return this;
    },
    /**
     * 删除 localStorage
     * @param key
     */
    remove: function (key) {
        localStorage.removeItem(key);
    },
    /**
     * 判断一个 localStorage 是否过期
     * @param key
     * @returns {boolean}
     */
    isExpire: function (key) {

        let isExpire = true,
            value = localStorage.getItem(key),
            now = new Date().getTime();

        if (value) {
            value = JSON.parse(value);
            // 当前时间是否大于过期时间
            isExpire = now > value._age;
        } else {
            // 没有值也是过期
        }
        return isExpire;
    },
    /**
     * 获取某个 localStorage 值
     * @param key
     * @returns {*}
     */
    get: function (key) {
        let isExpire = this.isExpire(key),
            value = null;
        if (!isExpire) {
            value = localStorage.getItem(key);
            value = JSON.parse(value);
            value = value._value;
        }
        return value;
    }
}