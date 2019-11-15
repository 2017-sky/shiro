package cn.java.shiro;

import cn.java.utils.ShiroUtil6;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;
import java.util.List;

public class ShiroDemo6 {
    public static void main(String[] args) {
        Subject user = ShiroUtil6.login("classpath:shiro_day6.ini", "superbird", "123");
        boolean flag6 = user.hasRole("system");
        System.out.println("flag6=" + flag6);
        List<String> roles = Arrays.asList("role1", "role2", "system");
        boolean[] flags = user.hasRoles(roles);
        System.out.println(Arrays.toString(flags));
        boolean flag1 = user.isPermitted("InRoom:xiaoFei");
        System.out.println("flag1=" + flag1);
        boolean[] flag2 = user.isPermitted("InRoom:add", "InRoom:insert");
        System.out.println("flag2=" + Arrays.toString(flag2));

    }
}
