package ru.testapp.contract.client.Activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.testapp.contract.client.ClientFactory;
import ru.testapp.contract.client.INewPerson;

/**
 * @author pavlin
 *
 * Defines new person activity
 */
public class NewPersonActivity extends AbstractActivity
	implements INewPerson.Presenter {
	
	private ClientFactory clientFactory;

	public NewPersonActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		INewPerson list = clientFactory.getNewPersonView();
		list.setPresenter(this);
		panel.setWidget(list.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
