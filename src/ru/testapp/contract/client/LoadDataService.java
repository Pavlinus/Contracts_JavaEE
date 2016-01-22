package ru.testapp.contract.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.AreaCoefficientDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.dto.RealtyTypeDTO;
import ru.testapp.contract.client.dto.YearCoefficientDTO;

/**
 * @author pavlin
 *
 * Interface for loading data from remote DB
 * 
 */
@RemoteServiceRelativePath("loaddata")
public interface LoadDataService extends RemoteService {
	ArrayList<PersonDTO> listPerson();
	ArrayList<ContractDTO> listContract();
	ArrayList<ContractTableItem> listContractPerson();
	ArrayList<YearCoefficientDTO> listYearCoefficient();
	ArrayList<AreaCoefficientDTO> listAreaCoefficient();
	ArrayList<RealtyTypeDTO> listRealtyCoefficient();
	PersonDTO getPerson(int personId);
	AddressDTO getAddress(int addressId);
	ContractDTO getContract(int contractId);
	Integer getFreeContractNumber();
}
