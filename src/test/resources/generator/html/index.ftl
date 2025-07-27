<!DOCTYPE html >
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head th:include="header"/>
<body>
<div id="${jsName}app" v-cloak>
    <div>
        <el-form :inline="true" ref="queryFormRef" :model="queryForm" class="demo-form-inline">
            <el-form-item label="关键字" prop="keyword">
                <el-input v-model="queryForm.keyword" placeholder="关键字"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="myQuery" icon="el-icon-search">查询</el-button>
                <el-button @click="myQueryReset">重置</el-button>
                <my-btn auth="${jsModule}:${jsName}:add" type="primary" @click="myAdd" label="新增"/>
            </el-form-item>
        </el-form>
        <my-table :columns="tableColumns" :page="page" :table-loading="tableLoading"
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