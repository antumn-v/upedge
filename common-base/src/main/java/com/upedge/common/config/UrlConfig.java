package com.upedge.common.config;

public class UrlConfig {

    public final static String OLD_APP_HOST = "http://localhost:85";
    public final static String OLD_ADMIN_HOST = "http://localhost:8081/";
    public final static String NEW_UPEDGE_HOST = "http://localhost:8611/";

    /**
     * 测试
     */
    public final  static String ADMIN_LOGIN_URL = OLD_ADMIN_HOST+"/upedgeadmin/f/adminlogin";
    public final  static String ADMIN_LOGIN_CODE = "djkllasjmgqewk.dwrweqe-wefsag!";

    /**
     * 获取所有客户经理
     */
    public final  static String GET_ALL_MANAGER = OLD_ADMIN_HOST+"/upedgeadmin/f/cmsmigration/allmanager";
    public final  static String GET_ALL_MANAGER_CODE = "sdgdfhsdfwerfe.dwrweqe-wefsag!";


    /**
     * 向新架构发送通知  让新架构生成当前登录用户的session 和  manager
     */
    public final  static String SEND_NEW_LOGIN = NEW_UPEDGE_HOST+"/ums/user/creatLoginInfo";
    public final  static String SEND_NEW_LOGIN_CODE = "sdfsdghwreerasf.dwrweqe-wefsag!";

    /**
     * 老架构退出登录  发送请求到新架构  新架构删除该用户的登录信息缓存
     */
    public final  static String SEND_NEW_LOGINOUT = NEW_UPEDGE_HOST+"/ums/user/delLoginInfo";
    public final  static String SEND_NEW_LOGINOUT_CODE = "sjfdsufzxhcasdsaf.dwrweqe-wefsag!";

    /**
     * 老架构跳转新架构页面  将该登录客户经理的信息发送到新架构    避免因为新架构退出  造成403
     */
    public final  static String SEND_NEW_ADMIN_LOGINOUT = NEW_UPEDGE_HOST+"/ums/user/creatAdminLoginInfo";
    public final  static String SEND_NEW_ADMIN_LOGINOUT_CODE = "sjfdGSDGDFHSADhcasdsaf.dwrweqe-wefsag!";

    /**
     * app  获取新架构cms数据接口 -- 1  notice/list
     */
    public final  static String SEND_NEW_APP_NOTICE_LIST = NEW_UPEDGE_HOST+"/cms/appNotice/admin/list";
    public final  static String SEND_NEW_APP_NOTICE_LIST_CODE = "zsdgfsdfghjghfhoAcsdof.dwrweqe-wefsag!";


    /**
     * app  获取新架构cms数据接口 -- 2  /notice/1270944553997836288/detail
     */
    public final  static String SEND_NEW_APP_NOTICE_DETAIL = NEW_UPEDGE_HOST+"/cms/appNotice/admin/info/";
    public final  static String SEND_NEW_APP_NOTICE_DETAIL_CODE = "sjfsdgsdbcxvsdfdsafdsg.dwrweqe-wefsag!";

    /**
     * app  获取新架构cms数据接口 -- 3  appNotice/user/recent
     */
    public final  static String SEND_NEW_APP_APPNOTICE_NEW = NEW_UPEDGE_HOST+"/cms/appNotice/user/recent";
    public final  static String SEND_NEW_APP_APPNOTICE_NEW_CODE = "sjfdsgsafdwetsaf.dwrweqe-wefsag!";

}
