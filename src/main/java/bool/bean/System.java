/**
 * 
 */
package bool.bean;

/**
 * 系统
 * @author wangw
 */
public class System {
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "error";
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 信息
	 */
	private Object info;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}
}