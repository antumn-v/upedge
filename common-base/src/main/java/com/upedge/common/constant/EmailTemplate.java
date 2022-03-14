package com.upedge.common.constant;

public class EmailTemplate {

    public static String Verification_Code = "\n" +
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Document</title>\n" +
            "</head>\n" +
            "<style>\n" +
            "    * {margin: 0;padding: 0;}\n" +
            "    table,td,tr {vertical-align: top;border-collapse: collapse;}\n" +
            "    a[x-apple-data-detectors=true] {color: inherit !important;text-decoration: none !important;}\n" +
            "    .wrap{width: 650px;margin:  0 auto;display: flex;flex-direction: column;padding: 20px;box-sizing: border-box;}\n" +
            "    .tent{flex: 1;margin-top:40px;}\n" +
            "    .title{text-align: center;font-weight: 400;font-size: 24px;margin: 0;}\n" +
            "    .number{font-weight: 700;font-size: 48px;line-height: 56px;color:#D80E0E;text-align: center;margin: 0;margin-top:30px;}\n" +
            "    .numberFont{font-weight: 700;font-size: 14px;text-align: center;margin: 0;margin-top: 25px;}\n" +
            "    .btnbox{display: flex;justify-content: center;align-items: center;width: 100%;height: 200px;}\n" +
            "    .btn{border: none;outline: none;width: 200px;height: 40px;color: #fff;border-radius: 50px;cursor: pointer;\n" +
            "    background: linear-gradient(126.74deg, #D80E0E -3.92%, #FF8282 115.68%);}\n" +
            "    .wrapBottom{padding-left: 40px;margin-top: 50px;}\n" +
            "    .tips{font-weight: 400;font-size: 15px;color: #555;line-height: 25px;}\n" +
            "    .tiptent{font-weight: 400;font-size: 12px;text-indent: 10px;line-height: 25px;}\n" +
            "    .support{text-decoration: underline;}\n" +
            "</style>\n" +
            "<body>\n" +
            "    <div class=\"wrap\">\n" +
            "        <div class=\"wrapTop\">\n" +
            "            <img src=\"https://app.upedge.cn/image/20220314135348.png\" alt=\"\">\n" +
            "        </div>\n" +
            "        <div class=\"tent\">\n" +
            "            <p class=\"title\">Verification Code</p>\n" +
            "            <p class=\"number\">verifyCode</p>\n" +
            "            <p class=\"numberFont\">This verification code will be invalid in one hour.</p>\n" +
            "        </div>\n" +
            "        <div class=\"wrapBottom\">\n" +
            "            <p class=\"tips\">Tips:</p>\n" +
            "            <p class=\"tiptent\">If you need any help, please contact your customer manager.</p>\n" +
            "            <p class=\"tiptent\">If you can't reach your customer manager, you could send email to <span class=\"support\">support@upedge.cn</span> </p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";



}
