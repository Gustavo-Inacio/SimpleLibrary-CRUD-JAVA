package route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import service.action.Action;
import util.Exceptions.RouteNotFoundException;

public class RoutesMapping {
	private static RouteTree generalRoot;
	public static final String endingDefault = "/index";
	
	static {
		generalRoot = new RouteTree("/");
	}
	
	public static Action getAction (RequestedPathRoute rpr) throws RouteNotFoundException {
		String rootName = "/ParkingLot";
		String path = rpr.getURI().replace(rootName, "");
        
        String[] routing = path.split("(?=/)");
        
        Class actionClass = null;
        
        int lastRoutePos = routing.length - 1;
        for(int i = 0; i < routing.length; i++) {
        	
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
	
	public static Route getRoute(RequestedPathRoute rpr) throws RouteNotFoundException {
		String rootName = "/library";
		String path = rpr.getURI().replace(rootName, "");
		
        String[] routing = path.split("(?=/)");
        
        Route route = null;
        
        int lastRoutePos = routing.length - 1;
        RouteTree lastRouteTree = RoutesMapping.generalRoot;
        
        try {
	     for(int i = 0; i < routing.length ; i++) {
	     	String actualRoutePath = routing[i]; 
	     	
	     	if(i != lastRoutePos ) { // if this path is not the last one, then go to the next route
	     		lastRouteTree = lastRouteTree.getRouteTree(actualRoutePath);
	     	}
	     	else { // if this is the last route informed, it means that or there is a class to be executed, or the default route
	     		if(lastRouteTree.hasChildPath(actualRoutePath, rpr.getMethod())) { // if the actual route is in the routes
	     			route = lastRouteTree.getRoute(actualRoutePath, rpr.getMethod());
	     			
	     		}else { // if this class is not in the routesTree, then tries to access a tree's default root 
	 				Route defaultRoute = lastRouteTree.getRouteTree(actualRoutePath).getDefaultRoute(rpr.getMethod());
	     			route = defaultRoute;
	     		}
	     	}
	     }
        	
        } catch (RouteNotFoundException e) {
        	List<String> r = Arrays.asList(routing);
			System.out.println("RouteNotFound "+r+" -> " + e.getMessage());
			throw e;
		}
        
        return route;
	
	}
	
	public static RouteTree getGeneralRoot() {
		return RoutesMapping.generalRoot;
	}
}
