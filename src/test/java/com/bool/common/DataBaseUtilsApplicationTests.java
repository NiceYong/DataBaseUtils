package com.bool.common;

import bool.DataBaseUtilsApplication;
import bool.config.property.KafkaBoolProperties;
import bool.service.kafka.util.KafkaUtil;
import bool.service.mongodb.dao.mongoServiceTest.MongoBean;
import bool.service.mongodb.mongoServiceTest.MongodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataBaseUtilsApplication.class)
public class DataBaseUtilsApplicationTests {

	@Autowired
	private MongodService mongodService;

	/**
	 * 测试mongoDB封装方法
     */
	@Test
	public void contextLoads() {

		/*增加方法(单条)*/
		MongoBean build = MongoBean.builder()
				.userName("xyx")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean save = mongodService.save(build);
		System.out.println(save);


		/*增加方法(集合)*/
		MongoBean build1 = MongoBean.builder()
				.userName("xyx1")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean build2 = MongoBean.builder()
				.userName("xyx2")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean build3 = MongoBean.builder()
				.userName("xyx3")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean build4 = MongoBean.builder()
				.userName("xyx4")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean build5 = MongoBean.builder()
				.userName("xyx5")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		MongoBean build6 = MongoBean.builder()
				.userName("xyx6")
				.age(27)
				.money(3000.5)
				.startDate(new Date())
				.endDate(new Date())
				.build();
		List<MongoBean> mongoBeanList = new ArrayList<>(6);
		mongoBeanList.add(build1);
		mongoBeanList.add(build2);
		mongoBeanList.add(build3);
		mongoBeanList.add(build4);
		mongoBeanList.add(build5);
		mongoBeanList.add(build6);
		//默认注解表加入数据
		//List<MongoBean> mongoBeanList1 = mongodService.saveList(mongoBeanList);
		//自定义表名加入数据
		List<MongoBean> mongoBeanList1 = mongodService.saveList(mongoBeanList,"mongoBean");
		System.out.println(mongoBeanList1);



		/*删除方法*/
		//Query query = new Query();
		//mongodService.remove(query);
	}

	@Test
	public void mongoFind(){

		/*根据ID查询*/
		//根据实体反射查找表
		MongoBean byId = mongodService.findById("5b34871df0526131f33acf49");
		//固定表名查找
		MongoBean mongoBean = mongodService.findById("5b34871df0526131f33acf49", "mongo_bean");
		//输出
		System.out.println(byId);
		System.out.println(mongoBean);

		/*查询全部*/
		//根据实体反射查找表
		List<MongoBean> all = mongodService.findAll();
		//固定表名查找
		List<MongoBean> mongoBeanAll = mongodService.findAll("mongo_bean");
		//输出
		System.out.println(all);
		System.out.println(mongoBeanAll);

		/*根据条件查询一条数据，查询最新数据*/
		Query query = new Query().with(new Sort(Sort.Direction.DESC,"startDate"));
		//默认增加limit = 1
		MongoBean one = mongodService.findOne(query);
		List<MongoBean> mongoBeen = mongodService.find(query);
		System.out.println(one);
		System.out.println(mongoBeen);

		/*统计查询*/
		//先加入排序字段
		Query countQuery = new Query();
		long count = mongodService.count(countQuery);
		System.out.println(count);

		/*分组查询*/
		//首先创建需要分组字段对象
		GroupOperation groupOperation = Aggregation.group("userName");
		AggregationResults<Map> results = mongodService.FindGroupResult(groupOperation);
		System.out.println(results);

	}

	/**
	 * kafka 生产测试方式
     */
	@Autowired
	private KafkaBoolProperties kafka;

	@Test
	public void kafkaTest(){
		for(int i =0;i<1000;i++){
			KafkaUtil.send(kafka.getProducerTopic(),"xyx4");
		}
	}

}
