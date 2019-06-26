let myMenuItem = Vue.extend({
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
        '       <my-menu-item v-else-if="children.type==\'0\'" :item="children"></my-menu-item>',
        '   </el-menu-item-group>',
        '</el-submenu>'
    ].join('')
});
let myDictSelectT = Vue.extend({
    name: 'my-dict-select',
    props: {
        value: '',
        icon: {default: false},
        type: {
            default: null
        },
        disabled: {
            default: false
        }
    },
    data() {
        return {
            isShowIcon: {default: false},
            model: '',
            options: []
        }
    },
    created (){
    },
    mounted() {
        this.loadData();
    },
    methods: {
        handleFocus(event) {
            this.$emit('focus', event);
        },
        handleBlur(event) {
            this.$emit('blur', event);
        },
        emitChange(val) {
            this.$emit('change', val);
        },
        emitInput(val) {
            this.$emit('input', val);
        },
        loadData() {
            let that = this;
            if (that.type === undefined || that.type === '' || that.type === null) {
                return;
            }
            if(that.icon &&  that.icon==='true'){
                that.isShowIcon = true;
            }
            that.options = dictUtil.getDictsByType(that.type);
            if(that.value) that.model = that.value;
        }
    },
    watch: {
        value(val) {
            this.model = val;
        }
    },
    template: [
        '<el-select value=""  :disabled="disabled" filterable clearable v-model="model" v-on:focus="handleFocus($event)" v-on:handleBlur="handleBlur($event)" v-on:change="emitChange" v-on:input="emitInput"  placeholder="请选择">',
        '<el-option v-if="isShowIcon"  v-for="item in options" :label="item.name" :disabled="item.disabled" :value="item.code" :key="item.code"">',
            '<span style="float: left">{{ item.name }}</span>',
            '<span style="float: right; color: #8492a6; font-size: 13px"> <i :class="item.code"></i></span>',
        '</el-option>',
        '<el-option  v-else v-for="item in options" :label="item.name" :disabled="item.disabled" :value="item.code" :key="item.code""></el-option>',
        '</el-select>'
    ].join('')
});
let myDictSpan = Vue.extend({
    name: 'my-dict-span',
    props: {
        value: '',
        type: {
            default: null
        }
    },
    data() {
        return {
            model: '',
            options: []
        }
    },
    created (){
    },
    mounted() {
        this.loadData();
    },
    methods: {
        loadData() {
            let that = this;
            if (that.type === undefined || that.type === '' || that.type === null) {
                return;
            }
            that.value = dictUtil.getNameByTypeAndCode(that.type, that.value);
            if(that.value) that.model = that.value;
        }
    },
    template: [
        '<span value="">{{model}}',
        '</span>'
    ].join('')
});
let myAreaSelectT = Vue.extend({
    name: 'my-area-select',
    props: {
        value: '',
        showNone: {default: true},
        type: {
            default: null
        },
        disabled: {
            default: false
        }
    },
    data() {
        return {
            model: '',
            options: []
        }
    },
    mounted() {
        this.loadData();
    },
    methods: {
        handleFocus(event) {
            this.$emit('focus', event);
        },
        handleBlur(event) {
            this.$emit('blur', event);
        },
        emitChange(val) {
            this.$emit('change', val);
        },
        emitInput(val) {
            this.$emit('input', val);
        },
        loadData() {
            let that = this
            if (that.type === undefined || that.type === '' || that.type === null) {
                return;
            }
            that.options = dictUtil.getDictsByType(that.type);
        }
    },
    watch: {
        value(val) {
            this.model = val;
        }
    },
    template: [
        '<el-select value="" :showNone="showNone" :disabled="disabled" v-model="model" v-on:focus="handleFocus($event)" v-on:handleBlur="handleBlur($event)" v-on:change="emitChange" v-on:input="emitInput"  placeholder="请选择">',
        '<el-option label="不限" value="" v-if="showNone"></el-option>',
        '<el-option  v-for="item in options" :label="item.name" :disabled="item.disabled" :value="item.code" :key="item.code""></el-option>',
        '</el-select>'
    ].join('')
});
let myBtnT = Vue.extend({
    name: 'my-btn',
    props: {
        label: {default: ''},
        auth: {default: ''},
        type: {default: 'primary'},
        disabled: {default: false},
        icon: {default: ''}
    },
    data() {
        return {
            isShow: true
        }
    },
    mounted() {
        this.load();
    },
    methods: {
        load() {
            let that = this;
            if (that.auth != null && that.auth != '') {
                httpUtil.get({url: '/check/permissions', data: {codes: that.auth}}, function (r) {
                    if (r.code == 0) {
                        that.isShow = r.data[that.auth.split(":").join("")];
                    }
                })
            }
        },
        click(val) {
            this.$emit('click', val)
        },
    },
    template: [
        '<el-button :type="type" :icon="icon"  @click="click" :disabled="disabled" v-if="isShow">{{label}}</el-button>',
    ].join('')
});
let myPaginationT = Vue.extend({
    name: 'my-pagination',
    props: {
        page: {
            default: function () {
                return {
                    pageSizes: [10, 20, 50, 100],
                    currPage: 1,
                    pageSize: 10,
                    totalPage: 1,
                    totalCount: 0
                }
            }
        }
    },
    data() {
        return {}
    },
    mounted() {
    },
    methods: {
        // 页面大小改变重新查询数据
        pagesizechange: function (val) {
            this.$emit('pagesizechange', val);
        },
        // 页码改变加载对应页码数据
        currentpagechange: function (val) {
            this.$emit('currentpagechange', val);
        },
    },
    template: [
        '<div>',
        '<el-pagination v-if="page.totalCount>0" style="text-align: right; margin-top: 10px;margin-right: 10px;" @size-change="pagesizechange" @current-change="currentpagechange" :current-page="page.currPage" :page-sizes="page.pageSizes"  :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper" :total="page.totalCount">',
        '</el-pagination>',
        '</div>',
    ].join('')
});
let myTableT = Vue.extend({
    name: 'my-table',
    props: {
        showPage: {
            default: true
        },
        defaultSort: {
            default: function () {
                return {}
            }
        },
        columns: {
            default: function () {
                return []
            }
        },
        page: {
            default: function () {
                return {
                    expand: {default: false}, // 是否展开行
                    pageSizes: [10, 20, 50, 100],
                    currPage: 1,
                    pageSize: 10,
                    totalPage: 1,
                    totalCount: 0,
                    list: []
                }
            }
        }
    },
    data() {
        return {btnsAuth: {}}
    },
    mounted() {
        this.btnAuth();
    },
    methods: {
        // 按钮权限
        btnAuth() {
            let that = this;
            let btns = that.columns[that.columns.length - 1].buttons;
            if (btns) {
                let codes = [];
                for (let b in btns) {
                    if (btns[b].auth) {
                        codes.push(btns[b].auth);
                    }
                }
                httpUtil.get({url: '/check/permissions', data: {codes: codes.join(',')}}, function (r) {
                    if(r.code ==0){
                        that.btnsAuth = r.data;
                    }
                })
            }
        },
        //排序
        sortChange(val) {
            if (val && val.column.sortable != null) {
                //加了sortBy字段才返回
                if (val.column.sortBy) {
                    if (val.order === 'descending') {
                        val.sortBy = val.column.sortBy + "-desc";
                    } else {
                        val.sortBy = val.column.sortBy + "-asc";
                    }
                    this.$emit('sortchange', val);
                }
            }
        },
        // 页面大小改变重新查询数据
        pagesizechange(val) {
            this.$emit('pagesizechange', val);
        },
        // 页码改变加载对应页码数据
        currentpagechange(val) {
            this.$emit('currentpagechange', val);
        },
        getDeepValue(row, rowKey){
            let keys = rowKey.split('.');
            let value = row;
            for(let i=0;i<keys.length;i++){
                try {
                    value = value[keys[i]];
                } catch (e) {
                }
            }
            return value || '';
        },
        getDictLabel(type, row, rowKey) {
            let value = '' ;
            value = dictUtil.getNameByTypeAndCode(type, this.getDeepValue(row,rowKey));
            return value;
        },
        getImage (row, rowKey) {
            let value = this.getDeepValue(row,rowKey);
            if(!value){
                value= '/statics/image/headPic.jpg';
            }
            let image = '<img width="30px" height="30px" src="' + value + '"/>'
            return image;
        },
        getIcon (row, rowKey) {
            let value = this.getDeepValue(row,rowKey);
            let icon = '<i class="'+value+'"></i>'
            return icon;
        },
        getDateFormat (row, rowKey,dateFormat) {
            let value = this.getDeepValue(row,rowKey);
            if(value && dateFormat){
                value= T.dateFmt(dateFormat,new Date(value));
            }
            return value;
        },
        checkBtnAuth(auth,row) {
            if(row &&  row.version == 0){
                let isAllAuth =  this.btnsAuth['IS_ALL_AUTH'] || false;
                let defaultDataAuth =  this.btnsAuth['DEFAULT_DATA_AUTH'] || false;
                if(isAllAuth){
                    return true;
                }else if(!defaultDataAuth){
                    return false;
                }
            }
            if (auth) {
                try {
                    return this.btnsAuth[auth.split(":").join("")];
                } catch (e) {
                    console.log(e);
                    return true;
                }
            } else {
                return true;
            }
        },
        btnDisabled(disabled, index, row) {
            if (typeof disabled === 'function') {
                return disabled(index, row);
            } else {
                return disabled;
            }
        }
    },
    template: [
        '<div>',
        '<el-table :default-sort="defaultSort" @sort-change="sortChange"  border stripe size="mini" style="width: 100%" :data="page.list">',
        ' <el-table-column type="expand" v-if="page.expand">',
        '   <template slot-scope="props">',
        '       <el-form label-position="left" inline class="my-table-expand">',
        '           <el-form-item :label="item.label"  v-for="item in columns" v-if="item.label!==\'操作\'">',
        '           <span v-if="item.date">：{{ getDateFormat(props.row,item.name,item.date)}}</span>',
        '           <span v-else-if="item.dict">：{{ getDictLabel(item.dict,props.row,item.name)}}</span>',
        '           <span v-else-if="item.image" v-html="getImage(props.row,item.name)"></span>',
        '           <span v-else-if="item.icon" v-html="getIcon(props.row,item.name)"></span>',
        '           <span v-else>：{{ getDeepValue(props.row,item.name)}}</span>',
        '           </el-form-item>',
        '       </el-form>',
        '   </template>',
        ' </el-table-column >',
        '<template  v-for="item in columns">',
        '<el-table-column v-if="item.buttons" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width" :fixed="item.fixed">',
        '<template slot-scope="scope">',
        '<el-button v-for="btn in item.buttons"  @click.native.prevent="btn.click(scope.$index, scope.row) " v-if="checkBtnAuth(btn.auth,scope.row)" :type="btn.type?btn.type:\'text\'" :icon="btn.icon?btn.icon:\'\'" style="margin-bottom: 5px;" size="small" :disabled="btn.disabled">',
        '<template v-if="btn.label">{{btn.label}}</template>',
        '</el-button>',
        '</template>',
        '</el-table-column>',
        '<el-table-column v-else-if="item.dict" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width"  :fixed="item.fixed">',
        '<template slot-scope="scope"> {{getDictLabel(item.dict, scope.row, item.name)}}</template>',
        '</el-table-column>',
        '<el-table-column v-else-if="item.html" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width"  :fixed="item.fixed">',
        '<template slot-scope="scope">',
        '<div v-if="item.html" v-html="item.html(scope.row)"></div>',
        '</template>',
        '</el-table-column>',
        '<el-table-column v-else-if="item.image" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width">',
        '<template slot-scope="scope">',
        '<div v-if="item.image" v-html="getImage(scope.row,item.name)"></div>',
        '</template>',
        '</el-table-column>',
        '<el-table-column v-else-if="item.icon" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width">',
        '<template slot-scope="scope">',
        '<div v-if="item.icon" v-html="getIcon(scope.row,item.name)"></div>',
        '</template>',
        '</el-table-column>',
        '<el-table-column v-else-if="item.date" :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width">',
        '<template slot-scope="scope"> {{getDateFormat(scope.row,item.name,item.date)}}</template>',
        '</el-table-column>',
        '<el-table-column show-overflow-tooltip v-else :prop="item.name" :label="item.label" :key="item.name" :formatter="item.formatter" :width="item.width" :fixed="item.fixed" :sort-by="item.sortBy" :sortable="item.sortable?item.sortable:false">',
        '</el-table-column>',
        '</template>',
        '</el-table>',
        '<my-pagination v-on:pagesizechange="pagesizechange" v-on:currentpagechange="currentpagechange" :page="page">',
        '</my-pagination>',
        '</div>',
    ].join('')
});

