package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface ContractAddedHandler extends EventHandler {
	void onContractAdded(ContractAddedEvent event);
}
