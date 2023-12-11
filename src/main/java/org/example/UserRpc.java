package org.example;


/**
 * @Author: zhujunhua
 * @Date: 2023/12/11 17:32
 */
public class UserRpc {
    public static class UserInfo {
        public Long uid;
        public String name;
        public Integer age;
    }

    public static UserRpc getInstance() {
        return new UserRpc();
    }

    public UserInfo getUserInfo(long uid) {
        if (uid == 1) {
            UserInfo userInfo = new UserInfo();
            userInfo.uid = 100L;
            userInfo.name = "zhujunhua";
            userInfo.age = 100;
            return userInfo;
        }
        return null;
    }
}
