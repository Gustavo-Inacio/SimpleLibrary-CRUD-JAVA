package route;

import java.util.HashMap;
import java.util.Map;

import service.action.Action;
import util.Exceptions.RouteNotFoundException;
import util.enuns.HttpMethods;

public class RouteTree {
	private String path;
	private Map<HttpMethods, Route> defaultRoute = new HashMap<>();
	//private Map<String, Map<HttpMethods, Route>> routeChildren = new HashMap<>();
	private Map<String, RouteHttpMethod> routeChildren = new HashMap<>();
	private Map<String, RouteTree> routeTree = new HashMap<>();
	private boolean hasChildrenTree = false;
	private boolean hasChildrenRoute = false;
	
	
	public RouteTree(String path) {
		this.path = path;
		System.out.println(path + " path");
	}
	
	private void updateHasChildrenTree() {
		if(this.routeTree.isEmpty()) {
			this.hasChildrenTree = true;
		}
	}
	
	private void updateHasChildrenRoute() {
		if(this.routeChildren.isEmpty()) {
			this.hasChildrenRoute = true;
		}
	}
	
	public boolean hasChildrenTree() {
		return hasChildrenTree;
	}
	
	public boolean hasChild(Route route) {
		boolean contains = false;
		if(this.routeChildren.containsKey(route.getPath())) { // if contains the path
			contains = this.routeChildren.get(route.getPath()).hasRouteWith(route.getMethod()); // if contains the method
		}
		return contains;
	}
	
	public boolean hasChildPath(RequestedPathRoute rpr) {
		//return routeChildren.containsKey(path);
		boolean contains = false;
		if(this.routeChildren.containsKey(rpr.getURI())) { // if contains the path
			contains = this.routeChildren.get(rpr.getURI()).hasRouteWith(rpr.getMethod()); // if contains the method
		}
		return contains;
	}
	
	public boolean hasChildPath(String path, HttpMethods method) {
		//return routeChildren.containsKey(path);
		RequestedPathRoute rpr = new RequestedPathRoute(path, method);
		return this.hasChildPath(rpr);
	}
	
	public boolean hasChildRouteTree(String path) {
		return this.routeTree.containsKey(path);
	}
	
	public boolean hasChildrenRoute() {
		return hasChildrenRoute;
	}

	public String getPath() {
		return this.path;
	}

	public Route getDefaultRoute(HttpMethods method) throws RouteNotFoundException {
		if(!this.defaultRoute.containsKey(method) || this.defaultRoute.get(method).getActionClass() == null) {
			throw new RouteNotFoundException("Tried to get default, but there is no default route in + " + this.path);			
		}
		
		return this.defaultRoute.get(method);
	}

	public void setDefaultRoute(HttpMethods method, Class<? extends Action> defaultAction) {
		Route newRoute = new Route(RoutesMapping.endingDefault, method, defaultAction);
		
		this.defaultRoute.put(method, newRoute);
	}
	
	public void setDefaultRoute(Route route){
		route.setPath(RoutesMapping.endingDefault);
		this.defaultRoute.put(route.getMethod(), route);
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void removeRouteChildren(Route routeChildren) {
		this.updateHasChildrenRoute();
		this.routeChildren.remove(routeChildren.getPath(), routeChildren);
	}
	
	public void removeRouteTree(RouteTree routeTree) {
		this.updateHasChildrenTree();
		this.routeTree.remove(routeTree.getPath(), routeTree);
	}

	public Map<String, RouteTree> getRouteTreeMap() {
		return this.routeTree;
	}
	
	public RouteTree getRouteTree(String route) throws RouteNotFoundException {
		if(!this.routeTree.containsKey(route))
			throw new RouteNotFoundException("Route " + route + " not found!");
		
		return this.routeTree.get(route);
	}
	
	public RouteTree getEntireTree() {
		return this;
	}
	
	public Route getRoute(RequestedPathRoute rpr) throws RouteNotFoundException {
		if(!this.routeChildren.containsKey(rpr.getURI()) || !this.routeChildren.get(rpr.getURI()).hasRouteWith(rpr.getMethod()))
			throw new RouteNotFoundException("Route " + rpr + " not found!");
		
		return this.routeChildren.get(rpr.getURI()).getRoute(rpr.getMethod());
	}
	
	public Route getRoute(String path, HttpMethods method) throws RouteNotFoundException {
		RequestedPathRoute rpr = new RequestedPathRoute(path, method);
		
		return this.getRoute(rpr);
	}

	public void setRouteTree(Map<String, RouteTree> routeTree) {
		this.updateHasChildrenTree();
		this.routeTree = routeTree;
	}

	public void appendRoute(Route route) {
		this.updateHasChildrenRoute();
		boolean hasPath = this.routeChildren.containsKey(route.getPath());
		if(hasPath) {
			RouteHttpMethod routeWithThisPath = this.routeChildren.get(route.getPath());
			routeWithThisPath.addRoute(route);
		}
		else {
			RouteHttpMethod newPath = new RouteHttpMethod(route.getPath());
			newPath.addRoute(route);
			this.routeChildren.put(route.getPath(), newPath);
		}
		
		
	}
	
	public void appendRouteThree(RouteTree tree) {
		this.updateHasChildrenTree();
		this.routeTree.put(tree.getPath(), tree);
	}

	@Override
	public String toString() {
		return "\n \t RouteTree [\n \t path=" + path + ",\n \t defaultRoute=" + defaultRoute + ",\n \t routeChildren=" + routeChildren
				+ ",\n \t routeTree=" + routeTree + ",\n \t hasChildrenTree=" + hasChildrenTree + ",\n \t hasChildrenRoute="
				+ hasChildrenRoute + "\n \t]";
	}
	
	

	
	
}
