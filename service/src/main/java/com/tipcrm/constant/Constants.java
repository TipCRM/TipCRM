package com.tipcrm.constant;
import java.util.Map;

import com.google.common.collect.Maps;

public class Constants {
    public static final Integer HASH_ITERATIONS = 2;

    public static class Email {
        public static final String ADD_USER_CONTENT = "管理员已为您分配帐号，您的工号是{0}, 您可以用工号或者邮箱({1})登录系统，初始密码是{2}。请尽快修改密码。";
    }

    public static class User {
        public static final String SYSTEM = "系统";
    }

    public static class Filter {
        public static final String FILTER_NAME = "tipcrm_filter";
    }

    public static class Permission {
        public static final String CUSTOMER_DEPARTMENT_VIEW = "customer:department:view";
        public static final String CUSTOMER_COMPANY_VIEW = "customer:company:view";
        public static final String CUSTOMER_ADD = "customer:add";
        public static final String CUSTOMER_MY_UPDATE = "customer:my:update";
        public static final String CUSTOMER_DEPARTMENT_UPDATE = "customer:department:update";
        public static final String CUSTOMER_COMPANY_UPDATE = "customer:company:update";
        public static final String CUSTOMER_COMMUNICATION_ADD = "customer:communication:add";
        public static final String CUSTOMER_STATUS_CHANGE = "customer:status:change";
        public static final String CUSTOMER_MY_DELETE = "customer:my:delete";
        public static final String CUSTOMER_DEPARTMENT_DELETE = "customer:department:delete";
        public static final String CUSTOMER_COMPANY_DELETE = "customer:company:delete";
        public static final String CUSTOMER_DEPARTMENT_TRANSFER_TO_USER = "customer:department:transfer_to_user";
        public static final String CUSTOMER_MY_TRANSFER_TO_USER = "customer:my:transfer_to_user";
        public static final String CUSTOMER_COMPANY_TRANSFER_TO_DEPARTMENT = "customer:company:transfer_to_department";
        public static final String CUSTOMER_MY_TRANSFER_TO_DEPARTMENT = "customer:my:transfer_to_department";
        public static final String CUSTOMER_APPROVAL = "customer:approval";
        public static final String USER_DETAIL_VIEW = "user:detail:view";
        public static final String USER_ADD = "user:add";
        public static final String USER_UPDATE = "user:update";
        public static final String USER_DELETE = "user:delete";
        public static final String ROLE_ADD = "role:add";
        public static final String ROLE_UPDATE = "role:update";
        public static final String ROLE_DELETE = "role:delete";
        public static final String DEPARTMENT_ADD = "department:add";
        public static final String DEPARTMENT_UPDATE = "department:update";
        public static final String DEPARTMENT_DELETE = "department:delete";
        public static final String CONTRACT_DEPARTMENT_VIEW = "contract:department:view";
        public static final String CONTRACT_COMPANY_VIEW = "contract:company:view";
        public static final String CONTRACT_UPDATE = "contract:update";
        public static final String CONTRACT_APPROVAL = "contract:approval";
        public static final String PRODUCTION_ADD = "production:add";
        public static final String PRODUCTION_UPDATE = "production:update";
        public static final String PRODUCTION_DELETE = "production:delete";
        public static final String SYSTEM_CONFIGURE = "system:configure";
        public static final String DEPARTMENT_GOAL_VIEW = "department_goal:view";
        public static final String DEPARTMENT_GOAL_ASSIGN = "department_goal:assign";
        public static final String USER_GOAL_VIEW = "user_goal:view";
        public static final String USER_GOAL_ASSIGN = "user_goal:assign";
        public static final String ROLE_ASSIGN = "role:assign";
        public static final String LEVEL_VIEW = "level:view";
        public static final String LEVEL_ADD = "level:add";
        public static final String LEVEL_UPDATE = "level:update";
        public static final String LEVEL_DELETE = "level:delete";
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

        public static class Notification {
            public static final String SENDER = "sender";
            public static final String TYPE = "type";
            public static final String SEND_TIME = "send_time";
            public static final String SUBJECT = "subject";
            public static final String CONTENT = "content";
            public static final String READ_STATUS = "read_status";
            public static final String RECEIVER = "receiver";
        }

        public static class User {
            public static final String USER_NAME = "user_name";
            public static final String STATUS = "status";
            public static final String DEPARTMENT_ID = "department";
            public static final String LEVEL_ID = "level";
        }
    }

    public static class SortFieldName {
        public static class Customer {
            public static final Map<String, String> fieldMap = Maps.newHashMap();

            static {
                fieldMap.put(QueryFieldName.Customer.CUSTOMER_NAME, "name");
                fieldMap.put(QueryFieldName.Customer.STATUS, "status.id");
                fieldMap.put(QueryFieldName.Customer.FOLLOW_USER, "followUser.userName");
                fieldMap.put(QueryFieldName.Customer.FOLLOW_DEPARTMENT, "followDepartment.name");
                fieldMap.put(QueryFieldName.Customer.LAST_COMMUNICATION_TIME, "lastCommunication.communicateTime");
                fieldMap.put(QueryFieldName.Customer.NEXT_COMMUNICATION_TIME, "lastCommunication.nextCommunicateTime");
            }
        }

        public static class Notification {
            public static final Map<String, String> fieldMap = Maps.newHashMap();

            static {
                fieldMap.put(QueryFieldName.Notification.SENDER, "entryUser.userName");
                fieldMap.put(QueryFieldName.Notification.TYPE, "type.name");
                fieldMap.put(QueryFieldName.Notification.SEND_TIME, "entryTime");
                fieldMap.put(QueryFieldName.Notification.SUBJECT, "subject");
                fieldMap.put(QueryFieldName.Notification.CONTENT, "content");
                fieldMap.put(QueryFieldName.Notification.READ_STATUS, "readStatus.name");
                fieldMap.put(QueryFieldName.Notification.RECEIVER, "toUser.userName");
            }
        }

        public static class User {
            public static final Map<String, String> fieldMap = Maps.newHashMap();

            static {
                fieldMap.put(QueryFieldName.User.USER_NAME, "userName");
                fieldMap.put(QueryFieldName.User.STATUS, "status.name");
                fieldMap.put(QueryFieldName.User.DEPARTMENT_ID, "department.name");
                fieldMap.put(QueryFieldName.User.LEVEL_ID, "level.name");
            }
        }
    }
}
