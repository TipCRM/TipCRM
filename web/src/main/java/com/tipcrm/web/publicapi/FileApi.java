package com.tipcrm.web.publicapi;

import com.tipcrm.constant.AttachmentType;
import com.tipcrm.service.FileService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiresAuthentication
@Api
public class FileApi {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "avatar", method = RequestMethod.POST)
    public JsonEntity<String> uploadAvatar(MultipartFile file) throws IOException {
        return ResponseHelper.createInstance(fileService.uploadAvatar(file));
    }

    @RequestMapping(value = "avatar/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<?> getAvatar(@PathVariable String fileName) {
        return ResponseEntity.ok(fileService.downloadFile(fileName, AttachmentType.AVATAR));
    }
}
