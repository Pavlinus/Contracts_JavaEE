package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface PersonEditHandler extends EventHandler {
	void onPersonEdit(PersonEditEvent event);
}
