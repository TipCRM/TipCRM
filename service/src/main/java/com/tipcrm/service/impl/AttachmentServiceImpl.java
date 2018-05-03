package com.tipcrm.service.impl;

import com.tipcrm.constant.AttachmentType;
import com.tipcrm.constant.Attachments;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.dao.entity.Attachment;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.repository.AttachmentRepository;
import com.tipcrm.service.AttachmentService;
import com.tipcrm.service.ListBoxService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private ListBoxService listBoxService;

    @Override
    public Attachment findDefaultAvatar() {
        return attachmentRepository.findOne(Attachments.DEFAULT_AVATAR.getValue());
    }

    @Override
    public Attachment findByFileNameAndType(String fileName, AttachmentType type) {
        if (StringUtils.isBlank(fileName) || type == null) {
            return null;
        }
        ListBox listBox = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), type.name());
        Integer dotPos = fileName.lastIndexOf(".");
        if (dotPos < 0) {
            return attachmentRepository.findByIdAndTypeId(fileName, listBox.getId());
        } else {
            return attachmentRepository.findByIdAndExtAndTypeId(fileName.substring(0, dotPos), fileName.substring(dotPos + 1), listBox.getId());
        }
    }
}
