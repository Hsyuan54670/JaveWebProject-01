package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.UpLoadService;
import com.hsyuan.tliaswebstduy.utils.AliyunOSSOperator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UpLoadController {


    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 本地磁盘存储方案
     */
    /*@PostMapping
    public Result upload(String name,Integer age, MultipartFile file) throws IOException {
        log.info("上传的文件名为：{}",file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件
        file.transferTo(new File("D:/AForAll/Java/Projects/springboot-web-quickstart/Tlias-web-stduy/src/main/resources/templates/" + fileName));
        return Result.success();
    }*/

    @PostMapping
    public Result upload(MultipartFile file) throws Exception {
        log.info("上传的文件名为：{}",file.getOriginalFilename());
        String url=aliyunOSSOperator.upload(file.getBytes(),file.getOriginalFilename());
        log.info("上传的文件url为：{}",url);
        return Result.success(url);
    }
}
