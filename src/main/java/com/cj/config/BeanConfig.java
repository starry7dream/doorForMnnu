package com.cj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;


@Configuration
public class BeanConfig {

    @Bean
    public LoginInfoMap loginInfoMap() throws IOException {
        System.out.println("loginInfoMap 执行了");
        return new LoginInfoMap();
    }


    public class LoginInfoMap{
        //设备信息
        private HashMap<Integer, ArrayList<String>> confirmMsg;
        //邀请码
        private ArrayList<String>inviteCodeList;
        //记录Ip
        private HashMap<Integer,String> ipMap;
        public LoginInfoMap() throws IOException {
            confirmMsg=new HashMap<>();
            ipMap=new HashMap<>();
            inviteCodeList=new ArrayList<>();
            Properties pps = new Properties();
            pps.load(this.getClass().getResourceAsStream("/inviteCode.properties"));
            Enumeration enum1 = pps.propertyNames();//得到配置文件的名字
            while(enum1.hasMoreElements()) {
                String strKey = (String) enum1.nextElement();
                inviteCodeList.add(strKey);
            }
        }

        public HashMap<Integer, ArrayList<String>> getConfirmMsg() {
            return confirmMsg;
        }
        public void setConfirmMsg(HashMap<Integer, ArrayList<String>> confirmMsg) {
            this.confirmMsg = confirmMsg;
        }

        public ArrayList<String> getInviteCodeList() {
            return inviteCodeList;
        }
        public void setInviteCodeList(ArrayList<String> inviteCodeList) {
            this.inviteCodeList = inviteCodeList;
        }

        public HashMap<Integer, String> getIpMap() {
            return ipMap;
        }

        @Scheduled(cron = "0 0 12 * * ?" )
        public void clearMap(){
            confirmMsg.clear();
            ipMap.clear();
        }

    }

}
