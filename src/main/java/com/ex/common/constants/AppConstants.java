package com.ex.common.constants;

public class AppConstants {

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "2";
    public static final String SORT_DIR = "asc";
    public static final Long ADMIN_ID = 101L;
    public static final Long USER_ID = 102L;
    public static final String SORT_USERS_BY = "id";
    public static final String SORT_CURRENCY_RATE_BY = "saleRate";
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public static final String[] PUBLIC_URLS = { "/","/login","/error","/v3/api-docs/**", "/swagger-ui/**", "/api/register/**"};
    public static final String[] RESOURCES_URLS = { "/ajax/**","/css/**","/fonts/**","/img/**","/js/**","/scss/**","/vendor/**","/swagger-ui/**","/assets/**"};

    public static final String[] ACCOUNTANT_URLS = {"/accountant/**"};
    public static final String[] ADMIN_URLS = { "/admin/**"};
    public static final String[] ADMIN_ACCOUNTANT_URLS = {"/admin/currencies/get-currency-rate",
            "/admin/chartOfAccounts/chartOfAccountsTree", "/admin/chartOfAccounts/printChartOfAccountsReport",
            "/admin/currencies/currenciesList", "/admin/currencies/printCurrencyReport",
            "/admin/currencies/currencyRateList",
            "/admin/accintervals/listIntervals",
            "/accountant/setIntervalSelected/**",
            "/admin/reports/vouchersStatement", "/admin/reports/printVoucherStatementReport",
            "/admin/reports/accountStatement", "/admin/reports/printAccountStatementReport",
            "/api/employeeInfo","/admin/chartOfAccounts/get-account-currency",
    };
    public static final String[] ALL_USERS_URLS = {"/changePassword", "/dashboard"};
    public static final String SUCCESS_INSERT_MSG = "تمت عملية الإضافة بنجاح";
    public static final String SUCCESS_UPDATE_MSG = "تمت عملية التعديل بنجاح";
    public static final String SUCCESS_DELETE_MSG = "تمت عملية الحذف بنجاح";
    public static final String GLOBAL_EXCEPTION_MSG = "حدث خطأ!! راجع مسؤول النظام";
    public static final String FINANCIAL_ROLE_HIERARCHY = "ROLE_ADMIN > ROLE_ACCOUNTANT";
    public static final String ACCOUNTANT_ROLE = "ROLE_ACCOUNTANT";
    public static final String ADMIN_ROLE = "ROLE_ADMIN";
    public static final String NO_DATA_EXCEPTION_MSG = "لاتوجد بيانات..!";
    public static final String PASSWORD_NOT_MATCH = "خطأ!!! كلمة المرور الجديدة غير متطابقة";
    public static final String INCORRECT_OLD_PASSWORD = "خطأ!!! كلمة المرور السابقة غير صحيحة";
    public static final String INCORRECT_REUSE_OLD_PASSWORD = "خطأ!!! لايمكنك استخدام نفس كلمة المرور السابقة";
    public static final String PASSWORD_CHANGED_SUCCESS_MSG = "تم تعديل كلمة المرور بنجاح";

}
