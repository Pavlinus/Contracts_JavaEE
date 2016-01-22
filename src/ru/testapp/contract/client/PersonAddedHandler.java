package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface PersonAddedHandler extends EventHandler {
	void onPersonAdded(PersonAddedEvent event);
}
