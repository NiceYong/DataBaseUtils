package bool.service.mongodb.dao.mongoServiceTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * <p></p>
 * <pre>
 *     author      xiaoyunxiang
 *     date        2018/6/28
 *     email       xyx@tjbool.com
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "mongo_bean")
public class MongoBean implements Serializable {

    @Id
    private String id;

    private String userName;

    private Integer age;

    private Double money;

    private Date startDate;

    private Date endDate;
}
