package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import ru.testapp.contract.client.Views.ContractEditorView;
import ru.testapp.contract.client.Views.NewPersonView;
import ru.testapp.contract.client.Views.PersonEditorView;
import ru.testapp.contract.client.Views.PersonListView;
import ru.testapp.contract.client.Views.StartWindowView;

public class ClientFactoryImpl implements ClientFactory {
	private static final EventBus eventBus = new SimpleEventBus();
	@SuppressWarnings("deprecation")
	private final PlaceController placeController = new PlaceController(eventBus);
	private final IStartWindow startWindow = new StartWindowView();
	private final IContractEditor contractEditor = new ContractEditorView();
	private final IPersonList personListWindow = new PersonListView();
	private final INewPerson newPersonView = new NewPersonView();
	private final IEditPerson personEditor = new PersonEditorView();
	
	public static EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public IStartWindow getStartWindowView() {
		return startWindow;
	}

	@Override
	public IContractEditor getContractEditor() {
		return contractEditor;
	}

	@Override
	public IPersonList getPersonListView() {
		return personListWindow;
	}

	@Override
	public INewPerson getNewPersonView() {
		return newPersonView;
	}

	@Override
	public IEditPerson getPersonEditor() {
		return personEditor;
	}

}
