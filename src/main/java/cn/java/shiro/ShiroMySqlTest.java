package cn.java.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroMySqlTest {
    public static void main(String[] args) {
        //获取配置文件
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-mysql.ini");
        //获得SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //等前用户;Subject-->SecurityUtils（将securityManager绑定到上下文）
        SecurityUtils.setSecurityManager(securityManager);
        //获得等前用户，进行交互
        Subject users = SecurityUtils.getSubject();
        //通过UsernamePasswordToken来模拟html/jsp传递过来的用户名与密码
        UsernamePasswordToken token = new UsernamePasswordToken("admin@shiro.com", "admin ");
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
                if (users.isPermitted("add")) {
                    System.out.println("有这个权限");
                } else {
                    System.out.println("没有这个权限");
                }
                if (users.isPermitted("get")) {
                    System.out.println("有“get”方法");
                } else {
                    System.out.println("没有“get”方法");
                }
                if (users.isPermitted("1")) {
                    System.out.println("有\"1\"方法");
                } else {
                    System.out.println("没有\"1\"方法");
                }
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("登录失败");
        }

    }
}

