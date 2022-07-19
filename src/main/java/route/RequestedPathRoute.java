package route;

public class RequestedPathRoute {
	private String URI;
	private String method;
	
	public RequestedPathRoute(String URI, String method) {
		this.URI = URI;
		this.method = method;
	}

	public String getURI() {
		return URI;
	}

	public String getMethod() {
		return method;
	}
}
