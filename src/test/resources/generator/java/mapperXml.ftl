<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${tableClass.shortClassName}${mapperSuffix}">
    <#assign dateTime = .now>
    <!--
        Author: ${author}
        Date: ${dateTime?string["yyyy-MM-dd HH:mm:ss"]}
        Description: ${description} mapper.xml
    -->
    <#if cachePackage??>
    <cache eviction="LRU" flushInterval="60000" readOnly="true" size="1560" type="${cachePackage}"></cache>
    </#if>

    <resultMap id="BaseResultMap" type="${poPackage}.${tableClass.shortClassName}Po" >
    <#if tableClass.allFields??>
    <#list tableClass.allFields as field>
        <id column="${field.columnName}" property="${field.fieldName}" jdbcType="${field.jdbcType}" />
    </#list>
    </#if>
    </resultMap>
    <sql id="Base_Column_List">
    <#if tableClass.allFields??>
        <#list tableClass.allFields as field>${field.columnName}<#if field_has_next>,</#if></#list>
    </#if>
    </sql>
    <sql id="Base_Alias_Column_List">
    <#if tableClass.allFields??>
        <#list tableClass.allFields as field>m.${field.columnName}<#if field_has_next>,</#if></#list>
    </#if>
    </sql>
    <delete id="deleteByPrimaryKey">
        DELETE FROM ${tableClass.tableName}
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse>
        </#list>
        </#if>
    </delete>
    <delete id="delete" parameterType="${poPackage}.${tableClass.shortClassName}Po">
        DELETE FROM ${tableClass.tableName}
        <where>
            <trim prefix="" prefixOverrides="and" suffix="">
            <#if tableClass.allFields??>
            <#list tableClass.allFields as field>
                <if test="${field.fieldName} != null">
                    AND ${field.columnName} = <#noparse>#</#noparse>{${field.fieldName}}
                </if>
            </#list>
            </#if>
            </trim>
        </where>
    </delete>
    <insert id="insert"  parameterType="${poPackage}.${tableClass.shortClassName}Po">
        <selectKey keyProperty="${genSelectKey}" order="BEFORE"  resultType="String">
            SELECT REPLACE(uuid(),'-','') FROM DUAL
        </selectKey>
        INSERT INTO ${tableClass.tableName}
        (<#if tableClass.allFields??><#list tableClass.allFields as field>${field.columnName}<#if field_has_next>,</#if></#list></#if>)
        VALUES
        (<#if tableClass.allFields??><#list tableClass.allFields as field><#noparse>#</#noparse>{${field.fieldName},jdbcType=${field.jdbcType}}<#if field_has_next>,</#if></#list></#if>)
    </insert>
    <insert id="insertSelective" parameterType="${poPackage}.${tableClass.shortClassName}Po">
        <selectKey keyProperty="${genSelectKey}" order="BEFORE" resultType="String">
            SELECT REPLACE(UUID(),'-','') FROM DUAL
        </selectKey>
        INSERT INTO ${tableClass.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#if tableClass.allFields??>
        <#list tableClass.allFields as field>
            <if test="${field.fieldName} != null">
                ${field.columnName},
            </if>
        </#list>
        </#if>
        </trim>
        <trim prefix="VALUES (" suffix=")" suffixOverrides=",">
        <#if tableClass.allFields??>
        <#list tableClass.allFields as field>
            <if test="${field.fieldName} != null">
                <#noparse>#</#noparse>{${field.fieldName},jdbcType=${field.jdbcType}},
            </if>
        </#list>
        </#if>
        </trim>
    </insert>

    <update id="updateByPrimaryKey"  parameterType="${poPackage}.${tableClass.shortClassName}Po">
        UPDATE ${tableClass.tableName}
        <set>
        <#if tableClass.allFields??>
        <#list tableClass.baseFields as field>
            ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName}},
        </#list>
        </#if>
        </set>
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
         WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse>
        </#list>
        </#if>
    </update>
    <update id="updateByPrimaryKeyAndVersion">
        UPDATE ${tableClass.tableName}
        <set>
        <#if tableClass.allFields??>
        <#list tableClass.baseFields as field>
            ${field.columnName} = <#noparse>#{</#noparse>record.${field.fieldName}},
        </#list>
        </#if>
        </set>
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
            WHERE ${field.columnName} = <#noparse>#{</#noparse>record.${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse> and version = <#noparse>#{</#noparse>oldVersion<#noparse>}</#noparse>
        </#list>
        </#if>
    </update>
    <update id="updateByPrimaryKeySelective"  parameterType="${po}.${tableClass.shortClassName}Po">
        UPDATE ${tableClass.tableName}
        <set>
        <#if tableClass.baseFields??><#list tableClass.baseFields as field>
            <if test="${field.fieldName} != null">
                ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName}},
            </if>
        </#list>
        </#if>
        </set>
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse>
        </#list>
        </#if>
    </update>
    <update id="updateByPrimaryKeySelectiveAndVersion">
        UPDATE ${tableClass.tableName}
        <set>
        <#if tableClass.baseFields??><#list tableClass.baseFields as field>
            <if test="record.${field.fieldName} != null">
                ${field.columnName} = <#noparse>#{</#noparse>record.${field.fieldName}},
            </if>
        </#list>
        </#if>
        </set>
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
            WHERE ${field.columnName} = <#noparse>#{</#noparse>record.${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse>  and version = <#noparse>#{</#noparse>oldVersion<#noparse>}</#noparse>
        </#list>
        </#if>
    </update>

    <select id="selectByPrimaryKey" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${tableClass.tableName}
        <#if tableClass.pkFields??>
        <#list tableClass.pkFields as field>
        WHERE ${field.columnName} = <#noparse>#{</#noparse>${field.fieldName},jdbcType=${field.jdbcType}<#noparse>}</#noparse>
        </#list>
        </#if>
    </select>
    <select id="selectListByKeys" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM ${tableClass.tableName}
        <where>
            <trim prefix="" prefixOverrides="and" suffix="">
                <#if tableClass.pkFields??>
                and <#list tableClass.pkFields as field> ${field.columnName}</#list> in
                <foreach collection="keys" separator="," open="(" close=")" item="item">
                    <#noparse>#</#noparse>{item}
                </foreach>
                </#if>
            </trim>
        </where>
    </select>

    <sql id="base_query_condition_sql">
        <where>
            <trim prefix="" prefixOverrides="and" suffix="">
            <#if tableClass.allFields??>
            <#list tableClass.allFields as field>
                <if test="${field.fieldName} != null">
                    AND m.${field.columnName} = <#noparse>#</#noparse>{${field.fieldName}}
                </if>
            </#list>
            </#if>
            </trim>
        </where>
    </sql>
    <select id="selectOne" resultMap="BaseResultMap"  parameterType="${poPackage}.${tableClass.shortClassName}Po">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${tableClass.tableName} m
        <include refid="base_query_condition_sql"/>
    </select>

    <select id="select" resultMap="BaseResultMap"  parameterType="${poPackage}.${tableClass.shortClassName}Po">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${tableClass.tableName} m
        <include refid="base_query_condition_sql"/>
    </select>

    <select id="selectAll" resultMap="BaseResultMap"  parameterType="${poPackage}.${tableClass.shortClassName}Po">
        SELECT
        <include refid="Base_Column_List" />
        FROM ${tableClass.tableName} m
        <include refid="base_query_condition_sql"/>
    </select>

    <select id="selectList" resultMap="BaseResultMap" parameterType="${voQm}.${tableClass.shortClassName}Vo">
        SELECT
        <include refid="Base_Alias_Column_List"/>
        FROM ${tableClass.tableName} m
        <include refid="cust_query_condition_sql"/>
    </select>

    <resultMap id="DTOResultMap" type="${dtoPo}.${tableClass.shortClassName}Dto" extends="BaseResultMap">
    </resultMap>

    <select id="selectDTOList" resultMap="DTOResultMap" parameterType="${voQm}.${tableClass.shortClassName}Vo">
        SELECT
        <include refid="Base_Alias_Column_List"/>
        FROM ${tableClass.tableName} m
        <include refid="cust_query_condition_sql"/>
    </select>
    <sql id="cust_query_condition_sql">
        <where>
            <trim prefix="" prefixOverrides="and" suffix="">
                <#if tableClass.allFields??>
                <#list tableClass.allFields as field>
                <if test="${field.fieldName} != null <#if field.stringColumn==true> and ${field.fieldName} != ''</#if>">
                    AND m.${field.columnName} = <#noparse>#</#noparse>{${field.fieldName}}
                </if>
                </#list>
                </#if>
                <!--  keyword   -->
                <if test="keyword != null and keyword != ''">
                </if>
            </trim>
        </where>
    </sql>
</mapper>