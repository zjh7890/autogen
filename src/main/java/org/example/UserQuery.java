package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhujunhua
 * @Date: 2023/12/11 17:35
 */
public class UserQuery {
    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        List<UserRpc.UserInfo> query = query(ids);
        System.out.println(query);
    }

    public static List<UserRpc.UserInfo> query(List<Long> ids) {
        UserRpc instance = UserRpc.getInstance();
        List<UserRpc.UserInfo> list = new ArrayList<>();
        for (Long id : ids) {
            UserRpc.UserInfo userInfo = instance.getUserInfo(id);
            list.add(userInfo);
        }
        return list;
    }
}
