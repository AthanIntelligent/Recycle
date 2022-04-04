package com.jack.recycle.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PicUtil {
    public static String dealPic(MultipartFile file) {
        //图片上传成功后，将图片的地址写到数据库
        //保存图片的路径
        String filePath = "C:\\PIC\\goodsImg";
        //获取原始图片名
        String originalFilename = file.getOriginalFilename();
        //获取文件的后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //获取图片上传的时间，以时间作为图片的名字可以防止图片重名
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+suffix;
        //封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        //判断文件后缀
        if (".jpg".equals(suffix)||".png".equals(suffix)){
            //把本地文件上传到封装上传文件位置的全路径
            try {
                file.transferTo(targetFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return targetFile.getPath();
        }else {
            return "0";
        }
    }
}
