package route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import service.action.Action;
import util.Exceptions.RouteNotFoundException;

public class RoutesMapping {
	private static RouteTree generalRoot = new RouteTree("");
	public static final String endingDefault = "/index";
	
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
        RouteTree lastRouteTree = RoutesMapping.generalRoot.getEntireTree();
        
        try {
        	
//        	if(RoutesMapping.generalRoot.hasChildrenTree()) {
//     			lastRouteTree = RoutesMapping.generalRoot.getEntireTree();
//     		}
//        	else if(routing.length == 1) {
//        		if(RoutesMapping.generalRoot.hasChildrenRoute()){
//            		route = RoutesMapping.generalRoot.getRoute(routing[0]);
//            	} else {
//            		Route defaultRoute = lastRouteTree.getDefaultRoute();
//         			route = defaultRoute;
//            	}
//        	}
//        	else {
//        		throw new RouteNotFoundException("Route not found");
//        	}
        	
             
             for(int i = 0; i < routing.length ; i++) {
             	String actualRoutePath = routing[i];
             	if(actualRoutePath.trim() == "/") {
         			actualRoutePath = "";
         		}
             	if(i != lastRoutePos ) { // if this path is not the last one, then go to the next route
             		lastRouteTree = lastRouteTree.getRouteTree(actualRoutePath);
             	}
             	else {
             		if(lastRouteTree.hasChildPath(actualRoutePath))
             			route = lastRouteTree.getRoute(actualRoutePath);
             		else {

             			System.out.println("Explode");
             			
         				System.out.println("Tring to get default ");
         				Route defaultRoute = lastRouteTree.getDefaultRoute();
             			route = defaultRoute;
             			
             			System.out.println("default rout -> " + defaultRoute);
             		}
             	}
             }
        	
        }catch (RouteNotFoundException e) {
        	List<String> r = Arrays.asList(routing);
			System.out.println("RouteNotFound "+r+" -> " + e.getMessage());
			throw e;
		}
        
        return route;
        
       
        
//        try {
//        	Class actionObject = Class.forName(actionClass.getName());
//        	Action action = (Action) actionClass.newInstance(); // creates an instance of the action and return.
//        
//        	return action;
//        	
//        } catch (Exception e) {
//        	System.err.println(e);
//        	throw new RouteNotFoundException("Route not found -> " + e.getMessage());
//		}
	
	}
	
	public static RouteTree getGeneralRoot() {
		return RoutesMapping.generalRoot;
	}
}
