/**
 * 
 */
package bool.bean;

/**
 * 结果
 * @author wangw
 */
public class Result {
	/**
	 * 成功
	 */
	private boolean succeed;
	
	/**
	 * 原因
	 */
	private String reason;
	
	public boolean isSucceed() {
		return succeed;
	}
	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}