package com.zn.main;

/**
 * Created by zn on 2015/12/16.
 * 该类是对数据的抽象，实际存储的数据是以2进制形式存储，并且格式为
 * 网站:账户-密码-备注
 */
public class AccountEntity {
    private String id = System.currentTimeMillis() + "";
    private String website;
    private String account;
    private String password;
    private String remark;

    public String getId() {
        return id;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * 将网站:账户-密码-备注格式的字符串转换成自己
     * @param string
     * @return
     */
    public void convert2This(String string) {
        //网站:账户-密码-[备注（备注里不能出现-字符）]-id
        if(string != null && !string.trim().equals("")) {
            this.website = string.substring(0, string.indexOf(":"));
            String tmp = string.substring(string.indexOf(":") + 1);
            String[] datas = tmp.split("-");
            if (datas.length > 3) {
                this.account = datas[0];
                this.password = datas[1];
                this.remark = datas[2];
                this.id = datas[3];
            }
        } else {
            this.id = null;
        }
    }
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(website)
                .append(":")
                .append(account)
                .append("-")
                .append(password)
                .append("-")
                .append(remark)
                .append("-" + id);
        return buffer.toString();
    }
}
