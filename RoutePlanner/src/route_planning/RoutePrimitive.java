package route_planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class RoutePrimitive {

	private List<LlaCoordinate> llaCoordinates;

	public RoutePrimitive() {

		llaCoordinates = new ArrayList<LlaCoordinate>();
	}

	public List<LlaCoordinate> getRouteWaypoints() {
		return Collections.unmodifiableList(llaCoordinates);
	}

	public void addRouteWaypoint(LlaCoordinate llaCoordinate) {
		llaCoordinates.add(llaCoordinate);
	}

	public void printRoutePrimitive() {
		int i;
		for(i=0;i<llaCoordinates.size();i++) {
			llaCoordinates.get(i).printLla();
		}
		
	}
	
	
	

	
}