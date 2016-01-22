package ru.testapp.contract.client.Activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.testapp.contract.client.ClientFactory;
import ru.testapp.contract.client.IStartWindow;

/**
 * @author pavlin
 * 
 * Defines start window activity
 * 
 */
public class StartWindowActivity extends AbstractActivity 
	implements IStartWindow.Presenter {
	
	private ClientFactory clientFactory;
	
	public StartWindowActivity(ClientFactory factory) {
		this.clientFactory = factory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IStartWindow startWindow = clientFactory.getStartWindowView();
		startWindow.setPresenter(this);
		panel.setWidget(startWindow.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
