//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package mkcloudadmin.permission.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoRes implements Serializable {
    private int userId;
    private String userUid;
    private String companyUid = "";
    private String companyName = "";
    private String officeUid = "";
    private String officeName = "";
    private String loginName = "";
    private String name = "";
    private String email = "";
    private String phone = "";
    private String mobile = "";
    private String userType = "";
    private String userTypeName = "";
    private List<RoleInfoRes> roleInfos;
    private String loginIp = "";
    private Date lastLoginDate;
    private String loginFlag;

    public UserInfoRes() {
    }

    public String getLoginFlag() {
        return this.loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserUid() {
        return this.userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLastLoginDate() {
        return this.lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public List<RoleInfoRes> getRoleInfos() {
        if (this.roleInfos == null) {
            this.roleInfos = new ArrayList();
        }

        return this.roleInfos;
    }

    public void setRoleInfos(List<RoleInfoRes> roleInfos) {
        this.roleInfos = roleInfos;
    }

    public String getUserTypeName() {
        return this.userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getCompanyUid() {
        return this.companyUid;
    }

    public void setCompanyUid(String companyUid) {
        this.companyUid = companyUid;
    }

    public String getOfficeUid() {
        return this.officeUid;
    }

    public void setOfficeUid(String officeUid) {
        this.officeUid = officeUid;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOfficeName() {
        return this.officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return this.userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
