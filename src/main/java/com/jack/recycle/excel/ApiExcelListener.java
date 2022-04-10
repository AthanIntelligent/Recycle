package com.jack.recycle.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.read.metadata.holder.ReadRowHolder;
import com.alibaba.excel.util.CollectionUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
public class ApiExcelListener extends AnalysisEventListener<Object> {

    private static final int BATCH_COUNT = 30;

    List<ApiExcelModel> list = new ArrayList<>();

    private ApiExcelService apiExcelService;

    public ApiExcelListener(ApiExcelService apiExcelService) {
        this.apiExcelService = apiExcelService;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }else if (exception instanceof Exception){
            ReadRowHolder readRowHolder = context.readRowHolder();
            Integer rowIndex = readRowHolder.getRowIndex();
            log.error("第{}行，数据格式有误:{}", rowIndex,exception.getMessage());
            String message = String.format("第%s行，数据格式有误: %s", rowIndex, exception.getMessage());
            throw new Exception(message);
        }
    }

    @SneakyThrows
    @Override
    public void invoke(Object obj, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}");
        ApiExcelModel model = (ApiExcelModel) obj;
        //校验
        validate(model);
        list.add(model);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            apiExcelService.saveBathData(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 校验
     * @param model
     */
    private void validate(ApiExcelModel model) throws Exception {
        hibernateValidate(model);
    }


    /**
     * hibernate校验
     * @param model
     */
    private void hibernateValidate(ApiExcelModel model) throws Exception {
        Set<ConstraintViolation<ApiExcelModel>> validateSet = HibernateValidator.getValidator().validate(model, Default.class);
        if (validateSet != null && !validateSet.isEmpty()) {
            ConstraintViolation constraint = validateSet.stream().findAny().orElse(null);
            throw new Exception(constraint.getMessage());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //遗留数据保存
        if (!CollectionUtils.isEmpty(list)) {
            apiExcelService.saveBathData(list);
        }
        log.info("所有数据解析完成！");
    }

}
