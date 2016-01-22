package ru.testapp.contract.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author pavlin
 * 
 * Defines new person place
 * 
 */
public class NewPersonPlace extends Place {
	private String name;
	
	public NewPersonPlace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<NewPersonPlace> {

		@Override
		public NewPersonPlace getPlace(String token) {
			return new NewPersonPlace(token);
		}

		@Override
		public String getToken(NewPersonPlace place) {
			return place.getName();
		}
	}
}
