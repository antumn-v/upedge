package com.upedge.common.config;

public interface OssClientConfig {

    String ACCESSKEYID = "LTAI5t9qLH665h8twgzHP4QH";
    String ACCESSKEYSECRET = "YlLOWVUe5UhdrnhM1ywBxQE4s59oey";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。

    // Endpoint以杭州为例，其它Region请按实际情况填写。

    //  外网访问（IPv4）
    String ENDPOINT = "oss-us-west-1.aliyuncs.com";
    String BUCKETNAME = "sourcinbox-app";


    //  ECS 的经典网络访问（内网）
    //String  ENDPOINT = "oss-us-west-1-internal.aliyuncs.com";
    //String  BUCKETNAME = "sourcinbox-app";

    //  ECS 的 VPC 网络访问（内网）
    //String  ENDPOINT = "oss-us-west-1-internal.aliyuncs.com";
    //String  BUCKETNAME = "sourcinbox-app";



}
