package net.xiaodiclass.xdclass;

import io.jsonwebtoken.Claims;
import net.xiaodiclass.xdclass.domain.User;
import net.xiaodiclass.xdclass.utils.JwtUtils;
import org.junit.jupiter.api.Test;

public class CommonTests {
    @Test
    public void testGeneJWT(){
        User user=new User();
        user.setId(999);
        user.setHeadImg("www.xdclass.net");
        user.setName("xd");
        String token=JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
    }

    @Test
    public void testCheck(){
        String token="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZGNsYXNzIiwiaWQiOjk5OSwibmFtZSI6InhkIiwiaW1nIjoid3d3LnhkY2xhc3MubmV0IiwiaWF0IjoxNTgwMjAwODYxLCJleHAiOjE1ODAyMjYwNjF9.ZQJFe8-qFl2dG5QUsSXk7zO1dCVyy5C5F10T-PHsN1I";
        Claims claims=JwtUtils.checkJWT(token);
        if(claims!=null){
            int id=(Integer)claims.get("id");
            String name=(String)claims.get("name");
            String img=(String)claims.get("img");
            System.out.println(id);
            System.out.println(name);
            System.out.println(img);
        }else{
            System.out.println("非法token");
        }
    }
}
