/**
 * 
 */
package bool.bean;

import java.util.List;

/**
 * 传输
 * @author wangw
 */
public class Transmit {
	/**
	 * 平台
	 */
	private Platform platform;
	
	/**
	 * 平台列表
	 */
	private List<Platform> platforms;
	
	/**
	 * 车辆
	 */
	private Vehicle vehicle;
	
	/**
	 * 车辆列表
	 */
	private List<Vehicle> vehicles;
	
	public Platform getPlatform() {
		return platform;
	}
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}
	public List<Platform> getPlatforms() {
		return platforms;
	}
	public void setPlatforms(List<Platform> platforms) {
		this.platforms = platforms;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}