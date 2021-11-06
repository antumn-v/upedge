package com.upedge.common.model.account.payoneer;

/**
 * @author 海桐
 */
public class PayoneerConstant {

    public static String TOKEN_URL = "https://login.sandbox.payoneer.com/api/v2/oauth2/token";

    public static String API = "https://api.sandbox.payoneer.com/v4/";

    public static String REFRESH_TOKEN_URL = "api/v2/oauth2/token";

    public static String REVOKE_TOKEN = "api/v2/oauth2/revoke";

    public static String PAYMENT_CREATE = "accounts/{accountId}/balances/{balanceId}/payments/debit";

    public static String PAYMENT_STATE = "accounts/{accountId}/payments/{clientReferenceId}?type= client_reference_id";

    public static String PAYMENT_COMMIT = "accounts/{accountId}/payments/{commitId}";

}
