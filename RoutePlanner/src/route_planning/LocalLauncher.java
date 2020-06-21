package route_planning;

public class LocalLauncher {
	
	
	public static void main(String args []) {
		
		

		
		LlaCoordinate lla1 = new LlaCoordinate(41.52502908777771, -86.25366162200442


,50);
		LlaCoordinate lla2 = new LlaCoordinate(41.52502808373813, -86.25228833098879


,50);
		LlaCoordinate lla3 = new LlaCoordinate(41.5245130093639, -86.2522896720933


,0);
		LlaCoordinate lla4 = new LlaCoordinate(41.52451602150669, -86.25366162200442


,0);

		//double k=LlaCalculations.distance(lat1, lon1,lat2,lon2,"K");
		//double m=LlaCalculations.distance(lla2,lla4);
		//double bearing=LlaCalculations.getBearing(lla2, lla4);
		//LlaCoordinate lla5 = LlaCalculations.movePoint(lla2,m,bearing);

		

		//lla5.printLla();
		//System.out.println("bearing:" +bearing);
		//System.out.println(l);
		//System.out.println("dist:"+m);		
		//System.out.println(lla3.getLatitude());	
		//System.out.println(lla3.getLongitude());	
		
		RouteCandidateList rc = AssignRoutes.getRoutes(lla1,lla2,lla3,lla4,3,15);
		
		//System.out.println(rc.getRoutePrimitive(0));
		//System.out.println(rc.getRoutePrimitive(0));
		
		//RoutePrimitive p=rc.getRoutePrimitive(0);

		rc.printRouteCandidateList();
			
		
	}
	
	
	
	

}
