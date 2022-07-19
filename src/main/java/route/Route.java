package route;

import service.action.Action;
import util.enuns.HttpMethods;

public class Route {
	private String path;
	private HttpMethods method;
	private Class<? extends Action> actionClass;
	private boolean isProtected = true;
	
	
	public Route(String path, HttpMethods method, Class<? extends Action> actionClass) {
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
	public HttpMethods getMethod() {
		return method;
	}
	public void setMethod(HttpMethods method) {
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
		return "\n \t Route [\n \t path=" + path + ",\n \t method=" + method + ",\n \t actionClass=" + actionClass + ",\n \t isProtected="
				+ isProtected + "\n \t]";
	}
	
	
	
}
