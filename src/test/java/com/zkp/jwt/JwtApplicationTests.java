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
    //index???????????????????????????????????????????????????
    public static final String ES_INDEX="han_index";

    //????????????
    @Test
    public void createIndex() throws IOException {
        //1. ????????????
        CreateIndexRequest index = new CreateIndexRequest(ES_INDEX);
        //2. ?????????????????????,?????????????????????
        CreateIndexResponse response = client.indices().create(index, RequestOptions.DEFAULT);
        //3.????????????
        System.out.println(response.toString());
    }
    //????????????????????????
    @Test
    public void exitIndex() throws IOException{
        //1.
        GetIndexRequest request = new GetIndexRequest(ES_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("????????????"+exists);
    }
    //????????????
    @Test
    public void deleteIndex() throws IOException{
        DeleteIndexRequest request = new DeleteIndexRequest(ES_INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("????????????"+response);
    }

    @Test
    public void createDocument() throws IOException {
        //????????????
        User user = new User(1,"zkp","123");
        //????????????
        IndexRequest request = new IndexRequest(ES_INDEX);
        //??????
        request.id("1").timeout(TimeValue.timeValueSeconds(1));
        //????????????????????????
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //?????????????????????????????????????????????
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
        //????????????
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    //??????????????????
    @Test
    public void exitDocument() throws IOException {
        GetRequest request = new GetRequest(ES_INDEX, "1");
        //??????????????????_source ????????????
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //??????????????????
    @Test
    public void getDocument() throws IOException {
        GetRequest request = new GetRequest(ES_INDEX, "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("??????????????????"+response.getSourceAsString());
    }

    //????????????
    @Test
    public void updateDocument() throws IOException {
        //????????????
        User userInfo = new User(1,"??????","sdaf");

        UpdateRequest request = new UpdateRequest(ES_INDEX, "1");
        request.timeout("1s");

        request.doc(JSON.toJSONString(userInfo),XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //????????????
    @Test
    public void deleteDocument() throws IOException{
        DeleteRequest request = new DeleteRequest(ES_INDEX, "1");
        request.timeout("1s");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //????????????
    @Test
    public void bulkDocument() throws IOException{
        BulkRequest request = new BulkRequest();
        request.timeout("10s");

        ArrayList<User> userInfos = new ArrayList<>();
        userInfos.add(new User(1,"??????","sasa"));
        userInfos.add(new User(2,"??????","sasa"));
        userInfos.add(new User(3,"??????","sasa"));
        userInfos.add(new User(4,"??????","sasa"));
        userInfos.add(new User(5,"??????","sasa"));
        userInfos.add(new User(6,"??????","sasa"));

        //?????????????????????
        for (int i = 0; i <userInfos.size() ; i++) {
            request.add(
                    new IndexRequest(ES_INDEX)
                            .id(""+(i+1))
                            .source(JSON.toJSONString(userInfos.get(i)),XContentType.JSON));
        }

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    //??????
    @Test
    public void SearchDocument() throws IOException{
        SearchRequest request = new SearchRequest(ES_INDEX);
        //??????????????????
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //??????????????????QueryBuilders???????????????
        //QueryBuilders.termQuery ????????????
        //QueryBuilders.matchAllQuery() ????????????
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", "??????");
        builder.query(matchQuery);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        System.out.println("??????????????????"+JSON.toJSONString(response.getHits()));
    }



}
