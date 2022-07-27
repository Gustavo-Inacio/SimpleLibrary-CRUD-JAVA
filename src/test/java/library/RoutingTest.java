package library;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import route.RequestedPathRoute;
import route.Route;
import route.RouteTree;
import route.RoutesMapping;
import service.action.Action;
import service.action.CreateAuthour;
import util.Exceptions.RouteNotFoundException;
import util.enuns.HttpMethods;
import util.messages.Message;

public class RoutingTest {
	
	@Test
	public void shouldReturnCreateAuthourAction() throws RouteNotFoundException {
		String URI = "/library/authour";
		HttpMethods method = HttpMethods.POST;
		
		Route newRoute = new Route("/authour", method, CreateAuthour.class);
		RoutesMapping.getGeneralRoot().appendRoute(newRoute);
		
		RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
		Route route = RoutesMapping.getRoute(rpr);
		
		RoutesMapping.getGeneralRoot().removeRouteChildren(newRoute);
		
		RoutesMapping.getGeneralRoot().removeRouteChildren(route);
		
		Assert.assertEquals(route.getActionClass(), CreateAuthour.class);
	}
	
	public void shouldThrowARouteNotFoundException() throws RouteNotFoundException {
		Assertions.assertThrows(RouteNotFoundException.class, () -> {
			String URI = "/library/authour";
			HttpMethods method = HttpMethods.POST;
			
			RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
			Route route = RoutesMapping.getRoute(rpr);
		});
	}
	
	public void shouldThrowARouteNotFoundExceptionForEmpty() throws RouteNotFoundException{
		Assertions.assertThrows(RouteNotFoundException.class, () -> {
			String URI = "/";
			HttpMethods method = HttpMethods.POST;
			
			RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
			Route route = RoutesMapping.getRoute(rpr);
		});
	}
	
	@Test
	public void shouldDifferentiateDefaultPathsByMethod() throws RouteNotFoundException {
		RequestedPathRoute rpr = new RequestedPathRoute("/library/treeTest", HttpMethods.POST);
		RequestedPathRoute rprGET = new RequestedPathRoute("/library/treeTest", HttpMethods.GET);
		
		RouteTree rt = new RouteTree("/treeTest");
		Route defaultRoutePOST = new Route("", HttpMethods.POST, TestImplementationOfAction01.class);
		Route defaultRouteGET = new Route("", HttpMethods.GET, TestImplementationOfAction02.class);
		rt.setDefaultRoute(defaultRoutePOST);
		rt.setDefaultRoute(defaultRouteGET);
		
		RoutesMapping.getGeneralRoot().appendRouteTree(rt);
		
		Route routePOST = RoutesMapping.getRoute(rpr);
		Route routeGET = RoutesMapping.getRoute(rprGET);
		
		Assert.assertEquals(routePOST.getActionClass(), defaultRoutePOST.getActionClass());
		Assert.assertEquals(routeGET.getActionClass(), defaultRouteGET.getActionClass());
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
		
	}
	
	@Test
	public void shouldReturnRouteFromTree() throws RouteNotFoundException {
		String URI = "/library/authour/bio";
		HttpMethods method = HttpMethods.POST;
		
		Route newRoute = new Route("/bio", method, CreateAuthour.class);
		RouteTree rt = new RouteTree("/authour");
		rt.appendRoute(newRoute);
		
		RoutesMapping.getGeneralRoot().appendRouteTree(rt);
		
		RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
		Route route = RoutesMapping.getRoute(rpr);
		
		Assert.assertEquals(route.getActionClass(), CreateAuthour.class);
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
		
	}
	
	@Test
	public void shouldReturnBaseRoute() throws RouteNotFoundException {
		String URI = "/library/test";
		HttpMethods method = HttpMethods.POST;
		
		Route newRoute = new Route("/test", method, CreateAuthour.class);

		RoutesMapping.getGeneralRoot().appendRoute(newRoute);
		
		RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
		Route route = RoutesMapping.getRoute(rpr);
		
		Assert.assertEquals(route.getActionClass(), CreateAuthour.class);
		
		RoutesMapping.getGeneralRoot().removeRouteChildren(newRoute);
	}
	
	@Test
	public void shouldDiferenciateBetweenTwoMethods() throws RouteNotFoundException {
		String URI = "/library/testofmethod/test";
		
		RequestedPathRoute rprPOST = new RequestedPathRoute(URI, HttpMethods.POST);
		RequestedPathRoute rprGET = new RequestedPathRoute(URI, HttpMethods.GET);
		
		Route newRoutePOST = new Route("/test", HttpMethods.POST, TestImplementationOfAction02.class);
		Route newRouteGET = new Route("/test", HttpMethods.GET, TestImplementationOfAction01.class);
		RouteTree rt = new RouteTree("/testofmethod");
		rt.appendRoute(newRoutePOST);
		rt.appendRoute(newRouteGET);

		RoutesMapping.getGeneralRoot().appendRouteTree(rt);
	
		Route routePOST = RoutesMapping.getRoute(rprPOST);
		Route routeGET = RoutesMapping.getRoute(rprGET);
		
		Assert.assertEquals(routePOST.getActionClass(), TestImplementationOfAction02.class);
		Assert.assertEquals(routeGET.getActionClass(), TestImplementationOfAction01.class);
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
	}
	
	@Test
	public void shouldReturnDefaultRoute() throws RouteNotFoundException {
		String URI = "/library/tree";
		
		RequestedPathRoute rprDefault = new RequestedPathRoute(URI, HttpMethods.POST);
		RequestedPathRoute rprChild = new RequestedPathRoute(URI + "/child", HttpMethods.POST);
		
		Route defaultRoute = new Route(RoutesMapping.endingDefault, HttpMethods.POST, TestImplementationOfAction02.class);
		Route childRoute = new Route("/child", HttpMethods.POST, TestImplementationOfAction01.class);
		
		RouteTree rt = new RouteTree("/tree");
		
		rt.appendRoute(childRoute);
		rt.setDefaultRoute(HttpMethods.POST, TestImplementationOfAction02.class);

		RoutesMapping.getGeneralRoot().appendRouteTree(rt);
	
		Route childReturned = RoutesMapping.getRoute(rprChild);
		Route defaultReturned = RoutesMapping.getRoute(rprDefault);
		
		Assert.assertEquals(childReturned.getActionClass(), TestImplementationOfAction01.class);
		Assert.assertEquals(defaultReturned.getActionClass(), TestImplementationOfAction02.class);
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
	}
}


class TestImplementationOfAction01 implements Action{

	@Override
	public Message execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}

class TestImplementationOfAction02 implements Action{

	@Override
	public Message execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}
