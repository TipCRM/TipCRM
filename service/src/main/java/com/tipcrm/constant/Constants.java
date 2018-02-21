package com.tipcrm.constant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Constants {
    public static final Integer HASH_ITERATIONS = 2;

    public static class User {
        public static final String SYSTEM = "系统";
    }

    public static class Filter {
        public static final String FILTER_NAME = "tipcrm_filter";
    }

    public static class Permission {
        public static final String CUSTOMER_ADD_UPDATE = "customer:add_update";
        public static final String CUSTOMER_DELETE = "customer:delete";
        public static final String CUSTOMER_TRANSFER = "customer:transfer";
        public static final String CUSTOMER_APPROVAL = "cusotmer:approval";
        public static final String USER_ADD_UPDATE = "user:add_update";
        public static final String USER_DELETE = "user:delete";
        public static final String ROLE_ADD = "role:add";
        public static final String ROLE_ASSIGN = "role:assign";
        public static final String DEPARTMENT_ADD_UPDATE = "department:add_update";
        public static final String DEPARTMENT_DELETE = "department:delete";
    }

    public static class RequestResult {
        public static final String SUCCESS = "success";
        public static final String FAIL = "fail";
    }

    public static class Notification {
        public static class Subject {
            public static final String NEW_CUSTOMER_APPROVAL = "客户操作审批结果 - {0}";
        }

        public static class Content {
            public static final String NEW_CUSTOMER_APPROVAL = "您的客户操作申请已{0}! \n申请号：{1}\n审批人:{2}\n审批时间:{3}\n审批意见:{4}";
        }
    }
    public static class QueryFieldName {
        public static class Customer {
            public static final String CUSTOMER_NAME = "customer_name";
            public static final String STATUS = "status";
            public static final String LAST_COMMUNICATION_TIME = "last_communication_time";
            public static final String LAST_COMMUNICATION_CONTENT = "last_communication_content";
            public static final String NEXT_COMMUNICATION_TIME = "next_communication_time";
            public static final String FOLLOW_USER = "follow_user";
            public static final String FOLLOW_DEPARTMENT = "follow_department";
        }
    }
}
