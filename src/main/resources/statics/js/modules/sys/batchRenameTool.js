Vue.component('myPagination', myPaginationT);
Vue.component('myTable', myTableT);
let myDiv = Vue.extend({
    template: '<div></div>'
})
Vue.component('myDiv', myDiv);
Vue.component('myDictSelect', myDictSelectT);
Vue.component('myBtn', myBtnT);

let batchrenametoolapp = new Vue({
    el: '#batchrenametoolapp',
    data() {
        return {
            queryForm: {
                pageEnabled: true,
                pageNo: 1,
                pageSize: 10
            },
            tableColumns: [
                {
                    name: 'willRename',
                    label: '是否变化',
                    formatter: function (row, column, cellValue, index) {
                        return cellValue ? '是': '否';
                    }
                },
                {
                    align: 'left',
                    name: 'oldName',
                    label: '旧名'
                },
                {
                    align: 'left',
                    name: 'newName',
                    label: '新名'
                },
                {
                    align: 'left',
                    name: 'file',
                    label: '文件路径'
                }
            ],
            page: {},
            previewConfigVisible: false,
            toolForm: {
                toolFormLabelWidth: '200px',
                renameConfig: {
                    directory: null,
                    mode: 'TIMESTAMP',
                    oldText: null,
                    newText: null,
                    prefix: null,
                    suffix: null,
                    pattern: null,
                    replacement: null,
                    startNumber: 1,
                    step: 1,
                    preview: null,
                    recursive: true
                }
            },
            rules: {
                toolForm:{
                    directory: [{required: true, message: '必填', trigger: 'blur'}],
                    oldText: [{required: true, message: '必填', trigger: 'blur'}],
                    newText: [{required: true, message: '必填', trigger: 'blur'}],
                    prefix: [{required: true, message: '必填', trigger: 'blur'}],
                    suffix: [{required: true, message: '必填', trigger: 'blur'}],
                    pattern: [{required: true, message: '必填', trigger: 'blur'}],
                    //replacement: [{required: true, message: '必填', trigger: 'blur'}],
                    startNumber: [{required: true, message: '必填', trigger: 'blur'}],
                    step: [{required: true, message: '必填', trigger: 'blur'}],
                    mode: [{required: true, message: '必选', trigger: 'change'}],
                    recursive: [{required: true, message: '必选', trigger: 'change'}]
                }

            }
        }
    },
    created: function () {
    },
    methods: {
        sortchange(val){
            let that = this;
            that.queryForm.orderBy=val.sortBy;
        },
        pagesizechange(val) {
            let that = this;
            that.queryForm.pageSize = val;
            that.loadPreviewData();
        },
        currentpagechange(val) {
            this.queryForm.pageNo = val;
            this.loadPreviewData();
        },
        selectFolder: async function () {
            let that = this;
            document.getElementById('folderInput').addEventListener('change', (e) => {
                const files = e.target.files;
                for (let file of files) {
                    // 输出类似: "myFolder/sub/file.txt"
                    console.log(file.webkitRelativePath);
                }
            });
            const inputElement = document.getElementById('folderInput');

            inputElement.click();

            // try {
            //     // 调用 preload.js 中暴露的全局 API
            //     const path = await window.showDirectoryPicker();
            //     if (path) {
            //         that.toolForm.renameConfig.directory = path;
            //         console.log(path)
            //     }
            // } catch (error) {
            //     console.error(error);
            // }
        },
        previewConfig() {
            let that = this;
            that.$refs['batchRenameToolRef'].validate((valid) => {
                if (valid) {
                    that.loadPreviewData();
                }
            });
        },
        renameConfig() {
            let that = this;
            that.$confirm('此操作将批量修改文件名数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                that.$refs['batchRenameToolRef'].validate((valid) => {
                    if (valid) {
                        const loading = that.$loading({
                            lock: true,
                            text: 'Loading',
                            spinner: 'el-icon-loading',
                            background: 'rgba(0, 0, 0, 0.7)'
                        });
                        that.toolForm.renameConfig.preview = false;
                        httpUtil.post({
                            url: "sys/tool/batch-rename/execute",
                            data: JSON.stringify(that.toolForm.renameConfig)
                        }, function (r) {
                            loading.close();
                            if(r.code == 0) {
                                that.page = r.data
                                that.page.expand = true;
                                that.previewConfigVisible = true;
                            }
                            alertMsg(that, r)
                        });
                    }
                });
            });
        },
        loadPreviewData() {
            let that = this;
            const loading = that.$loading({
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });

            that.toolForm.renameConfig.preview = true;

            httpUtil.post({
                url: "sys/tool/batch-rename/preview",
                data: JSON.stringify(that.toolForm.renameConfig)
            }, function (r) {
                loading.close();
                if (r.code == 0) {
                    that.page = r.data
                    that.page.expand = true;
                    that.previewConfigVisible = true;
                }else {
                    alertMsg(that, r)
                }
            });
        }
    }
});
