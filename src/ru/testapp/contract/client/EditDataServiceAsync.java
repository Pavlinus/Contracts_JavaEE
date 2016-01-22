package ru.testapp.contract.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;

public interface EditDataServiceAsync {
	void addPerson(PersonDTO person, AsyncCallback<Integer> callback);
	void addContract(ContractDTO person, AsyncCallback<Integer> callback);
	void addAddress(AddressDTO person, AsyncCallback<Integer> callback);
	void updatePerson(PersonDTO person, AsyncCallback<Void> callback);
	void updateContract(ContractDTO person, AsyncCallback<Void> callback);
	void updateAddress(AddressDTO person, AsyncCallback<Void> callback);
}
