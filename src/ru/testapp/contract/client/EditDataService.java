package ru.testapp.contract.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;

/**
 * @author pavlin
 *
 * Interface for remote service communication with DB.
 * The aim is remote data changing.
 * 
 */
@RemoteServiceRelativePath("editor")
public interface EditDataService extends RemoteService {
	int addPerson(PersonDTO person);
	int addContract(ContractDTO contract);
	int addAddress(AddressDTO address);
	void updatePerson(PersonDTO person);
	void updateContract(ContractDTO contract);
	void updateAddress(AddressDTO address);
}
