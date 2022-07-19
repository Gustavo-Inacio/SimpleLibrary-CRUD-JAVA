package route;

import java.util.HashMap;
import java.util.Map;

import util.enuns.HttpMethods;

public class RouteHttpMethod {
	private String path;
	private Map<HttpMethods, Route> routes = new HashMap<>();
	
	public RouteHttpMethod(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setMethod(String path) {
		this.path = path;
	}
	
	public void addRoute(Route route) {
		this.routes.put(route.getMethod(), route);
	}
	
	public void removeRoute(Route route) {
		this.routes.remove(route.getMethod(), route);
	}
	
	public void removeRoute(HttpMethods method) {
		this.routes.remove(method);
	}
	
	public Route getRoute(HttpMethods method) {
		return this.routes.get(method);
	}
	
	public boolean hasRouteWith(HttpMethods method) {
		return this.routes.containsKey(method);
	}
	
	
	
}
