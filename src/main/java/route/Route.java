package route;

import service.action.Action;

public class Route {
	private String path;
	private String method;
	private Class<? extends Action> actionClass;
	private boolean isProtected = true;
	
	
	public Route(String path, String method, Class<? extends Action> actionClass) {
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
	public Class<? extends Action> getActionClass() {
		return actionClass;
	}
	public void setActionClass(Class<Action> actionClass) {
		this.actionClass = actionClass;
	}
	public boolean isProtected() {
		return isProtected;
	}
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}

	@Override
	public String toString() {
		return "Route [path=" + path + ", method=" + method + ", actionClass=" + actionClass + ", isProtected="
				+ isProtected + "]";
	}
	
	
	
}
