package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author pavlin
 *
 * When contracts change this event fires
 * 
 */
public class ContractChangedEvent extends GwtEvent<ContractChangedHandler> {
	public static Type<ContractChangedHandler> TYPE = 
			new Type<ContractChangedHandler>(); 
	private ContractTableItem contractItem;
	
	public ContractChangedEvent(ContractTableItem contractItem) {
		this.contractItem = contractItem;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContractChangedHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContractChangedHandler handler) {
		handler.onContractChanged(this);
	}

	public ContractTableItem getContractItem() {
		return contractItem;
	}

	public void setContractItem(ContractTableItem contractItem) {
		this.contractItem = contractItem;
	}


}
