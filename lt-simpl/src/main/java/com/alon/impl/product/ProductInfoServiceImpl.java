package com.alon.impl.product;

import com.alon.mapper.dao.ProductInfoMapper;
import com.alon.model.ProductInfo;
import com.alon.service.product.ProductInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoMapper infoMapper;
    @Autowired
    private JestClient jestClient;

    @Override
    public List<ProductInfo> getInfo(ProductInfo info) {
        log.info("======进入service=========");
        List<ProductInfo> infos = new ArrayList<ProductInfo>();
        try {
            infos = infoMapper.getInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        return infos;
    }

    @Override
    public void saveEsInfo(ProductInfo info) {
        Index index = new Index.Builder(info).index("lt").type("productInfo").id(String.valueOf(info.getId()))
                .build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map getEsData(ProductInfo info) {
        //定义一个查询语句
        String query = makeQueryForSearch(info);
        //创建查询对象
        Search search = new Search.Builder(query)
                .addIndex("lt").addType("productInfo").build();
        //准备执行search
        SearchResult searchResult = null;
        try {
            //得到执行结果
            searchResult  = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //把结果封装
        makeResultForSearch(searchResult);
        return null;
    }


    /**
      * 方法表述: 制作dsl语句
      * @Author 一股清风
      * @Date 14:22 2019/5/8
      * @param       info
      * @return void
    */
    private String makeQueryForSearch(ProductInfo info) {
        //GET lt/productInfo/_search
        //{
        //  "query": {
        //    "bool": {
        //      "filter": [
        //          {"term":{"catalog3Id":"61"}}
        //        ],
        //        "must":
        //            { "match": { "skuName": "小米" }  }
        //    }
        //  }
        //  , "highlight": {
        //    "fields": {"skuName":{}}
        //  },
        //  "from": 0,
        //  "size": 2
        //}
        //创建查询器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //创建QueryBuilder对象（bool）
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //根据条件进行查询数据
        if (StringUtils.isNotBlank(info.getSkuName())) {
            //创建一个TermQuery对象
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuName",info.getSkuName());
            //将termQueryBuilder添加到must中，并将must赋给bool
            boolQueryBuilder.must(termQueryBuilder);

            //对skuName进行高亮设置
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            highlightBuilder.field("skuName");
            highlightBuilder.preTags("<span style = 'color : red'>");
            highlightBuilder.postTags("</span>");
            //将高亮放入查询器
            searchSourceBuilder.highlighter(highlightBuilder);
        }
        //继续设置分类id查询
        if (null != info.getCatalog3Id()) {
            //将分类id查询添加到查询器中
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", info.getCatalog3Id());
            //将termQueryBuilder放到filter,并且将filter放到bool中
            boolQueryBuilder.filter(termQueryBuilder);
        }
        //设置分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(2);
        //执行query
        searchSourceBuilder.query(boolQueryBuilder);
        String query = searchSourceBuilder.toString();
        log.info("query：" + query);
        return query;
    }
    /**
      * 方法表述: 封装查询结果
      * @Author 一股清风
      * @Date 14:38 2019/5/8
      * @param       searchResult
      * @return void
    */
    private void makeResultForSearch(SearchResult searchResult) {
        List<ProductInfo> infos = new ArrayList<ProductInfo>();
        //将searchResult中查询的数据解析出来
        List<SearchResult.Hit<ProductInfo, Void>> hits = searchResult.getHits(ProductInfo.class);
        //将集合进行遍历取值
        for (SearchResult.Hit<ProductInfo, Void> hit : hits) {
            ProductInfo source = hit.source;
            //获取高亮字段
            if (null != hit.highlight && hit.highlight.size() > 0) {
                List<String> skuName = hit.highlight.get("skuName");
                //TODO 此处需要封装返回数据
                log.info("highlight(高亮):" + skuName.get(0));
                source.setSkuName(skuName.get(0));
            }
            infos.add(source);
        }
        //TODO 此处需要封装返回数据
        log.info("查询结果："+infos);
    }
}
