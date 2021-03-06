package rest;

import classes.RouteList;
import classes.StopList;
import classes.StopTimeList;
import classes.TripList;

import com.google.gson.*;

import enumerations.JSONClasses;

public class JSONParsing {

	public static Object parsed(String toParse, JSONClasses classe) {
		Gson gson = new GsonBuilder().create();
		
		if (toParse == null || checkErrors(toParse))
			return null;
				
		StopList stops = null;
		RouteList routes = null;
		StopTimeList times = null;
		TripList trips = null;
		
		try {
			if (classe == JSONClasses.Stop) {
				stops = gson.fromJson(toParse, StopList.class);
				return stops;
			} else if (classe == JSONClasses.Routes) {
				routes = gson.fromJson(toParse, RouteList.class);
				return routes;
			} else if (classe == JSONClasses.StopTime) {
				times = gson.fromJson(toParse, StopTimeList.class);
				return times;
			} else if (classe == JSONClasses.Trips) {
				trips = gson.fromJson(toParse, TripList.class);
				return trips;
			} else {
				stops = null;
			}
		} catch (JsonSyntaxException e) {
			System.err.println("Tem que colocar a chave da OST no ficheiro Constants na package utils.");
		}
		return stops;
	}

	private static boolean checkErrors(String toParse) {
		
		String ostError = "O servidor OST devolveu a resposta: ", endError = ".\nTente novamente mais tarde.";
		
		String err403 = "You don't have permission to access this resource",
				err404 = "The page you are looking for doesn't exist",
				err500 = "We are sorry, but something went wrong",
				err502 = "Bad Gateway",
				err503 = "We are currently performing scheduled maintenance";
		
		if (toParse.contains(err403)) {
			System.err.println(ostError+err403+endError);
			return true;
		} else if (toParse.contains(err404)) {
			System.err.println(ostError+err404+endError);
			return true;
		} else if (toParse.contains(err500)) {	
			System.err.println(ostError+err500+endError);
			return true;
		} else if (toParse.contains(err502)) {	
			System.err.println(ostError+err502+endError);
			return true;
		} else if (toParse.contains(err503)) {	
			System.err.println(ostError+err503+endError);
			return true;
		} 
		
		return false;
	}
	
}
