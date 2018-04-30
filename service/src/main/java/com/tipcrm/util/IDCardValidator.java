package com.tipcrm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

public class IDCardValidator {

    private static final List<Integer> PARAM = Lists.newArrayList(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
    private static final List<Integer> LAST_NUM = Lists.newArrayList(1, 0, -1, 9, 8, 7, 6, 5, 4, 3, 2);

    /**
     * <h4>身份证正确性校验</h4> 校验规则：<br>
     * 1. 将身份证号前17分别依次乘以下系数：<br>
     * &nbsp;&nbsp;7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2<br>
     * 2. 将17个结果相加<br>
     * 3. 将相加结果除以11得余数<br>
     * 4. 余数0 - 10分别应该对应以下最后一位号码<br>
     * &nbsp;&nbsp;1,0,X,9,8,7,6,5,4,3,2
     *
     * @return
     * @PARAM id
     */
    public static boolean validate(String idCard) {
        if (StringUtils.isBlank(idCard)) {
            return false;
        }
        if (idCard.length() != 18) {
            return false;
        }
        int sum = 0;
        try {
            for (int i = 0; i < 17; i++) {
                if (idCard.charAt(i) < '0' || idCard.charAt(i) > '9') {
                    return false;
                }
                int now = Integer.parseInt(idCard.substring(i, i + 1));
                sum += now * PARAM.get(i);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            sdf.parse(idCard.substring(6, 14));
            int mod = sum % 11;
            String last = idCard.substring(17, 18);
            int lastNum;
            if (last.equalsIgnoreCase("x")) {
                lastNum = -1;
            } else {
                lastNum = Integer.parseInt(last);
            }
            return LAST_NUM.get(mod).equals(lastNum);
        } catch (ParseException e) {
            return false;
        }
    }
}
