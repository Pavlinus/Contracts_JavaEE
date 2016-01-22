package ru.testapp.contract.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ru.testapp.contract.client.ContractTableItem;
import ru.testapp.contract.client.LoadDataService;
import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.AreaCoefficientDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.dto.RealtyTypeDTO;
import ru.testapp.contract.client.dto.YearCoefficientDTO;

public class LoadDataServiceImpl extends RemoteServiceServlet 
	implements LoadDataService {

	private static final long serialVersionUID = 300L;

	@Override
	public ArrayList<PersonDTO> listPerson() {
		ArrayList<PersonDTO> result = new ArrayList<PersonDTO>();
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT e FROM Person e";
		Query query = entityManager.createQuery(jpql, Person.class);
		
		@SuppressWarnings("unchecked")
		List<Person> list = (List<Person>) query.getResultList();
		
		for(Person val : list) {
			result.add(initPersonDTO(val));
		}
		
		entityManager.close();
		
		return result;
	}

	@Override
	public ArrayList<ContractTableItem> listContractPerson() {
		ArrayList<ContractDTO> contractList = listContract();
		ArrayList<PersonDTO> personList = listPerson();
		ArrayList<ContractTableItem> tableItems = new ArrayList<ContractTableItem>();
		
		// loop over contracts list
		for(ContractDTO contract : contractList) {
			PersonDTO person = null;
			
			// find corresponding person by id
			for(PersonDTO item : personList) {
				if(item.getId() == contract.getPerson_id()) {
					person = item;
					break;
				}
			}
			
			if(contract != null && person != null) {
				ContractTableItem item = initContractTableItem(contract, person);
				tableItems.add(item);
			}
		}
		
		return tableItems;
	}

	/* (non-Javadoc)
	 * @see ru.testapp.contract.client.LoadDataService#listYearCoefficient()
	 */
	@Override
	public ArrayList<YearCoefficientDTO> listYearCoefficient() {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT e FROM YearCoefficient e ORDER BY e.year ASC";
		Query query = entityManager.createQuery(jpql, YearCoefficient.class);
		ArrayList<YearCoefficientDTO> result = new ArrayList<YearCoefficientDTO>();
		
		@SuppressWarnings("unchecked")
		List<YearCoefficient> list = (List<YearCoefficient>) query.getResultList();
		
		for(YearCoefficient val : list) {
			YearCoefficientDTO dto = new YearCoefficientDTO();
			dto.setId(val.getId());
			dto.setYear(val.getYear());
			dto.setCoefficient(val.getCoefficient());
			result.add(dto);
		}
		
		entityManager.close();
		
		return result;
	}


	@Override
	public ArrayList<AreaCoefficientDTO> listAreaCoefficient() {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT e FROM AreaCoefficient e ORDER BY e.size ASC";
		Query query = entityManager.createQuery(jpql, AreaCoefficient.class);
		ArrayList<AreaCoefficientDTO> result = new ArrayList<AreaCoefficientDTO>();
		
		@SuppressWarnings("unchecked")
		List<AreaCoefficient> list = (List<AreaCoefficient>) query.getResultList();
		
		for(AreaCoefficient val : list) {
			AreaCoefficientDTO dto = new AreaCoefficientDTO();
			dto.setId(val.getId());
			dto.setSize(val.getSize());
			dto.setCoefficient(val.getCoefficient());
			result.add(dto);
		}
		
		entityManager.close();
		
		return result;
	}


	@Override
	public ArrayList<RealtyTypeDTO> listRealtyCoefficient() {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT e FROM RealtyCoefficient e";
		Query query = entityManager.createQuery(jpql, RealtyCoefficient.class);
		ArrayList<RealtyTypeDTO> result = new ArrayList<RealtyTypeDTO>();
		
		@SuppressWarnings("unchecked")
		List<RealtyCoefficient> list = (List<RealtyCoefficient>) query.getResultList();
		
		for(RealtyCoefficient val : list) {
			RealtyTypeDTO dto = new RealtyTypeDTO();
			dto.setId(val.getId());
			dto.setName(val.getName());
			dto.setCoefficient(val.getCoefficient());
			result.add(dto);
		}
		
		entityManager.close();
		
		return result;
	}

	/**
	 * Get initialized entity manager
	 * @return entity manager object
	 */
	private EntityManager getEntityManager() {
		EntityManagerFactory entityManagerFactory = 
				Persistence.createEntityManagerFactory("db-persist");
		return entityManagerFactory.createEntityManager();
	}

	/**
	 * Initialize PersonDTO object by person data
	 * @param person object received from DB
	 * @return PersonDTO object
	 */
	private PersonDTO initPersonDTO(Person person) {
		PersonDTO dto = new PersonDTO();
		 
		if(person != null) {
			dto.setId(person.getId());
			dto.setFio(person.getFio());
			dto.setBirthDate(person.getBirthDate());
			dto.setPassportNumber(person.getPassportNumber());
			dto.setPassportSerial(person.getPassportSerial());
		}
		
		return dto;
	}

	/**
	 * Initialize AddressDTO object by address data
	 * @param address address object received from DB
	 * @return AddressDTO object
	 */
	private AddressDTO initAddressDTO(Address address) {
		AddressDTO dto = new AddressDTO();
		
		if(address != null) {
			dto.setId(address.getId());
			dto.setBuilding(address.getBuilding());
			dto.setFlat(address.getFlat());
			dto.setHouse(address.getHouse());
			dto.setHousing(address.getHousing());
			dto.setLocality(address.getLocality());
			dto.setPostIndex(address.getPostIndex());
			dto.setRegion(address.getRegion());
			dto.setRepublic(address.getRepublic());
			dto.setState(address.getState());
			dto.setStreet(address.getStreet());
		}
		
		return dto;
	}
	
	/**
	 * Initialize ContractDTO object by contract data
	 * @param contract object received from DB
	 * @return ContractDTO object
	 */
	private ContractDTO initContractDTO(Contract contract) {
		ContractDTO dto = new ContractDTO();
		
		if(contract != null) {
			dto.setAddress_id(contract.getAddress_id());
			dto.setArea(contract.getArea());
			dto.setBonus(contract.getBonus());
			dto.setBuiltYear(contract.getBuiltYear());
			dto.setCalculationDate(contract.getCalculationDate());
			dto.setComment(contract.getComment());
			dto.setContractDate(contract.getCalculationDate());
			dto.setContractNumber(contract.getContractNumber());
			dto.setDateSince(contract.getDateSince());
			dto.setDateTo(contract.getDateTo());
			dto.setId(contract.getId());
			dto.setInsuranceSum(contract.getInsuranceSum());
			dto.setPerson_id(contract.getPerson_id());
			dto.setRealty_id(contract.getRealty_id());
		}
		
		return dto;
	}
	
	/**
	 * Init ContractTableItem object by contract and person data
	 * @param contract contract DTO object
	 * @param person person DTO object
	 * @return
	 */
	private ContractTableItem initContractTableItem(ContractDTO contract, 
			PersonDTO person) {
		ContractTableItem item = new ContractTableItem();
		
		item.setBonus(Float.parseFloat(contract.getBonus()));
		item.setContractNumber(contract.getContractNumber());
		item.setId(contract.getId());
		item.setPersonName(person.getFio());
		item.setContractDate(contract.getContractDate());
		item.setSince(contract.getDateSince());
		item.setTo(contract.getDateTo());
		
		return item;
	}

	@Override
	public PersonDTO getPerson(int personId) {
		EntityManager em = getEntityManager();
		Person person = em.find(Person.class, personId);
		
		return initPersonDTO(person);
	}

	@Override
	public AddressDTO getAddress(int addressId) {
		EntityManager em = getEntityManager();
		Address address = em.find(Address.class, addressId);
		
		em.close();
		
		return initAddressDTO(address);
	}

	@Override
	public ContractDTO getContract(int contractId) {
		EntityManager em = getEntityManager();
		Contract contract = em.find(Contract.class, contractId);
		
		return initContractDTO(contract);
	}

	@Override
	public ArrayList<ContractDTO> listContract() {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT e FROM Contract e";
		Query query = entityManager.createQuery(jpql, Contract.class);
		
		@SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>)query.getResultList();
		ArrayList<ContractDTO> result = new ArrayList<ContractDTO>();
		
		for(Contract item : list) {
			result.add(initContractDTO(item));
		}
		
		entityManager.close();
		
		return result;
	}

	@Override
	public Integer getFreeContractNumber() {
		EntityManager entityManager = getEntityManager();
		String jpql = "SELECT MAX(e.contractNumber) FROM Contract e";
		Query query = entityManager.createQuery(jpql, Integer.class);
		Integer result = (Integer)query.getSingleResult();
		
		entityManager.close();
		
		return result + 1;
	}
}
