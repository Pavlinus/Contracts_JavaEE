package ru.testapp.contract.client.Activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.testapp.contract.client.ClientFactory;
import ru.testapp.contract.client.IPersonList;

/**
 * @author pavlin
 *
 * Defines person list activity
 * 
 */
public class PersonListActivity extends AbstractActivity
	implements IPersonList.Presenter {
	
	private ClientFactory clientFactory;

	public PersonListActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IPersonList list = clientFactory.getPersonListView();
		list.setPresenter(this);
		panel.setWidget(list.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
}
