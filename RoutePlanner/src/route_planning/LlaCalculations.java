package route_planning;
import static java.lang.Math.*;


public class LlaCalculations {
	

	
	
	public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

		// lat1 = lat1/1000000;
		// lat2 = lat2/1000000;
		// lon1 = lon1/1000000;
		// lon2 = lon2/1000000;
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance)/1000;
		

	}
	
	public static double distance(LlaCoordinate wp1, LlaCoordinate wp2) {

		double lat1 = wp1.getLatitude();
		double lat2 = wp2.getLatitude();
		double lon1 = wp1.getLongitude();
		double lon2 = wp2.getLongitude();
		double el1 = wp1.getAltitude();
		double el2 = wp2.getAltitude();
		
		
		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		//double height = el1 - el2;

		//distance = Math.pow(distance, 2) + Math.pow(height, 2);

		//return Math.sqrt(distance)/1000;
		
		return distance;
		

	}
	
	
	public static double getBearing(double lat1, double lon1, double lat2, double lon2){
		
		  double longitude1 = lon1;
		  double longitude2 = lon2;
		  double latitude1 = Math.toRadians(lat1);
		  double latitude2 = Math.toRadians(lat2);
		  double longDiff= Math.toRadians(longitude2-longitude1);
		  double y= Math.sin(longDiff)*Math.cos(latitude2);
		  double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

		  return (Math.toDegrees(Math.atan2(y, x))+360)%360;
		}
	
	protected static double getBearing(LlaCoordinate wp1, LlaCoordinate wp2){
		
		double lat1 = wp1.getLatitude();
		double lat2 = wp2.getLatitude();
		double lon1 = wp1.getLongitude();
		double lon2 = wp2.getLongitude();

		
		
		  double longitude1 = lon1;
		  double longitude2 = lon2;
		  double latitude1 = Math.toRadians(lat1);
		  double latitude2 = Math.toRadians(lat2);
		  double longDiff= Math.toRadians(longitude2-longitude1);
		  double y= Math.sin(longDiff)*Math.cos(latitude2);
		  double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);

		  return (Math.toDegrees(Math.atan2(y, x))+360)%360;
		}
	
	

		public static LlaCoordinate movePoint(LlaCoordinate lla, double distanceInMetres, double bearing) {
		
		double latitude = lla.getLatitude();
		double longitude = lla.getLongitude();
		
		
	    double brngRad = toRadians(bearing);
	    double latRad = toRadians(latitude);
	    double lonRad = toRadians(longitude);
	    double earthRadiusInMetres = 6371000;
	    double distFrac = distanceInMetres / earthRadiusInMetres;

	    double latitudeResult = asin(sin(latRad) * cos(distFrac) + cos(latRad) * sin(distFrac) * cos(brngRad));
	    double a = atan2(sin(brngRad) * sin(distFrac) * cos(latRad), cos(distFrac) - sin(latRad) * sin(latitudeResult));
	    //double longitudeResult = (lonRad + a + 3 * PI) % (2 * PI) - PI;
	    double longitudeResult = (lonRad + a);
	    longitudeResult = ((toDegrees(longitudeResult) + 540) % 360) - 180;

	    LlaCoordinate lla2 = new LlaCoordinate(toDegrees(latitudeResult), longitudeResult, lla.getAltitude());
	    //System.out.println("latitude: " + toDegrees(latitudeResult) + ", longitude: " + toDegrees(longitudeResult));

	    return lla2;
	}
	
	
	
	
	

}
