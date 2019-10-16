package controller;

import entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author LeafDust
 * @create 2019-09-27 15:53
 */
@Controller
@RequestMapping("user")
public class HelloController implements Serializable {

    @RequestMapping("hello.do")
    public String showHello(ModelMap map) {
        System.out.println("HelloController.showHello()");
        map.put("message", "你好，SpringMVC");
        System.out.println("helloController.showHello");

        return "hello";
    }

    @RequestMapping({"reg.do", "register.do"})
    public String reg() {
        System.out.println("HelloController.reg()");
        return "reg";
    }

    @RequestMapping("reg_handle.do")
    public String regHandle(User user, ModelMap map) {
        System.out.println("HelloController.regHandle()");
        System.out.println(user);
        if (!"admin".equals(user.getUsername())) {
            return "redirect:login.do";
        }
        map.addAttribute("errorMessage", "用户名已存在！");
        return "error";
    }

    @RequestMapping("login.do")
    public String login() {
        System.out.println("HelloController.login()");
        return "login";
    }

    @RequestMapping("user_info.do")
    public String userInfo(ModelMap map, HttpSession session) {
        System.out.println("HelloController.user_info()");
        /*if (session.getAttribute("username") == null) {
            return "redirect:login.do";
        }*/
        map.addAttribute("username", session.getAttribute("username"));
        return "user_info";
    }

    @RequestMapping("login_handle.do")
    public String loginHandle(String username, String password, ModelMap map, HttpSession session) {
        System.out.println("HelloController.login_handle()");
        System.out.println("login_username: " + username);
        System.out.println("login_password: " + password);

        if ("Coldrain".equals(username)) {
            if ("123456".equals(password)) {
                session.setAttribute("username", username);
                //TODO 处理成功
                return "redirect:user_info.do";
            }
            //密码错误
            else {
                map.addAttribute("errorMessage", "密码错误！");
                return "error";
            }
        } else {
            //用户名错误
            map.addAttribute("errorMessage", "用户名错误！");
            return "error";
        }
    }
}
