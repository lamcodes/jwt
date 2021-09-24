package com.zkp.jwt.dto;

import lombok.Data;

import java.util.Date;
@Data
public class DepartmentDO {
    /**
     * 部门ID
     */
    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 最近修改人ID
     */
    private Long modifyUserId;

    /**
     * 创建人ID
     */
    private Long createUserId;
    /**
     * 部门code
     */
    private String code;
    /**
     * 上级部门id
     */
    private Long parentId;
    /**
     * 当前部门级别
     */
    private Long level;

    private Date gmtModified;
    private Date gmtCreate;


    private Date createTime;
    private Date modifyTime;



    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        setModifyTime(gmtModified);
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        setCreateTime(gmtCreate);
    }
}
