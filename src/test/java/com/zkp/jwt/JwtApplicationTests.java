package com.zkp.jwt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkp.jwt.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class JwtApplicationTests {

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(1);
        user.setUsername("zzz");
        String s = JSON.toJSONString(user);

        System.out.println(s);
        User parse =JSON.parseObject(s,User.class);
        System.out.println(parse);

        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(jsonObject.toJSONString());
    }
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    //index名字，静态一般都是放在另一个类中的
    public static final String ES_INDEX="han_index";

    //创建索引
    @Test
    public void createIndex() throws IOException {
        //1. 创建索引
        CreateIndexRequest index = new CreateIndexRequest(ES_INDEX);
        //2. 客户端执行请求,请求后获得相应
        CreateIndexResponse response = client.indices().create(index, RequestOptions.DEFAULT);
        //3.打印结果
        System.out.println(response.toString());
    }
    //测试索引是否存在
    @Test
    public void exitIndex() throws IOException{
        //1.
        GetIndexRequest request = new GetIndexRequest(ES_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("是否存在"+exists);
    }
    //删除索引
    @Test
    public void deleteIndex() throws IOException{
        DeleteIndexRequest request = new DeleteIndexRequest(ES_INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("是否删除"+response);
    }

    @Test
    public void createDocument() throws IOException {
        //创建对象
        User user = new User(1,"zkp","123");
        //创建请求
        IndexRequest request = new IndexRequest(ES_INDEX);
        //规则
        request.id("1").timeout(TimeValue.timeValueSeconds(1));
        //将数据放到请求中
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求，获取相应的结果
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate",LocalDateTime.now() );
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1").source(builder);
        IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index.toString());
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        //打印一下
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    //判断是否存在
    @Test
    public void exitDocument() throws IOException {
        GetRequest request = new GetRequest(ES_INDEX, "1");
        //不获取返回的_source 的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    @Test
    public void getDocument() throws IOException {
        GetRequest request = new GetRequest(ES_INDEX, "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("获取到的结果"+response.getSourceAsString());
    }

    //更新文档
    @Test
    public void updateDocument() throws IOException {
        //创建对象
        User userInfo = new User(1,"李四","sdaf");

        UpdateRequest request = new UpdateRequest(ES_INDEX, "1");
        request.timeout("1s");

        request.doc(JSON.toJSONString(userInfo),XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除文档
    @Test
    public void deleteDocument() throws IOException{
        DeleteRequest request = new DeleteRequest(ES_INDEX, "1");
        request.timeout("1s");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量添加
    @Test
    public void bulkDocument() throws IOException{
        BulkRequest request = new BulkRequest();
        request.timeout("10s");

        ArrayList<User> userInfos = new ArrayList<>();
        userInfos.add(new User(1,"李四","sasa"));
        userInfos.add(new User(2,"李四","sasa"));
        userInfos.add(new User(3,"李四","sasa"));
        userInfos.add(new User(4,"李四","sasa"));
        userInfos.add(new User(5,"李四","sasa"));
        userInfos.add(new User(6,"李四","sasa"));

        //进行批处理请求
        for (int i = 0; i <userInfos.size() ; i++) {
            request.add(
                    new IndexRequest(ES_INDEX)
                            .id(""+(i+1))
                            .source(JSON.toJSONString(userInfos.get(i)),XContentType.JSON));
        }

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    //查询
    @Test
    public void SearchDocument() throws IOException{
        SearchRequest request = new SearchRequest(ES_INDEX);
        //构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //查询条件使用QueryBuilders工具来实现
        //QueryBuilders.termQuery 精准查询
        //QueryBuilders.matchAllQuery() 匹配全部
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", "李四");
        builder.query(matchQuery);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println("查询出的结果"+JSON.toJSONString(response.getHits()));
    }



}
