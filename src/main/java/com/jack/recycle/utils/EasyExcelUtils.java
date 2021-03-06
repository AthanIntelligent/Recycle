package com.jack.recycle.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.jack.recycle.excel.ApiExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class EasyExcelUtils {

    private static ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


    /**
     * @param fileName
     * @return
     * @throws IOException
     */
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
            log.error("UnsupportedEncodingException:{}", e);
        } catch (IOException e) {
            log.error("IOException:{}", e);
        }
        return null;
    }

    /**
     * ??????
     *
     * @param fileName
     * @param data
     * @param sheetName
     * @param clazz
     */
    public static void export(String fileName, List data, String sheetName, Class clazz) {
        EasyExcel.write(getOutputStream(fileName), clazz).sheet(sheetName).doWrite(data);
    }
    /**
     * ?????????sheet
     *
     * @param fileName
     * @param data1
     * @param sheetName1
     * @param clazz1
     * @param data2
     * @param sheetName2
     * @param clazz2
     */
    public static void export(String fileName, List data1,List data2 , String sheetName1,String sheetName2, Class clazz1,Class clazz2) {
        List<String> excludeColumnFiledNames = new ArrayList<>();
        excludeColumnFiledNames.add("errorMessage");
        ExcelWriter excelWriter = EasyExcel.write(fileName).excludeColumnFiledNames(excludeColumnFiledNames).build();
        try {
            WriteSheet writeSheet = EasyExcel.writerSheet(0, sheetName1).head(clazz1).build();
            excelWriter.write(data1, writeSheet);
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1, sheetName2).head(clazz2).build();
            excelWriter.write(data2, writeSheet2);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }

    /**
     * ??????
     *
     * @param inputStream
     * @param importExcelListener
     * @param clazz
     */

    public static void Import(InputStream inputStream, ApiExcelListener importExcelListener, Class clazz) {
        EasyExcel.read(inputStream, clazz, importExcelListener).sheet().doRead();
    }



    public static HorizontalCellStyleStrategy getStyleStrategy(){

        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)12);
        // ????????????
        headWriteFont.setFontName("Frozen");
        headWriteCellStyle.setWriteFont(headWriteFont);
        //????????????
        headWriteCellStyle.setWrapped(false);
        // ??????????????????
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // ??????????????????
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // ???????????????
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // ?????????????????? FillPatternType ???FillPatternType.SOLID_FOREGROUND ??????????????????????????????.???????????? FillPatternType?????????????????????
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SQUARES);
        // ????????????
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // ????????????
        contentWriteFont.setFontHeightInPoints((short)12);
        // ????????????
        contentWriteFont.setFontName("Calibri");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // ??????????????? ?????????????????? ???????????????????????? ?????????????????????????????????
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

}
