package route;

import java.util.HashMap;
import java.util.Map;

import br.com.parking.service.actions.company.CreateCompanyAction;
import br.com.parking.service.actions.company.DeleteCompanyAction;
import br.com.parking.service.actions.company.UpdateCompanyAction;
import br.com.parking.service.actions.employee.CreateEmployeeAction;
import br.com.parking.service.actions.employee.DeleteEmployeeAction;
import br.com.parking.service.actions.employee.GetAccessToken;
import br.com.parking.service.actions.employee.UpdateEmployeeAction;
import br.com.parking.service.actions.parkinglot.CreateParkinglot;
import br.com.parking.service.actions.vehicle.type.CreateVehicleType;
import service.action.Action;
import util.Exceptions.RouteNotFoundException;

public class RoutesMapping {
	private static RouteTree generalRoot = new RouteTree("/");
	private static String endingDefault = "/index";
	
	public static Action getAction (String URI) throws RouteNotFoundException {
		String rootName = "/ParkingLot";
		String path = URI.replace(rootName, "");
        
        String[] routing = path.split("(?=/)");
        
        Map lastRoute = new HashMap();
        Class actionClass = null;
        
        
        int lastIterate = routing.length - 1;
        for (int i = 0; i < routing.length && actionClass == null ; i++) {
        	if(i == 0) { // if it is the first verify
        		if(generalRoot.get(routing[0]) instanceof Map) { // if the route is a map with other routes, save it to iterate, if not, execute the action
            		lastRoute = (Map) generalRoot.get(routing[0]);	// saving the map to continue iterating the routes
            	} else {
            		actionClass = (Class) generalRoot.get(routing[i]); // save the action class to be executed
            	}
        	} else {
        		if(lastRoute.get(routing[i]) instanceof Map) {
            		lastRoute = (Map) lastRoute.get(routing[i]);
            	} else  {
            		actionClass = (Class) lastRoute.get(routing[i]);
             	}
        	}	
        	
        	if(i == lastIterate  && actionClass == null ) {
        		// if this is the lats verification and no action was found.
        		if(lastRoute.containsKey(endingDefault)) { // if this path has a default action, apply. Or else, throw an Exception   			
        			actionClass = (Class) lastRoute.get(endingDefault);
        		} else { // if the route wasnt find and there is no default 
        			throw new RouteNotFoundException(path);
        		}
        	}
		}
        
        
        try {
        	Class actionObject = Class.forName(actionClass.getName());
        	Action action = (Action) actionClass.newInstance(); // creates an instance of the action and return.
        
        	return action;
        	
        } catch (Exception e) {
        	System.err.println(e);
        	throw new RouteNotFoundException("Route not found -> " + e.getMessage());
		}
	
	}
	
	public void generateBaseRouting() {
		
	}
	
	public void add(String path, Action actionClass, String method) {
		Route route = new Route(path, method, actionClass);
		this.add(route);
	}
	
	public void add(Route route) {
		
	}
}
