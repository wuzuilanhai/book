package com.biubiu.controller;

import com.biubiu.response.Response;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.collect.Sets;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Set;

/**
 * Created by Haibiao.Zhang on 2019-03-29 14:27
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Response<StorePath> upload(MultipartFile file) throws IOException {
        Set<MetaData> metaData = Sets.newHashSet();
        metaData.add(new MetaData("author", "zhb"));
        metaData.add(new MetaData("time", String.valueOf(System.currentTimeMillis())));
        return Response.succeed(
                storageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), metaData));
    }

    /**
     * 文件删除
     */
    @DeleteMapping
    public Response delete(String file) {
        storageClient.deleteFile(file);
        return Response.succeed("delete success");
    }

    /**
     * 文件下载
     */
    @GetMapping("/download")
    public void download(String group, String path, String fileName, HttpServletResponse response) throws IOException {
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        //设置相应类型application/octet-stream（注：applicatoin/octet-stream 为通用，一些其它的类型苹果浏览器下载内容可能为空）
        response.reset();
        response.setContentType("applicatoin/octet-stream");
        //设置头信息                 Content-Disposition为属性名  附件形式打开下载文件   指定名称  为 设定的fileName
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 写入到流
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.close();
    }

}
