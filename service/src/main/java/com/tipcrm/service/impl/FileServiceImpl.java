package com.tipcrm.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.tipcrm.config.FileConfiguration;
import com.tipcrm.constant.AttachmentLocation;
import com.tipcrm.constant.AttachmentType;
import com.tipcrm.constant.FileSizeUnit;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.dao.entity.Attachment;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.repository.AttachmentRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.FileService;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.service.WebContext;
import com.tipcrm.util.FileSizeCalculator;
import com.tipcrm.util.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    private FileConfiguration fileConfiguration;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private ListBoxService listBoxService;

    @Override
    public String uploadAvatar(MultipartFile file) {
        if (file == null) {
            throw new BizException("文件不能为空");
        }
        if (fileConfiguration == null) {
            throw new BizException("与文件相关的配置不存在");
        }
        if (file.getSize() > fileConfiguration.getAvatarMaxSize()) {
            throw new BizException("头像文件大小不能超过" + FileSizeCalculator.calcSize(fileConfiguration.getAvatarMaxSize(), FileSizeUnit.B, FileSizeUnit.MB));
        }
        String avatarPath = fileConfiguration.getBaseUrl() + "/avatar";
        String fileName = file.getOriginalFilename();
        ListBox avatar = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), AttachmentType.AVATAR.name());
        ListBox externalFile = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.EXTERNAL.name());
        if (StringUtils.isBlank(fileName)) {
            throw new BizException("文件名为空");
        }
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot < 0) {
            throw new BizException("文件不是图片");
        }
        String extension = fileName.substring(lastDot + 1).toLowerCase();
        if (StringUtils.isBlank(extension) || !ValidateUtils.isPicture(fileName)) {
            throw new BizException("文件不是图片");
        }
        String uuidFileName = UUID.randomUUID().toString();
        String realPath = avatarPath + getTimeDirectory() + uuidFileName + "." + extension;
        File dest = new File(realPath);
        // 检测是否存在目录
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            boolean mkdirRes = dest.getParentFile().mkdirs();
            if (!mkdirRes) {
                throw new BizException("创建目录失败，请联系运维。");
            }
        }
        try {
            file.transferTo(dest);
            Attachment attachment = new Attachment();
            attachment.setId(uuidFileName);
            attachment.setExt(extension);
            attachment.setPath(realPath);
            attachment.setType(avatar);
            attachment.setLocationType(externalFile);
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
        ListBox avatar = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), type.name());
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
        ListBox systemFile = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.SYSTEM.name());
        ListBox networkFile = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.NETWORK.name());
        Resource resource;
        if (systemFile.getId().equals(attachment.getLocationType().getId())) {
            resource = new ClassPathResource(attachment.getPath());
        } else if (networkFile.getId().equals(attachment.getLocationType().getId())) {
            try {
                URL url = new URL(attachment.getPath());
                resource = new UrlResource(url);
            } catch (MalformedURLException e) {
                throw new BizException("网络资源文件路径有误", e);
            }
        } else {
            resource = resourceLoader.getResource("file:" + Paths.get(attachment.getPath()));
        }
        if (resource == null) {
            throw new BizException(type.getValue() + "不存在");
        }
        return resource;
    }

}
