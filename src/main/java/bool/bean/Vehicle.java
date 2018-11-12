/**
 * 
 */
package bool.bean;

/**
 * 车辆
 * @author wangw
 */
public class Vehicle {
	/**
	 * ID
	 */
	private String ID;
	
	/**
	 * VIN
	 */
	private String VIN;
	
	/**
	 * ICCID
	 */
	private String ICCID;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String vIN) {
		VIN = vIN;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
}