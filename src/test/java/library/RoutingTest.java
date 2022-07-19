package library;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import route.RequestedPathRoute;
import route.Route;
import route.RouteTree;
import route.RoutesMapping;
import service.action.Action;
import service.action.CreateAuthour;
import util.Exceptions.ActionClassMustImplementsActionInterface;
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
		
		System.out.println(route);
		
		RoutesMapping.getGeneralRoot().removeRouteChildren(newRoute);
		
		Assert.assertEquals(route.getActionClass(), CreateAuthour.class);
	}
	
	@Test(expected = RouteNotFoundException.class)
	public void shouldThrowARouteNotFoundException() throws RouteNotFoundException {
		String URI = "/library/authour";
		HttpMethods method = HttpMethods.POST;
		
		RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
		Route route = RoutesMapping.getRoute(rpr);
		
	}
	
	
	@Test
	public void shouldReturnRouteFromTree() throws RouteNotFoundException {
		String URI = "/library/authour/bio";
		HttpMethods method = HttpMethods.POST;
		
		Route newRoute = new Route("/bio", method, CreateAuthour.class);
		RouteTree rt = new RouteTree("/authour");
		rt.appendRoute(newRoute);
		
		RoutesMapping.getGeneralRoot().appendRouteThree(rt);
		
		RequestedPathRoute rpr = new RequestedPathRoute(URI, method);
		Route route = RoutesMapping.getRoute(rpr);
		
		System.out.println(route);
		
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
		
		System.out.println(route);
		
		Assert.assertEquals(route.getActionClass(), CreateAuthour.class);
		
		RoutesMapping.getGeneralRoot().removeRouteChildren(newRoute);
	}
	
	@Test
	public void shouldDiferenciateBetweenTwoMethods() throws RouteNotFoundException {
		String URI = "/library/testofmethod/test";
		
		RequestedPathRoute rprPOST = new RequestedPathRoute(URI, HttpMethods.POST);
		RequestedPathRoute rprGET = new RequestedPathRoute(URI, HttpMethods.GET);
		
		Route newRoutePOST = new Route("/test", HttpMethods.POST, TestInplementatonOfAction02.class);
		Route newRouteGET = new Route("/test", HttpMethods.GET, TestInplementatonOfAction01.class);
		RouteTree rt = new RouteTree("/testofmethod");
		rt.appendRoute(newRoutePOST);
		rt.appendRoute(newRouteGET);

		RoutesMapping.getGeneralRoot().appendRouteThree(rt);
	
		Route routePOST = RoutesMapping.getRoute(rprPOST);
		Route routeGET = RoutesMapping.getRoute(rprGET);
		
		Assert.assertEquals(routePOST.getActionClass(), TestInplementatonOfAction02.class);
		Assert.assertEquals(routeGET.getActionClass(), TestInplementatonOfAction01.class);
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
	}
	
	@Test
	public void shouldReturnDefaultRoute() throws RouteNotFoundException {
		String URI = "/library/tree";
		
		RequestedPathRoute rprDefault = new RequestedPathRoute(URI, HttpMethods.POST);
		RequestedPathRoute rprChild = new RequestedPathRoute(URI + "/child", HttpMethods.POST);
		
		Route defaultRoute = new Route(RoutesMapping.endingDefault, HttpMethods.POST, TestInplementatonOfAction02.class);
		Route childRoute = new Route("/child", HttpMethods.POST, TestInplementatonOfAction01.class);
		
		RouteTree rt = new RouteTree("/tree");
		
		rt.appendRoute(childRoute);
		rt.setDefaultRoute(HttpMethods.POST, TestInplementatonOfAction02.class);

		RoutesMapping.getGeneralRoot().appendRouteThree(rt);
	
		Route childReturned = RoutesMapping.getRoute(rprChild);
		Route defaultReturned = RoutesMapping.getRoute(rprDefault);
		
		Assert.assertEquals(childReturned.getActionClass(), TestInplementatonOfAction01.class);
		Assert.assertEquals(defaultReturned.getActionClass(), TestInplementatonOfAction02.class);
		
		RoutesMapping.getGeneralRoot().removeRouteTree(rt);
	}
	
	@Test
	public void shouldNotReturnAction() {
		
	}
}


class TestInplementatonOfAction01 implements Action{

	@Override
	public Message execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}

class TestInplementatonOfAction02 implements Action{

	@Override
	public Message execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
}
