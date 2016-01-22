package ru.testapp.contract.client;

import com.google.gwt.event.shared.EventHandler;

public interface ContractSelectedHandler extends EventHandler {
	void onContractSelected(ContractSelectedEvent event);
}
