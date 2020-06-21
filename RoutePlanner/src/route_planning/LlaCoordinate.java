package route_planning;

/**
 * 
 */


/**
 * A terrestrial position defined by latitude, longitude, and altitude (LLA)
 *  
 * @author Michael Murphy
 *
 */
public class LlaCoordinate {
 


	private final double latitude;    
	private final double longitude;
	private final double altitude; 

	/**
	 * A terrestrial position defined by latitude, longitude, and altitude (LLA).
	 * 
	 * @param latitude 
	 *          the angle north of the equator in degrees (negative angles define latitudes in the southern hemisphere). Must be a value within this interval: -90 <= latitude <= 90
	 * @param longitude
	 *          the angle east of the prime meridian in degrees (negative angles define longitudes in the western hemisphere). Must be a value within this interval: -180 < longitude <= 180
	 * @param altitude
	 *          the distance above sea level in meters or more precisely the distance above the surface of the WGS-84 reference ellipsoid.
	 * @throws IllegalArgumentException
	 *           when the latitude or longitude is outside the specified range
	 */
	public LlaCoordinate(double latitude, double longitude, double altitude) {
		this.latitude = checkLatitudeRange(latitude);
		this.longitude = checkLongitudeRange(longitude);
		this.altitude = checkAltitudeRange(altitude);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LlaCoordinate))
			return false;
		LlaCoordinate other = (LlaCoordinate) obj;
		if (Double.doubleToLongBits(altitude) != Double.doubleToLongBits(other.altitude))
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	/**
	 * The altitude
	 * 
	 * @return the distance above sea level or more precisely the distance above the surface of the WGS-84 reference ellipsoid. For this project we need this distance in meters
	 */
	public double getAltitude() {
		return altitude;
	}

	@Override
	public String toString() {
		return String.format("LlaCoordinate(%f, %f, %f)", latitude, longitude, altitude);
	}

	/**
	 * The latitude angle.
	 * 
	 * @return the angle north of the equator in degrees (negative angles define latitudes in the southern hemisphere).
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * The longitude angle
	 * 
	 * @return the angle east of the prime meridian in degrees (negative angles define longitudes in the western hemisphere)
	 */
	public double getLongitude() {
		return longitude;
	}
	
	public void printLla() {
		/*
		 * System.out.println("Latitude = " + this.latitude);
		 * System.out.println("Longitude = " + this.longitude);
		 * System.out.println("Altitude = " +this.altitude);
		 */
		//System.out.println(this.latitude+"," +this.longitude+"," +this.altitude);
		System.out.println(this.latitude+"," +this.longitude);


	}



	/**
	 * @return a terrestrial position defined by an x, y, and z coordinate in an Earth centered Earth fixed reference frame.
	 */


	private double checkAltitudeRange(double altitude) {
		// on wrong side of the earth...
		// if (altitude < -6378137) {
		// throw new IllegalArgumentException("Invalid altitude");
		// }
		return altitude;
	}

	private double checkLatitudeRange(double latitude) {
		if (Math.abs(latitude) > 90) {
			throw new IllegalArgumentException("Invalid latitude");
		}
		return latitude;
	}

	private double checkLongitudeRange(double longitude) {
		if (longitude > 180 || longitude <= -180) {
			throw new IllegalArgumentException("Invalid longitude");
		}
		return longitude;
	}



}