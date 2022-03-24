package com.jack.recycle.utils;

import org.apache.poi.util.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StreamUtil {
    private static ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    public static void downloadAttach(File file, String fileName) throws IOException {
        HttpServletResponse response = servletRequestAttributes.getResponse();
        downloadAttach(file, response, fileName);
    }

    public static void downloadAttach(File file, HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        setResponse(file, response, fileName);
    }

    public static void setResponse(File file, HttpServletResponse response, String fileName) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0L);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\"");
        streamResponse(file, response);
    }

    private static void streamResponse(File file, HttpServletResponse resp) throws IOException {
        FileInputStream fIn = null;
        ServletOutputStream out = null;

        try {
            fIn = new FileInputStream(file);
            out = resp.getOutputStream();
            byte[] buffer = new byte[1024];
            boolean var5 = false;

            int len;
            while((len = fIn.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }

            out.flush();
        } catch (IOException var9) {
            throw var9;
        } finally {
            IOUtils.closeQuietly(fIn);
            IOUtils.closeQuietly(out);
        }
    }
}
