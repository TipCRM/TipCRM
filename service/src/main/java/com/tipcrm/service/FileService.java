package com.tipcrm.service;
import com.tipcrm.constant.AttachmentType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadAvatar(MultipartFile file);

    Resource downloadFile(String fileName, AttachmentType type);
}
