package net.xiaodiclass.xdclass.interceptor;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import net.xiaodiclass.xdclass.domain.JsonData;
import net.xiaodiclass.xdclass.utils.JwtUtils;
import org.apache.http.HttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {
    private static final Gson gson=new Gson();

    /**
     * 进入controller之前拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token==null){
            token=request.getParameter("token");
        }
        if(token!=null){
            Claims claims= JwtUtils.checkJWT(token);
            if(claims!=null){
                Integer userId=(Integer) claims.get("id");
                String name=(String)claims.get("name");
                request.setAttribute("user_id",userId);
                request.setAttribute("name",name);
                return true;
            }
        }
        sendJsonMessage(response, JsonData.buildError("请登录"));
        return false;
    }

    /**
     * 响应数据给前端
     * @param response
     * @param obj
     * @throws IOException
     */
    private static void sendJsonMessage(HttpServletResponse response,Object obj) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw=response.getWriter();
        pw.print(gson.toJson(obj));
        pw.close();
        response.flushBuffer();
    }

}
