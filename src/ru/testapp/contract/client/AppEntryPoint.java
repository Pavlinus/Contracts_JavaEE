package ru.testapp.contract.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import ru.testapp.contract.client.places.StartWindowPlace;

/**
 * @author pavlin
 * 
 * Application's entry point
 * 
 */
public class AppEntryPoint implements EntryPoint {
	private SimplePanel appWidget = new SimplePanel();
	private Place defaultPlace = new StartWindowPlace("start");
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = ClientFactoryImpl.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		AppActivityMapper appMapper = new AppActivityMapper(clientFactory);
		ActivityManager manager = new ActivityManager(appMapper, eventBus);
		manager.setDisplay(appWidget);
		
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		RootPanel.get().add(appWidget);
		historyHandler.handleCurrentHistory();
	}
}
