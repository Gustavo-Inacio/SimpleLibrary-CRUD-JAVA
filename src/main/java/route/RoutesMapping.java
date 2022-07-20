package route;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import service.action.Action;
import util.Exceptions.RouteNotFoundException;

public class RoutesMapping {
	private static RouteTree generalRoot;
	public static final String endingDefault = "/index";

	static {
		generalRoot = new RouteTree("/");
	}

	public static Action getAction(RequestedPathRoute rpr) throws RouteNotFoundException, UnexpectedException {
		Class<? extends Action> actionClass = RoutesMapping.getRoute(rpr).getActionClass();

		Action action;
		try {
			action = actionClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new UnexpectedException("Something went wrong on getting your route.");
		}

		return action;

	}

	public static Route getRoute(RequestedPathRoute rpr) throws RouteNotFoundException {
		String rootName = "/library";
		String path = rpr.getURI().replace(rootName, "");

		String[] routing = path.split("(?=/)");

		Route route = null;

		int lastRoutePos = routing.length - 1;
		RouteTree lastRouteTree = RoutesMapping.generalRoot;

		for (int i = 0; i < routing.length; i++) {
			String actualRoutePath = routing[i];

			if (i != lastRoutePos) { // if this path is not the last one, then go to the next route
				lastRouteTree = lastRouteTree.getRouteTree(actualRoutePath);
			} else { // if this is the last route informed, it means that or there is a class to be executed, or the default route
				if (lastRouteTree.hasChildPath(actualRoutePath, rpr.getMethod())) { // if the actual route is in the routes																					
					route = lastRouteTree.getRoute(actualRoutePath, rpr.getMethod());
				} else { // if this class is not in the routesTree, then tries to access a tree's default root
					Route defaultRoute = lastRouteTree.getRouteTree(actualRoutePath).getDefaultRoute(rpr.getMethod());
					route = defaultRoute;
				}
			}
		}

		return route;

	}

	public static RouteTree getGeneralRoot() {
		return RoutesMapping.generalRoot;
	}
}
