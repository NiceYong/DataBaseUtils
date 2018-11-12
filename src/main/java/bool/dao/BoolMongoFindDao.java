package bool.dao;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * <pre>
 *     author      xiaoyunxiang
 *     date        2018/6/29
 *     email       xyx@tjbool.com
 * </pre>
 */
public interface BoolMongoFindDao<T> {

    /**
     * 根据ID查询
     */
    public T findById(String id);

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     */
    public T findById(String id, String collectionName);

    /**
     * 获得所有该类型记录
     */
    public List<T> findAll();

    /**
     * 获得所有该类型记录,并且指定了集合名(表的意思)
     */
    public List<T> findAll(String collectionName);

    /**
     * 根据条件查询
     */
    public List<T> find(Query query);

    /**
     * 根据条件查询一个
     */
    public T findOne(Query query);

    /**
     * 根据条件 获得总数
     */
    public long count(Query query);

    /**
     * 分组查询
     * @param groupOperation 分组字段
     * @return
     */
    public AggregationResults<Map> FindGroupResult(GroupOperation groupOperation);

    /**
     * 分页方法
     */
    public List<T> findPage(Query query);

}
