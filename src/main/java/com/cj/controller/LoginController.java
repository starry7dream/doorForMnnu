package com.cj.controller;

import com.cj.config.BeanConfig;
import com.cj.dao.IUserDao;
import com.cj.domain.User;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;

import java.util.EnumSet;
import java.util.HashMap;


@Controller
public class LoginController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private BeanConfig.LoginInfoMap loginInfoMap;

//    private  HashMap<Integer, ArrayList<String>> confirmMsg=loginInfoMap.getConfirmMsg();

    @RequestMapping("/")
    public String logins() {
        return "index";
    }

    @RequestMapping("/mnnu")
    public String mnn() {
        return "mnnu";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
                        RedirectAttributes attributes, HttpServletRequest request
    ) {
        User user = userDao.checkLogin(username, password);
        HashMap<Integer, String> ipMap = loginInfoMap.getIpMap();
        /****
         * 1.判断IP 相同 则登录
         * 2.ip不同 判断浏览器设备,防备设备 相同 登录
         */
        Boolean isLogin = true;
        if (user != null) {

            HashMap<Integer, ArrayList<String>> confirmMsg = loginInfoMap.getConfirmMsg();

            session.setAttribute("user", user);
            String ua = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(ua);
            //获取浏览器信息
            Browser browser = userAgent.getBrowser();
            //获取系统信息
            OperatingSystem os = userAgent.getOperatingSystem();

            /**
             * 放入登入信息
             *
             * */
            ArrayList<String> loginInfo = new ArrayList<>();
            loginInfo.add(browser.getName());//浏览器名字 WEB_BROWSER  MOBILE_BROWSER
            loginInfo.add(browser.getBrowserType().toString());//浏览器类型 Chrome 8 Mobile Safari
            loginInfo.add(os.getName());//COMPUTER  MOBILE
            loginInfo.add(os.getDeviceType().toString());//Windows Mac OS X (iPhone)
            loginInfo.add(os.getManufacturer().toString());

            //初次登录
            if (!confirmMsg.containsKey(user.getId())) {
                isLogin = true;
                confirmMsg.put(user.getId(), loginInfo);
                ipMap.put(user.getId(), request.getRemoteAddr());
                System.out.println("初次登录" + request.getRemoteAddr());
            } else {
                /**成功登录下同一个IP
                 *
                 */
                System.out.println("不是第一次登录 此时IP:" + request.getRemoteAddr());
                System.out.println("第一次登录IP" + ipMap.get(user.getId()));
                if (request.getRemoteAddr().equals(ipMap.get(user.getId()))) {
                    isLogin = true;
                } else {
                    ArrayList<String> userLoginInfo = confirmMsg.get(user.getId());
                    for (int i = 0; i < userLoginInfo.size(); i++) {
                        if (!loginInfo.get(i).equals(userLoginInfo.get(i))) {
                            isLogin = false;
//                        System.out.println("登录错误 两个登录设备不同");
//                        System.out.println("登录设备"+loginInfo.get(i)+"第一次登录设备"+userLoginInfo.get(i));
                            attributes.addFlashAttribute("result", "请勿切换设备(ip/浏览器/手机),每天12点刷新");
                            break;
                        }
                    }
                }
            }
        } else {
            attributes.addFlashAttribute("result", "账号/密码错误");
            isLogin = false;
        }
        if (isLogin) {
            return "redirect:mnnu";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/register")
    public String register(User user, String inviteCode,
                           RedirectAttributes attributes) {

        ArrayList<String> inviteCodeList = loginInfoMap.getInviteCodeList();

        if (inviteCodeList.contains(inviteCode)) {
//            System.out.println(user);
            int relustcode = 0;
            try {
                User isExist = userDao.checkLogin(user.getUsername(), user.getPassword());
                if (isExist == null) {
                    relustcode = userDao.addUser(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (relustcode == 1) {
                inviteCodeList.remove(inviteCode);

                attributes.addFlashAttribute("result", "注册成功,此邀请码作废,剩余邀请码:" + inviteCodeList.size());
            } else {
                attributes.addFlashAttribute("result", "注册失败,该账户已存在");
            }
        } else {
//            System.out.println("failed..");
            attributes.addFlashAttribute("result", "邀请码不合法");
        }
        return "redirect:/";
    }

}
