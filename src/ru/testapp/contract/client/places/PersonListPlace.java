package ru.testapp.contract.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author pavlin
 * 
 * Defines person list place
 * 
 */
public class PersonListPlace extends Place {
	private String name;
	
	public PersonListPlace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<PersonListPlace> {

		@Override
		public PersonListPlace getPlace(String token) {
			return new PersonListPlace(token);
		}

		@Override
		public String getToken(PersonListPlace place) {
			return place.getName();
		}
	}
}
