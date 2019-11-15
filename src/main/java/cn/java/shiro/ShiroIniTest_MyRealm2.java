package cn.java.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ShiroIniTest_MyRealm2 {
    public static void main(String[] args) {

        //当启动ini配置文件的时候，默认创建一个securityManager对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置身份验证的策略，最少有一个的策略
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        //把策略只定给 securityManage
        securityManager.setAuthenticator(authenticator);
        //设置授权，authorizer用于进行授权的，WildcardPermissionResolver用于解析对应的字符串到 ModularRealmAuthorizer权限的实力的
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        securityManager.setAuthenticator(authenticator);

        //以上的代码相等于
        //下面是封装的（上面的是不封装的）
        // Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //        SecurityManager securityManager = factory.getInstance();

        //dataSource=org.springframework.jdbc.datasource.DriverManagerDataSource
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        //dataSource.driverClassName=com.mysql.cj.jdbc.Driver
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //dataSource.url=jdbc:mysql://localhost:3306/shiro?serverTimezone=GMT%2B8
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?serverTimezone=GMT%2B8");
        //dataSource.username=root
        dataSource.setUsername("root");
        //dataSource.password=123456
        dataSource.setPassword("123456");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MyRealm2 myRealm2 = new MyRealm2();
        myRealm2.setJdbcTemplate(jdbcTemplate);

        //设置数据源,需要自定义一个Realm（MyRealm2）
        securityManager.setRealm(myRealm2);
        //绑定上下文
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        //通过UsernamePasswordToken来模拟html/jsp传递过来的用户名与密码
        UsernamePasswordToken token = new UsernamePasswordToken("admin@shiro.com", "admin");
        //通过shiro来判断用户是否登陆成功
        try {
            subject.login(token);
            System.out.println(subject.hasRole("test"));
            System.out.println("登陆成功");
        } catch (AuthenticationException e) {
            System.out.println("用户名户密码错误，登陆失败");
        }

    }
}
