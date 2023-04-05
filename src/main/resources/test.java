package com.example.ruleapi.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (EagleDrlRule)实体类
 *
 * @author makejava
 * @since 2023-03-31 10:42:25
 */
public class EagleDrlRule implements Serializable {
    private static final long serialVersionUID = -33907488948311113L;

    private Integer id;

    private String ruleName;

    private String ruleCode;

    private Integer ruleStatus;

    private String ruleType;

    private String ruleVersion;

    private String cntSqls;

    private String seqSqls;

    private String ruleCreator;

    private String ruleAuditor;

    private Date createTime;

    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleVersion() {
        return ruleVersion;
    }

    public void setRuleVersion(String ruleVersion) {
        this.ruleVersion = ruleVersion;
    }

    public String getCntSqls() {
        return cntSqls;
    }

    public void setCntSqls(String cntSqls) {
        this.cntSqls = cntSqls;
    }

    public String getSeqSqls() {
        return seqSqls;
    }

    public void setSeqSqls(String seqSqls) {
        this.seqSqls = seqSqls;
    }

    public String getRuleCreator() {
        return ruleCreator;
    }

    public void setRuleCreator(String ruleCreator) {
        this.ruleCreator = ruleCreator;
    }

    public String getRuleAuditor() {
        return ruleAuditor;
    }

    public void setRuleAuditor(String ruleAuditor) {
        this.ruleAuditor = ruleAuditor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}

