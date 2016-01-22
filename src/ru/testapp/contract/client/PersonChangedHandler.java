package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface PersonChangedHandler extends EventHandler {
	void onPersonChanged(PersonChangedEvent event);
}
