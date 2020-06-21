package route_planning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RouteCandidateList {

	public List<RoutePrimitive> routeCandidates;
	
	public RouteCandidateList() {
		routeCandidates = new ArrayList<RoutePrimitive>();
				
	}
	public void addCandidate(RoutePrimitive pr) {
		routeCandidates.add(pr);

	}
	
	public RoutePrimitive getRoutePrimitive(int k) {
		
		return routeCandidates.get(k);
			
	}
	
	public void printRouteCandidateList() {
		int i;
		for(i=0;i<routeCandidates.size();i++) {
			System.out.println("Waypoints for UAV-"+(i+1));
			routeCandidates.get(i).printRoutePrimitive();
		}
	}


	
	
	
}
