package com.tipcrm.service.impl;
import com.tipcrm.constant.Attachments;
import com.tipcrm.dao.entity.Attachment;
import com.tipcrm.dao.repository.AttachmentRepository;
import com.tipcrm.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment findDefaultAvatar() {
        return attachmentRepository.findOne(Attachments.DEFAULT_AVATAR.getValue());
    }
}
