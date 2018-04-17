package com.tipcrm.service.impl;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.tipcrm.constant.AttachmentType;
import com.tipcrm.constant.FileConfig;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.dao.entity.Attachment;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.repository.AttachmentRepository;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.FileService;
import com.tipcrm.service.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private static final List<String> picExtension = Lists.newArrayList("JPG", "JPEG", "GIF", "PNG");

    private static final String avatarPath = FileConfig.baseUrl + "/avatar";

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Override
    public String uploadAvatar(MultipartFile file) {
        logger.info("avatarPath ======> " + avatarPath);
        String fileName = file.getOriginalFilename();
        ListBox avatar = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), AttachmentType.AVATAR.name());
        if (StringUtils.isBlank(fileName)) {
            throw new BizException("文件名为空");
        }
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot < 0) {
            throw new BizException("文件不是图片");
        }
        String extension = fileName.substring(lastDot + 1).toLowerCase();
        if (StringUtils.isBlank(extension) || !picExtension.contains(extension.toUpperCase())) {
            throw new BizException("文件不是图片");
        }
        String uuidFileName = UUID.randomUUID().toString();
        String realPath = avatarPath + getTimeDirectory() + uuidFileName + "." + extension;
        File dest = new File(realPath);
        // 检测是否存在目录
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            Attachment attachment = new Attachment();
            attachment.setId(uuidFileName);
            attachment.setExt(extension);
            attachment.setPath(realPath);
            attachment.setType(avatar);
            attachment.setEntryUser(webContext.getCurrentUser());
            attachment.setEntryTime(new Date());
            attachmentRepository.save(attachment);
            return uuidFileName + "." + extension;
        } catch (Exception e) {
            throw new BizException("文件上传失败", e);
        }
    }

    private String getTimeDirectory() {
        StringBuilder directory = new StringBuilder("/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        directory.append(sdf.format(date))
                 .append("/");
        sdf = new SimpleDateFormat("MM");
        directory.append(sdf.format(date))
                 .append("/");
        return directory.toString();
    }


    @Override
    public Resource downloadFile(String fileName, AttachmentType type) {
        fileName = fileName.toLowerCase();
        ListBox avatar = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), type.name());
        Attachment attachment;
        int dotPosition = fileName.indexOf(".");
        if (dotPosition < 0) {
            attachment = attachmentRepository.findByIdAndTypeId(fileName, avatar.getId());
        } else {
            String name = fileName.substring(0, dotPosition);
            String extension = fileName.substring(dotPosition + 1);
            attachment = attachmentRepository.findByIdAndExtAndTypeId(name, extension, avatar.getId());
        }
        if (attachment == null) {
            throw new BizException(type.getValue() + "不存在");
        }
        Resource resource = resourceLoader.getResource("file:" + Paths.get(attachment.getPath()));
        if (resource == null) {
            throw new BizException(type.getValue() + "不存在");
        }
        return resource;
    }

}
