package cn.java.shiro;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.sql.SQLOutput;

public class ShiroDemo2 {
    public static void main(String[] args) {
        //SecurutyManager-->Factory(接口) ，IniSecurityManagerFactory是接口的实现方法
        //先要加载shiro的配置文件，需要调用 IniSecurityManagerFactory
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //获得SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //等前用户;Subject-->SecurityUtils（将securityManager绑定到上下文）
        SecurityUtils.setSecurityManager(securityManager);
        //获得等前用户，进行交互
        Subject users = SecurityUtils.getSubject();
        //通过UsernamePasswordToken来模拟html/jsp传递过来的用户名与密码
        UsernamePasswordToken token = new UsernamePasswordToken("lisi", "123");
        //通过shiro来判断用户是否登陆成功
        try {
            users.login(token);
            if (users.isAuthenticated()) {
                System.out.println("登陆成功");
                if (users.hasRole("admin")) {
                    System.out.println("有admin角色");
                } else {
                    System.out.println("没有admin角色");
                }
                if (users.isPermitted("search")) {
                    System.out.println("有这个权限");
                } else {
                    System.out.println("没有这个权限");
                }
                if (users.isPermittedAll("ad", "update")) {
                    System.out.println("有\"ad\",\"update\"方法");
                } else {
                    System.out.println("没有\"ad\",\"update\"方法");
                }
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
        }

    }
}
