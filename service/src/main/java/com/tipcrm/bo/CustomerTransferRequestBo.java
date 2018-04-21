package com.tipcrm.bo;

import com.tipcrm.constant.CustomerTransferTarget;

public class CustomerTransferRequestBo {
    private CustomerTransferTarget target;
    private Integer targetId;
    private Integer customerId;

    public CustomerTransferTarget getTarget() {
        return target;
    }

    public void setTarget(CustomerTransferTarget target) {
        this.target = target;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
