package route;

import util.enuns.HttpMethods;

public class RequestedPathRoute {
	private String URI;
	private HttpMethods method;
	
	public RequestedPathRoute(String URI, HttpMethods method) {
		this.URI = URI;
		this.method = method;
	}

	public String getURI() {
		return URI;
	}

	public HttpMethods getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return "RequestedPathRoute [URI=" + URI + ", method=" + method + "]";
	}
	
	
}
