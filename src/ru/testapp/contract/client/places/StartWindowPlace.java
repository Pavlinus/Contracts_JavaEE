package ru.testapp.contract.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author pavlin
 * 
 * Defines start window place
 * 
 */
public class StartWindowPlace extends Place {
	private String name;
	
	public StartWindowPlace(String placeName) {
		name = placeName;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<StartWindowPlace> {

		@Override
		public StartWindowPlace getPlace(String token) {
			return new StartWindowPlace(token);
		}

		@Override
		public String getToken(StartWindowPlace place) {
			return place.getName();
		}
		
	}
}
