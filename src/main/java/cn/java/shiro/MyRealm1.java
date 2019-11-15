package cn.java.shiro;

import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {

    public String getName() {
        //返回数据源的名字
        return "myrealm1";
    }

    public boolean supports(AuthenticationToken authenticationToken) {
        //设置让数据源只支持密码（password），支持名字（username）,限制数据源只支持UsernamePasswordToken，“authenticationToken instanceof UsernamePasswordToken”是否支持UsernamePasswordToken
        return authenticationToken instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        //authenticationToken是存成数组，所以转化成String时需要强转
        String password = new String((char[]) authenticationToken.getCredentials());
        if (!"test".equals(username)) {
            throw new UnknownAccountException();
        }
        if (!"123456".equals(password)) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
