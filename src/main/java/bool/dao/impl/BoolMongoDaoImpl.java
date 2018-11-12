package bool.dao.impl;

import bool.dao.BoolMongoDao;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <pre>
 *     author      xiaoyunxiang
 *     date        2018/6/8
 *     email       xyx@tjbool.com
 * </pre>
 */
public class BoolMongoDaoImpl<T extends Serializable> implements BoolMongoDao<T> {
    private static final Logger logger = LoggerFactory.getLogger(BoolMongoDaoImpl.class);


    /**
     * spring mongodb　集成操作类
     */
    @Autowired
    private MongoTemplate mongoTemplate;


    private Class<T> entityClass;

    /**
     * 构造方法
     * 默认获取当前泛型传入实体对象类
     */
    public BoolMongoDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        // 返回表示此类型实际类型参数的 Type 对象的数组。
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        this.entityClass = (Class) params[0];
    }

    /**
     * 获得泛型类
     */
    private Class<T> getEntityClass() {

        return entityClass;
    }


    @Override
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    /**
     * 插入集合
     *
     * @param entityList
     */
    @Override
    public List<T> saveList(List<T> entityList) {
        mongoTemplate.insertAll(entityList);
        return entityList;
    }

    /**
     * 自定义表名插入数据
     * @param entityList 数据集合
     * @param collectionName 表名
     * @return
     */
    @Override
    public List<T> saveList(List<T> entityList, String collectionName) {
        mongoTemplate.insert(entityList, collectionName);
        return entityList;
    }


    @Override
    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(this.getEntityClass());
    }

    @Override
    public List<T> findAll(String collectionName) {
        return mongoTemplate.findAll(this.getEntityClass(), collectionName);
    }

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }


    @Override
    public long count(Query query) {
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public WriteResult update(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return mongoTemplate.updateMulti(query, update, this.getEntityClass());
    }

    @Override
    public T updateOne(Query query, Update update) {
        if (update == null) {
            return null;
        }
        return mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    @Override
    public void remove(Query query) {
        mongoTemplate.remove(query, this.getEntityClass());
    }

    /**
     * 分组查询
     * @param groupOperation 分组字段
     * @return
     */
    @Override
    public AggregationResults<Map> FindGroupResult(GroupOperation groupOperation) {
        Aggregation agg = Aggregation.newAggregation(this.getEntityClass(), groupOperation);
        return mongoTemplate.aggregate(agg, this.getEntityClass(), Map.class);
    }

    /**
     * 分页方法
     * 区分大数据分页和普通分页
     * 日后待补
     * @param query
     */
    @Override
    public List<T> findPage(Query query) {
        return null;
    }


}
