package ru.testapp.contract.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.AreaCoefficientDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.dto.RealtyTypeDTO;
import ru.testapp.contract.client.dto.YearCoefficientDTO;

public interface LoadDataServiceAsync {
	void listPerson(AsyncCallback<ArrayList<PersonDTO>> list);
	void listContract(AsyncCallback<ArrayList<ContractDTO>> list);
	void listContractPerson(AsyncCallback<ArrayList<ContractTableItem>> list);
	void listYearCoefficient(AsyncCallback<ArrayList<YearCoefficientDTO>> coeff);
	void listAreaCoefficient(AsyncCallback<ArrayList<AreaCoefficientDTO>> coeff);
	void listRealtyCoefficient(AsyncCallback<ArrayList<RealtyTypeDTO>> coeff);
	void getPerson(int personId, AsyncCallback<PersonDTO> person);
	void getAddress(int addressId, AsyncCallback<AddressDTO> address);
	void getContract(int contractId, AsyncCallback<ContractDTO> contract);
	void getFreeContractNumber(AsyncCallback<Integer> contractNumber);
}
