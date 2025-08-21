package com.ubtechinc.remote.model;

/**
 *插入表的数据类型定义
 * @author MacBook Air
 * @date 2025/8/20 6:08
 */
public class Datamodel {

    public static class User {

        private String userName;
        private String password;
        private String mobile;

        public User() {
        }


        public User(String userName, String password, String mobile) {
            this.userName = userName;
            this.password = password;
            this.mobile = mobile;
        }

        // Getter 和 Setter 方法

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Override
        public String toString() {
            return " | " + userName + " | " + password + " | " + mobile;
        }
    }
}
