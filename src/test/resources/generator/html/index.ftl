<!DOCTYPE html >
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head th:include="header"/>
<body>
<div id="${jsName}app" v-cloak>
    <div>
        <el-form :inline="true" ref="queryFormRef" :model="queryForm" class="demo-form-inline">
            <el-row type="flex" class="row-bg">
                <el-form-item label="关键字" prop="keyword">
                    <el-input v-model="queryForm.keyword" placeholder="关键字"></el-input>
                </el-form-item>

    <!--        <el-form-item label="关键字" prop="type">
                    <my-dict-select v-model="queryForm.type" type="billType"></my-dict-select>
                </el-form-item>

                <el-form-item label="用户" prop="userId">
                    <my-search-select v-model="queryForm.userId"
                                      search-url="/sys/user/list"
                                      search-param="orderBy-create_time desc,orderByEnabled-true"
                                      search-field-name="keyword"
                                      label-field-name="username"
                                      label-field-value="id"
                                      right-label-field-name="deptNames"
                    />
                </el-form-item>
      -->
            </el-row>
            <el-row type="flex" class="row-bg" justify="center">
                <el-form-item>
                    <el-button type="primary" @click="myQuery" icon="el-icon-search">查询</el-button>
                    <el-button @click="myQueryReset">重置</el-button>
                    <my-btn label="新增" type="primary" icon="el-icon-circle-plus" @click="myAdd" auth="${permission}:add"></my-btn>
                    <my-btn label="导出" type="info" icon="el-icon-download" @click="listExport"  auth="${permission}:list:export"></my-btn>
                </el-form-item>
            </el-row>
        </el-form>
        <my-table :columns="tableColumns" :page="page" :table-loading="tableLoading" @sortChange="sortchange" :default-sort="defaultSort"
                  v-on:pagesizechange="pagesizechange" v-on:currentpagechange="currentpagechange">
        </my-table>
        <el-dialog :close-on-click-modal="false" :title="addOrUpdateForm.title"
                   :visible.sync="addOrUpdateForm.${jsName}FormVisible">
            <el-form ref="addOrUpdateFormRef" :rules="rules" :model="addOrUpdateForm.${jsName}">
                <!--
				<el-form-item label="示例" prop="demo" :label-width="addOrUpdateForm.${jsName}FormLabelWidth" required>
					<el-select v-model="addOrUpdateForm.${jsName}.demo" placeholder="示例">
						<el-option label="未知" value=""></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="用户" prop="userId"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <my-search-select is-enabled="true" v-model="addOrUpdateForm.${jsName}.userId"
                                              search-url="/sys/user/list"
                                              search-param="orderBy-create_time desc,orderByEnabled-true"
                                              search-field-name="keyword"
                                              label-field-name="username"
                                              label-field-value="id"
                                              right-label-field-name="deptNames"
                            />
                        </el-form-item>
                        <el-form-item label="类型" prop="type"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <my-dict-select v-model="addOrUpdateForm.${jsName}.type" type="billType"></my-dict-select>
                        </el-form-item>
                        <el-form-item label="日期" prop="day"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <el-date-picker value-format="yyyy-MM-dd"
                                            v-model="addOrUpdateForm.${jsName}.day"
                                            type="date"
                                            placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="款项" prop="money"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <el-input-number controls-position="right" v-model="addOrUpdateForm.${jsName}.money"
                                             :precision="2" :step="0.1" :min="0"></el-input-number>
                        </el-form-item>
                        <el-form-item label="地址" prop="addr"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <el-input  type="textarea" :row="2"  v-model="addOrUpdateForm.${jsName}.addr"
                                       autocomplete="off" placeholder="地址"></el-input>
                        </el-form-item>
				-->
                <#if tableClass.allFields??>
                    <#list tableClass.allFields as field>
                        <el-form-item label="<#if field.remarks??>${field.remarks}</#if>" prop="${field.fieldName}"
                                      :label-width="addOrUpdateForm.${jsName}FormLabelWidth">
                            <el-input v-model="addOrUpdateForm.${jsName}.${field.fieldName}" autocomplete="off"
                                      placeholder="<#if field.remarks??>${field.remarks}</#if>"></el-input>
                        </el-form-item>
                    </#list>
                </#if>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addOrUpdateForm.${jsName}FormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
<script src="/statics/js/modules/${jsModule}/${jsName}.js"></script>
</body>
</html>