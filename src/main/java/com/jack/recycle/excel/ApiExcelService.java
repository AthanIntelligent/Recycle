package com.jack.recycle.excel;

import java.util.List;

/**
 * 定义导入excel api
 */
public interface ApiExcelService {
    void saveData(Object o) ;

    void saveBathData(List list);
}
