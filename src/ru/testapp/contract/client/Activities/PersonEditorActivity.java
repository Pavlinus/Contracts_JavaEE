package ru.testapp.contract.client.Activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.testapp.contract.client.ClientFactory;
import ru.testapp.contract.client.IEditPerson;

/**
 * @author pavlin
 *
 * Defines person editor activity
 * 
 */
public class PersonEditorActivity extends AbstractActivity
	implements IEditPerson.Presenter {
	
	private ClientFactory clientFactory;

	public PersonEditorActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IEditPerson list = clientFactory.getPersonEditor();
		list.setPresenter(this);
		panel.setWidget(list.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
