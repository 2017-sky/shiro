package cn.java.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniFactorySupport;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroUtil6 {

    /**
     * @param congigPath
     * @param usernmae
     * @param password
     * @return
     */
    public static Subject login(String congigPath, String usernmae, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(congigPath);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(usernmae, password);
        try {
            user.login(token);
            System.out.println("登陆成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("登陆失败");
        }
        return user;
    }
}
