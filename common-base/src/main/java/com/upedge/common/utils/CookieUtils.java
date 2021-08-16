package com.upedge.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

public class CookieUtils {

    private static String key = "f167105ef452466f80c97c3b355658a4";

    public static boolean addCookie(HttpServletRequest request, HttpServletResponse response, String customerId){
        try {
            String referrerToken = EncryptUtil.XORencode(customerId,key);
            Cookie[] cookies =  request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(referrerToken)){
                       return false;
                    }
                }
            }
            Cookie cookie = new Cookie(referrerToken, URLEncoder.encode(customerId));
            cookie.setMaxAge(5*365*24*60*60);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean haveSentNotice(HttpServletRequest request, HttpServletResponse response, String userId, String noticeId){
        String referrerToken = EncryptUtil.XORencode(noticeId,userId);
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(referrerToken)){
                    return false;
                }
            }
            Cookie cookie = new Cookie(referrerToken,referrerToken);
            cookie.setMaxAge(30*24*60*60);
            response.addCookie(cookie);
        }
        return true;
    }

    public static boolean firstLoginVerify(HttpServletRequest request, HttpServletResponse response, Long userId){
        String referrerToken = EncryptUtil.XORencode("SourcinBox", String.valueOf(userId));
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(referrerToken)){
                    return false;
                }
            }
            Cookie cookie = new Cookie(referrerToken,referrerToken);
            cookie.setMaxAge(5*365*24*60*60);

            response.addCookie(cookie);
        }
        return true;
    }
}
