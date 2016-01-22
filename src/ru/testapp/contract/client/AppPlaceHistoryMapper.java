package ru.testapp.contract.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import ru.testapp.contract.client.places.ContractEditorPlace;
import ru.testapp.contract.client.places.NewPersonPlace;
import ru.testapp.contract.client.places.PersonEditorPlace;
import ru.testapp.contract.client.places.PersonListPlace;
import ru.testapp.contract.client.places.StartWindowPlace;

@WithTokenizers({
	StartWindowPlace.Tokenizer.class, 
	ContractEditorPlace.Tokenizer.class,
	PersonListPlace.Tokenizer.class,
	NewPersonPlace.Tokenizer.class,
	PersonEditorPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
	
}
