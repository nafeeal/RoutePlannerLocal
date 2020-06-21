package route_planning;
import java.util.*;




public class AssignRoutes {
	
	
	public static RouteCandidateList getRoutes(LlaCoordinate wp1, LlaCoordinate wp2, LlaCoordinate wp3, LlaCoordinate wp4, int nUav, int cameraRadius) {
		
		RouteCandidateList candidateList = new RouteCandidateList();


		
		LlaCoordinate w1=wp1;
		LlaCoordinate w2=wp2;
		LlaCoordinate w3=wp3;
		LlaCoordinate w4=wp4;
		
		if (LlaCalculations.distance(wp1, wp2)<LlaCalculations.distance(wp2, wp3))
		{
			 w1=wp2;
			 w2=wp3;
			 w3=wp4;
			 w4=wp1;
		}
		
		
		double n_sr= nUav; // Number of sub-regions
		double length = LlaCalculations.distance(w1, w2);
		double width = LlaCalculations.distance(w2, w3);
		double w_sr= length/n_sr; // Width of each sub-region
		double avgSpeed = 20; //ms-1

		

		double distUAV2 = modRound((w_sr/(2*cameraRadius)))*2*cameraRadius + width*(modRound((w_sr/(2*cameraRadius)))+1);

		
		
		
		//candidateList.addCandidate(rp1);
		
		for (int i=0;i<n_sr;i++) {
			double bearing=LlaCalculations.getBearing(w1, w2);
			double distCovered=0; 
			//LlaCoordinate startingPoint = LlaCalculations.movePoint(w1,LlaCalculations.distance_hav(w1, w2)/nUav*i,bearing);
			LlaCoordinate startingPoint = LlaCalculations.movePoint(w1,w_sr*i,bearing);
			RoutePrimitive rp = new RoutePrimitive();
			rp.addRouteWaypoint(startingPoint);
			
			
			double runningBearing = LlaCalculations.getBearing(w1, w4);
			LlaCoordinate currentPoint = startingPoint;
			LlaCoordinate nextPoint = LlaCalculations.movePoint(currentPoint,width,runningBearing);
			rp.addRouteWaypoint(nextPoint);
			distCovered = distCovered + LlaCalculations.distance(startingPoint, nextPoint);
			currentPoint=nextPoint;
			boolean turn =true;
			int turnDir;
			int coverage=0;
			double DistToMove=0;
			//phase 1
			//for(int k=0;k<3;k++)
			while(coverage<w_sr-cameraRadius)
			{
				if(turn) { turnDir=270;}
				else {turnDir=90;}
				DistToMove=2*cameraRadius;
				if (coverage> w_sr-2*cameraRadius) {DistToMove=w_sr-cameraRadius-coverage;}
				
				
				runningBearing =(runningBearing +turnDir +360)%360;
				nextPoint = LlaCalculations.movePoint(currentPoint,DistToMove,runningBearing);
				rp.addRouteWaypoint(nextPoint);
				distCovered = distCovered + LlaCalculations.distance(currentPoint, nextPoint);

				currentPoint=nextPoint;
				
				runningBearing =(runningBearing +turnDir +360)%360;
				nextPoint = LlaCalculations.movePoint(currentPoint,width,runningBearing);
				rp.addRouteWaypoint(nextPoint);
				distCovered = distCovered + LlaCalculations.distance(currentPoint, nextPoint);

				currentPoint=nextPoint;
				turn=!turn;
				
				coverage=coverage+2*cameraRadius;
				
				
			}
			System.out.println("Distance covered by this UAV: "+distCovered);
			System.out.println("Flight Time of this UAV: "+distCovered/avgSpeed/60 + " minutes");

			candidateList.addCandidate(rp);
			}

		System.out.println(distUAV2);


		return candidateList;
		
		
	}
	
	 private static double modRound(double num) {
		 
		 double modifiedround = Math.round(num-0.000000000000001);
		 
		 return modifiedround;
		 
	 }
	
	

}
