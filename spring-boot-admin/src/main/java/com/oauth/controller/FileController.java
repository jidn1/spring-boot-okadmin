package com.oauth.controller;

import com.common.model.JsonResult;
import com.util.properties.PropertiesUtils;
import com.util.file.FileUtil;
import com.util.oss.OssUtil;
import com.util.uuid.UUIDUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/24 15:39
 * @Description:
 */
@Api(value = "文件上传处理")
@RestController
@RequestMapping("/file")
@MultipartConfig
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        JsonResult json = new JsonResult();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            if (multipartRequest != null) {
                //获得文件
                String names = file.getOriginalFilename().toLowerCase();
                if (names != null && (names.endsWith("jpg") || names.endsWith("jpge") || names.endsWith("png") || names.endsWith("gif") || names.endsWith("bmp"))) {
                } else {
                    JsonResult jsonResult = new JsonResult();
                    jsonResult.setMsg("图片格式不正确");
                    jsonResult.setSuccess(false);
                    return jsonResult;
                }
                InputStream inputStream = file.getInputStream();
                if (!file.isEmpty()) {
                    String fileName = UUIDUtil.getUUID() + "." + file.getOriginalFilename().split("\\.")[1];
                    String fileSavePath[] = FileUtil.createFileSavePath(fileName);
                    try {
                        findUploadUtil(inputStream, fileSavePath);
                        json.setObj(fileSavePath[0]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            json.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }



    private void findUploadUtil(InputStream ossStream, String[] fileSavePath) {
        String img_server_type = PropertiesUtils.APP.getProperty("app.img.server.type");
        switch (img_server_type) {
            case "oss": // 阿里云oss
                OssUtil.upload(ossStream, fileSavePath[0], false);
                break;
            case "aws": // 亚马逊aws
                //  AWSUtil.uploadToS3(ossStream, fileSavePath[0]);
                break;
            case "azure": // 微软azure
                // AzureUtil.upload(ossStream, fileSavePath[0]);
                break;
            default: // 默认阿里云oss
                OssUtil.upload(ossStream, fileSavePath[0], false);
                break;
        }
    }

}
