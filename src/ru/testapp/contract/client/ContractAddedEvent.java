package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author pavlin
 *
 * When new contracts add to DB this event fires
 * 
 */
public class ContractAddedEvent extends GwtEvent<ContractAddedHandler> {
	public static Type<ContractAddedHandler> TYPE = 
			new Type<ContractAddedHandler>(); 
	private ContractTableItem contractItem;
	
	public ContractAddedEvent(ContractTableItem contractItem) {
		this.contractItem = contractItem;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContractAddedHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContractAddedHandler handler) {
		handler.onContractAdded(this);
	}

	public ContractTableItem getContractItem() {
		return contractItem;
	}

	public void setContractItem(ContractTableItem contractItem) {
		this.contractItem = contractItem;
	}


}
