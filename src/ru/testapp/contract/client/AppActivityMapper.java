package ru.testapp.contract.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import ru.testapp.contract.client.Activities.ContractEditorActivity;
import ru.testapp.contract.client.Activities.NewPersonActivity;
import ru.testapp.contract.client.Activities.PersonEditorActivity;
import ru.testapp.contract.client.Activities.PersonListActivity;
import ru.testapp.contract.client.Activities.StartWindowActivity;
import ru.testapp.contract.client.places.ContractEditorPlace;
import ru.testapp.contract.client.places.NewPersonPlace;
import ru.testapp.contract.client.places.PersonEditorPlace;
import ru.testapp.contract.client.places.PersonListPlace;
import ru.testapp.contract.client.places.StartWindowPlace;

/**
 * @author pavlin
 * 
 * Class mapping Place with corresponding Activity
 *  
 */
public class AppActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;
	
	public AppActivityMapper(ClientFactory factory) {
		super();
		clientFactory = factory;
	}
	
	@Override
	public Activity getActivity(Place place) {
		if(place instanceof StartWindowPlace) {
			return new StartWindowActivity(clientFactory);
		} else if(place instanceof ContractEditorPlace) {
			return new ContractEditorActivity(clientFactory);
		} else if(place instanceof PersonListPlace) {
			return new PersonListActivity(clientFactory);
		} else if(place instanceof NewPersonPlace) {
			return new NewPersonActivity(clientFactory);
		} else if(place instanceof PersonEditorPlace) {
			return new PersonEditorActivity(clientFactory);
		}
		
		return null;
	}

}
