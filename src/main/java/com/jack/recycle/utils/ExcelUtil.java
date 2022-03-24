package com.jack.recycle.utils;

import com.alibaba.excel.EasyExcel;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class ExcelUtil<clazz> {
    public static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    public static void export(String fileName, List data, String sheetName, Class clazz) {
        EasyExcel.write(getOutputStream(fileName), clazz).sheet(sheetName).doWrite(data);
    }

    private static OutputStream getOutputStream(String fileName) {
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String replaceAll = null;
        try {
            replaceAll = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + replaceAll + ".xlsx");
            return response.getOutputStream();
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException:{}", e);
        } catch (IOException e) {
            logger.error("IOException:{}", e);
        }
        return null;
    }

    public static Integer importExcel(MultipartFile file, Class clazz){
        //判断是不是excel文件
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (".xls".equals(suffix)||".xlsx".equals(suffix)){
            //循环excel行，去掉表头行，从第二行开始循环
//            EasyExcel.
        }else{
            return StatusCode.CONFLICT;
        }
        return StatusCode.OK;
    }

}
