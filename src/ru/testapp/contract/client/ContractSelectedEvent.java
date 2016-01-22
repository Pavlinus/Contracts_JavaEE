package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author pavlin
 *
 * Used to send contract identifier to contract editor activity
 * 
 */
public class ContractSelectedEvent extends GwtEvent<ContractSelectedHandler> {
	public static Type<ContractSelectedHandler> TYPE = 
			new Type<ContractSelectedHandler>();
	private int contractId;
	
	public ContractSelectedEvent(int contractId) {
		this.setContractId(contractId);
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ContractSelectedHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ContractSelectedHandler handler) {
		handler.onContractSelected(this);
	}

	public int getContractId() {
		return contractId;
	}

	public void setContractId(int contractId) {
		this.contractId = contractId;
	}

}
