package com.geeke.fixasset.nature.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.google.common.collect.Lists;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * 卡片性质Entity
 * @author
 * @version
 */

public class TfamNature extends DataEntity<TfamNature> {

    private static final long serialVersionUID = 1641034040897356358L;

    private Integer code; // 编码

    private String name; // 名称

    private String companyId; // company_id

    // 构造方法
    public TfamNature() {
        super();
    }

    public TfamNature(String id) {
        super(id);
    }

    // 生成get和set方法

    @NotNull(message = "编码不能为空")
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Length(min = 1, max = 20, message = "名称长度必须介于 1 和 20 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 1, max = 100, message = "company_id长度必须介于 1 和 100 之间")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "tfam_nature";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1641034040897356358";
    }

    /**
     * 获取实体对应描述
     */
    @Override
    @JsonIgnore
    public String getBusTableComments() {
        return "卡片性质表";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
