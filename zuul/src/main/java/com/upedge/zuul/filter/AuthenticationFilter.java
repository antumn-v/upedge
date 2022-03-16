package com.upedge.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.utils.TokenUtil;
import com.upedge.zuul.utils.RequestUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RefreshScope
@Component
public class AuthenticationFilter extends ZuulFilter {

	static List<String> freePaths = new ArrayList<>();

	static {
		freePaths.add("/ums/user/signin");//注册
		freePaths.add("/ums/user/signup");//登陆
		freePaths.add("/ums/user/fb/signup");//登陆
		freePaths.add("/ums/user/info/select");//邮箱（登录名）查询
		freePaths.add("/ums/user/sendEmail/verifyCode");//邮箱验证码
		freePaths.add("/ums/user/emailCodeCheck");//邮箱验证码
		freePaths.add("/ums/user/recoverPassword");//找回密码
		freePaths.add("/ums/store/connect/shopify");//shopify授权
		freePaths.add("/ums/store/shopifyAuth");//shopify授权
		freePaths.add("/ums/store/connectShopify");//shopify授权
		freePaths.add("/cms/web");
		freePaths.add("/pms/alibabaApi/auth");

	}
	
	private static Logger log=LoggerFactory.getLogger(AuthenticationFilter.class);

	@Autowired
	RedisTemplate redisTemplate;

	@Override
	public boolean shouldFilter() {
		return true; // 表示是否需要执行该filter，true表示执行，false表示不执行
	}

	@Override
	public Object run() throws ZuulException {
        // filter需要执行的具体操作
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String path = request.getServletPath();
        // 登录不拦截
        if(null != path && freePaths.contains(path)) {
        	log.warn("FREE|"+RequestUtil.getIpAddress(request)+"|"+path+"| access path login free ");
        	return null;
        }

        // 不拦截
        if(null != path) {
        	if(path.contains("xxxcccds")
					|| path.contains("v2/api-docs")
					|| path.contains("webHook")
					|| path.contains("/auth")
					|| path.contains("/image")
					|| path.contains("/excel")
					|| path.contains("/cms/web")) {
        		log.warn("FREE|"+RequestUtil.getIpAddress(request)+"|"+path+"| generator ");
            	return null;
        	}
        }
        // token验证
        String token = request.getHeader("X-Token");
        if(token==null){
        	log.warn("NOTOKEN|"+RequestUtil.getIpAddress(request)+"|"+path+"| try access with out token ");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            try {
                ctx.getResponse().getWriter().write(new BaseResponse(-1,"there is no request token").toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
        	if(!redisTemplate.hasKey(TokenUtil.getTokenKey(token))) {
        		log.warn("ERRORTOKEN|"+RequestUtil.getIpAddress(request)+"|"+path+"| try access with token "+ token);
        		// 没有token，登录校验失败，拦截
                ctx.setSendZuulResponse(false);
                // 返回401状态码。也可以考虑重定向到登录页。
                ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
                try {
                    ctx.getResponse().getWriter().write(new BaseResponse(-1,"the token is not exist").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
        	}
        	else {
        		try {
        			String method = request.getMethod();
        			String json = "{}";
        			if(method.equals("POST")) {
        				json = RequestUtil.getRequestPostStr(request);
        			}        	        
//        			Enumeration<String> headernames = request.getHeaderNames();
        			String url = new String(request.getRequestURL());
        			log.warn("TOKEN|"+redisTemplate.opsForValue().get(TokenUtil.getTokenKey(token))+"|"+RequestUtil.getIpAddress(request)+"|"+method+"|"+path+"|"+url+"|"+JSONObject.toJSONString(request.getParameterMap())+"|"+json+"");
        		} catch (IOException e1) {
        			e1.printStackTrace();
        		}
        		redisTemplate.expire(TokenUtil.getTokenKey(token), 3, TimeUnit.HOURS);
        	}
        }
        return null;
	}

	/**
	 *  pre：可以在请求被路由之前调用
	 *  route：在路由请求时候被调用
	 *  post：在route和error过滤器之后被调用
	 *  error：处理请求时发生错误时被调用
	 */
	@Override
	public String filterType() {
		return "route";
	}

	/**
	 * 定义filter的顺序，数字越小表示顺序越高，越先执行
	 */
	@Override
	public int filterOrder() {
		return 0; 
	}
}
