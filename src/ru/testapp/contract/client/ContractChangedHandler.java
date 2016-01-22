package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface ContractChangedHandler extends EventHandler {
	void onContractChanged(ContractChangedEvent event);
}
