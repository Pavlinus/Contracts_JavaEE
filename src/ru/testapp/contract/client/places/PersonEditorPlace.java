package ru.testapp.contract.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author pavlin
 * 
 * Defines person editor place
 * 
 */
public class PersonEditorPlace extends Place {
	private String name;
	
	public PersonEditorPlace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<PersonEditorPlace> {

		@Override
		public PersonEditorPlace getPlace(String token) {
			return new PersonEditorPlace(token);
		}

		@Override
		public String getToken(PersonEditorPlace place) {
			return place.getName();
		}
	}
}
