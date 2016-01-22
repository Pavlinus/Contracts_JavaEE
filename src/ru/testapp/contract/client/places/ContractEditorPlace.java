package ru.testapp.contract.client.places;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * @author pavlin
 * 
 * Defines contract editor place
 * 
 */
public class ContractEditorPlace extends Place {
	private String name;
	
	public ContractEditorPlace(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static class Tokenizer implements PlaceTokenizer<ContractEditorPlace> {

		@Override
		public ContractEditorPlace getPlace(String token) {
			return new ContractEditorPlace(token);
		}

		@Override
		public String getToken(ContractEditorPlace place) {
			return place.getName();
		}
	}
}
