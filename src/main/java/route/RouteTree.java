package route;

import java.util.HashMap;
import java.util.Map;

import service.action.Action;

public class RouteTree {
	private String path;
	private Action defaultAction;
	private Map<String, Route> routeChildren = new HashMap<>();
	private Map<String, RouteTree> routeTree = new HashMap<>();
	
	public RouteTree(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

	public Action getDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(Action defaultAction) {
		this.defaultAction = defaultAction;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public Map<String, Route> getRouteChildren() {
		return routeChildren;
	}

	public void removeRouteChildren(Route routeChildren) {
		this.routeChildren.remove(routeChildren.getPath(), routeChildren);
	}
	
	public void removeRouteTree(RouteTree routeTree) {
		this.routeTree.remove(routeTree.getPath(), routeTree);
	}

	public Map<String, RouteTree> getRouteTree() {
		return routeTree;
	}

	public void setRouteTree(Map<String, RouteTree> routeTree) {
		this.routeTree = routeTree;
	}

	public void appendRoute(Route route) {
		this.routeChildren.put(route.getPath(), route);
	}
	
	public void appendRouteThree(RouteTree tree) {
		this.routeTree.put(tree.getPath(), tree);
	}

	
	
}
