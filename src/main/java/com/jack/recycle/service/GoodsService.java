package com.jack.recycle.service;

import com.jack.recycle.model.Goods;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface GoodsService {
    int addGoods(Goods goods);

    int delGoods(String uuid);

    int updGoods(Goods goods);

    List<Goods> dirGoods(Goods goods);

    Goods getGoods(String uuid);

    void getTemplate(HttpServletResponse response);

    void exportGoods(String uuid);

    Map<String,String> uuids(List<String> ids);

    List<Goods> importGoods(MultipartFile file);
}
