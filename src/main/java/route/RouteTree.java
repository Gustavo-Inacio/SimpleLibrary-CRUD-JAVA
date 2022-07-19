package route;

import java.util.HashMap;
import java.util.Map;

import service.action.Action;
import util.Exceptions.ActionClassMustImplementsActionInterface;
import util.Exceptions.RouteNotFoundException;

public class RouteTree {
	private String path;
	private Route defaultRoute;
	private Map<String, Route> routeChildren = new HashMap<>();
	private Map<String, RouteTree> routeTree = new HashMap<>();
	private boolean hasChildrenTree = false;
	private boolean hasChildrenRoute = false;
	
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
		return routeChildren.containsValue(route);
	}
	
	public boolean hasChildPath(String path) {
		return routeChildren.containsKey(path);
	}
	
	
	public boolean hasChildrenRoute() {
		return hasChildrenRoute;
	}
	
	public RouteTree(String path) {
		this.path = path;
		System.out.println(path + " path");
	}
	
	public String getPath() {
		return path;
	}

	public Route getDefaultRoute() throws RouteNotFoundException {
		if(!(this.defaultRoute instanceof Route)) {
			throw new RouteNotFoundException("Tried to get default, but there is no default route in + " + this.path);			
		}
		
		return this.defaultRoute;
	}

	public void setDefaultRoute(String method, Class<? extends Action> defaultAction) {
		this.defaultRoute = new Route(RoutesMapping.endingDefault, method, defaultAction);
		
		System.out.println("Appending default rout -> " + (this.defaultRoute ));
	}
	
	public void setDefaultRoute(Route route){
		
		this.defaultRoute = route;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Map<String, Route> getRouteChildren() {
		return routeChildren;
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
	
	public Route getRoute(String route) throws RouteNotFoundException {
		if(!this.routeChildren.containsKey(route))
			throw new RouteNotFoundException("Route " + route + " not found!");
		
		return this.routeChildren.get(route);
	}

	public void setRouteTree(Map<String, RouteTree> routeTree) {
		this.updateHasChildrenTree();
		this.routeTree = routeTree;
	}

	public void appendRoute(Route route) {
		this.updateHasChildrenRoute();
		this.routeChildren.put(route.getPath(), route);
	}
	
	public void appendRouteThree(RouteTree tree) {
		this.updateHasChildrenTree();
		this.routeTree.put(tree.getPath(), tree);
	}

	
	
}
