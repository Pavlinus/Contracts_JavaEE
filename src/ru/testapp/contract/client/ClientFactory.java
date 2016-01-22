package ru.testapp.contract.client;

import com.google.gwt.place.shared.PlaceController;

public interface ClientFactory {
	PlaceController getPlaceController();
	IStartWindow getStartWindowView();
	IContractEditor getContractEditor();
	IPersonList getPersonListView();
	INewPerson getNewPersonView();
	IEditPerson getPersonEditor();
}
