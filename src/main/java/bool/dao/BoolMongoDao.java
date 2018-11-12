package bool.dao;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * <p>mongoDB使用接口</p>
 * <pre>
 *     author      xiaoyunxiang
 *     date        2018/6/8
 *     email       xyx@tjbool.com
 * </pre>
 */
public interface BoolMongoDao<T>  extends BoolMongoFindDao<T>{
    /**
     * 插入
     */
    public T save(T entity);

    /**
     * 插入集合
     */
    public List<T> saveList(List<T> entityList);

    /**
     * 自定义表名插入数据
     * @param entityList 数据集合
     * @param collectionName 表名
     * @return
     */
    public List<T> saveList(List<T> entityList, String collectionName);

    /**
     * 根据条件 更新
     */
    public WriteResult update(Query query, Update update);

    /**
     * 更新符合条件并sort之后的第一个文档 并返回更新后的文档
     */
    public T updateOne(Query query, Update update);


    /**
     * 根据条件 删除
     *
     * @param query
     */
    public void remove(Query query);
}
