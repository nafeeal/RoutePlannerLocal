package edu.nd.dronology.services.extensions.areamapping.v2.generation.impl;

import java.util.List;

import edu.nd.dronology.core.coordinate.LlaCoordinate;
import edu.nd.dronology.services.extensions.areamapping.model.RoutePrimitive.RouteType;
import edu.nd.dronology.services.extensions.areamapping.v2.area.RectangleAreaInfo;
import edu.nd.dronology.services.extensions.areamapping.v2.area.SelectionInfo;
import edu.nd.dronology.services.extensions.areamapping.v2.assignment.RoutePrimitive2;
import edu.nd.dronology.services.extensions.areamapping.v2.generation.IRouteCandidateGenerator;
import edu.nd.dronology.services.extensions.areamapping.v2.generation.RouteCandidateList;


public class RectangleMappingGenerator implements IRouteCandidateGenerator<RectangleAreaInfo> {

	@Override
	public RouteCandidateList generateRotues(RectangleAreaInfo ainfo, SelectionInfo sInfo) {
		
		//The result object that is handed back
		RouteCandidateList candidateList = new RouteCandidateList();
		
		List<LlaCoordinate> coordinates = ainfo.getCoordinates();
		
		/*
		 * LlaCoordinate wp1 = coordinates.get(0); LlaCoordinate wp2 =
		 * coordinates.get(1); LlaCoordinate wp3 = coordinates.get(2); LlaCoordinate wp4
		 * = coordinates.get(3);
		 */
		
		LlaCoordinate w1 = new LlaCoordinate(coordinates.get(0).getLatitude(),coordinates.get(0).getLongitude(), 20);
		LlaCoordinate w2 = new LlaCoordinate(coordinates.get(1).getLatitude(),coordinates.get(1).getLongitude(), 20);
		LlaCoordinate w3 = new LlaCoordinate(coordinates.get(2).getLatitude(),coordinates.get(2).getLongitude(), 20);
		LlaCoordinate w4 = new LlaCoordinate(coordinates.get(3).getLatitude(),coordinates.get(3).getLongitude(), 20);
		
		if (LlaCalculations.distance(w1, w2)<LlaCalculations.distance(w2, w3))
		{ LlaCoordinate s;
			 s=w1;
			 w1=w2;
			 w2=w3;
			 w3=w4;
			 w4=s;
		}
		double nUav =3; // will change it later
		double n_sr= nUav; // Number of sub-regions
		double length = LlaCalculations.distance(w1, w2);
		double width = LlaCalculations.distance(w2, w3);
		double w_sr= length/n_sr; // Width of each sub-region
		double avgSpeed = 20; //ms-1
		double cameraRadius = 25; //will change it accordingly
		
		double distUAV = modRound((w_sr/(2*cameraRadius)))*2*cameraRadius + width*(modRound((w_sr/(2*cameraRadius)))+1);

		for (int i=0;i<n_sr;i++) {
			double bearing=LlaCalculations.getBearing(w1, w2);
			double distCovered=0; 
			//LlaCoordinate startingPoint = LlaCalculations.movePoint(w1,LlaCalculations.distance_hav(w1, w2)/nUav*i,bearing);
			LlaCoordinate startingPoint = LlaCalculations.movePoint(w1,w_sr*i,bearing);
			RoutePrimitive2 rp = new RoutePrimitive2(RouteType.STANDARD, 0, Integer.toString(i));
			rp.addRouteWaypoint(startingPoint);
			
			
			double runningBearing = LlaCalculations.getBearing(w1, w4);
			LlaCoordinate currentPoint = startingPoint;
			LlaCoordinate nextPoint = LlaCalculations.movePoint(currentPoint,width,runningBearing);
			rp.addRouteWaypoint(nextPoint);
			distCovered = distCovered + LlaCalculations.distance(startingPoint, nextPoint);
			currentPoint=nextPoint;
			boolean turn =true;
			int turnDir;
			double coverage=0;
			double DistToMove=0;

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
				
				coverage= coverage+2*cameraRadius;
				
				
			}
			System.out.println("Distance covered by this UAV: "+distCovered);
			System.out.println("Flight Time of this UAV: "+distCovered/avgSpeed/60 + " minutes");

			candidateList.addCandidate(rp);
			}

		System.out.println(distUAV);


		//return candidateList;
		
		
		
		
		/*
		 * RoutePrimitive2 rp1 = new RoutePrimitive2(RouteType.STANDARD, 0, "R1");
		 * rp1.addRouteWaypoint(new LlaCoordinate(upperLeft.getLatitude(),
		 * upperLeft.getLongitude(), 15)); rp1.addRouteWaypoint(new
		 * LlaCoordinate(upperRight.getLatitude(), upperRight.getLongitude(), 15));
		 * 
		 * 
		 * candidateList.addCandidate(rp1);
		 * 
		 * RoutePrimitive2 rp2 = new RoutePrimitive2(RouteType.STANDARD, 0, "R2");
		 * rp2.addRouteWaypoint(new LlaCoordinate(upperLeft.getLatitude(),
		 * upperLeft.getLongitude(), 15)); rp2.addRouteWaypoint(new
		 * LlaCoordinate(upperRight.getLatitude(), upperRight.getLongitude(), 15));
		 * candidateList.addCandidate(rp2);
		 * 
		 * RoutePrimitive2 rp3 = new RoutePrimitive2(RouteType.STANDARD, 0, "R3");
		 * rp3.addRouteWaypoint(new LlaCoordinate(upperLeft.getLatitude(),
		 * upperLeft.getLongitude(), 15)); rp3.addRouteWaypoint(new
		 * LlaCoordinate(upperRight.getLatitude(), upperRight.getLongitude(), 15));
		 * candidateList.addCandidate(rp3);
		 */
		 
		
		
		//if necessary you can add arbitray stuff that might be used later on for the assignment to a property table (key,value)
		candidateList.addProperty("0", 0);
		candidateList.addProperty("1", 1);
		candidateList.addProperty("2", 2);
		
		
		return candidateList;
	}
	 private static double modRound(double num) {
		 
		 double modifiedround = Math.round(num-0.000000000000001);
		 
		 return modifiedround;
		 
	 }

}
