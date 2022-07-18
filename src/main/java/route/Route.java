package route;

import service.action.Action;

public class Route {
	private String path;
	private String method;
	private Action actionClass;
	private boolean isProtected = true;
	
	
	public Route(String path, String method, Action actionClass) {
		this.path = path;
		this.method = method;
		this.actionClass = actionClass;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public Action getActionClass() {
		return actionClass;
	}
	public void setActionClass(Action actionClass) {
		this.actionClass = actionClass;
	}
	public boolean isProtected() {
		return isProtected;
	}
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	
	
}
