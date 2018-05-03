package com.tipcrm.service;

import com.tipcrm.constant.AttachmentType;
import com.tipcrm.dao.entity.Attachment;

public interface AttachmentService {
    Attachment findDefaultAvatar();

    Attachment findByFileNameAndType(String fileName, AttachmentType type);
}
