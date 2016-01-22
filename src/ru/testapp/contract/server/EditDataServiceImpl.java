package ru.testapp.contract.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import ru.testapp.contract.client.EditDataService;
import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;

/**
 * @author pavlin
 * 
 * Edit service RPC implementation
 */
public class EditDataServiceImpl extends RemoteServiceServlet
	implements EditDataService {

	private static final long serialVersionUID = 200L;

	@Override
	public int addPerson(PersonDTO personDTO) {
		Person person = new Person(personDTO);
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(person);
		entityManager.getTransaction().commit();
		
		String jpql = "SELECT e FROM Person e "
				+ "WHERE e.fio=:fio AND e.passportNumber=:num "
				+ "AND e.passportSerial=:serial";
		Query query = entityManager.createQuery(jpql, Person.class);
		query.setParameter("fio", person.getFio());
		query.setParameter("num", person.getPassportNumber());
		query.setParameter("serial", person.getPassportSerial());
		
		@SuppressWarnings("unchecked")
		List<Person> list = (List<Person>)query.getResultList();
		int newPersonId = -1;
		
		for(Person rec : list) {
			newPersonId = rec.getId();
		}
		
		entityManager.close();
		
		return newPersonId;
	}

	@Override
	public int addContract(ContractDTO contractDTO) {
		Contract contract = new Contract(contractDTO);
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(contract);
		entityManager.getTransaction().commit();
		
		String jpql = "SELECT e FROM Contract e WHERE e.contractNumber=:num";
		Query query = entityManager.createQuery(jpql, Contract.class);
		query.setParameter("num", contract.getContractNumber());
		
		@SuppressWarnings("unchecked")
		List<Contract> list = (List<Contract>)query.getResultList();
		int newContractId = -1;
		
		for(Contract rec : list) {
			newContractId = rec.getId();
		}
		
		entityManager.close();
		
		return newContractId;
	}

	@Override
	public int addAddress(AddressDTO addressDTO) {
		Address address = new Address(addressDTO);
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(address);
		entityManager.getTransaction().commit();
		
		String jpql = "SELECT e FROM Address e "
				+ "WHERE e.flat=:flat AND e.locality=:loc AND e.republic=:rep "
				+ "AND e.state=:state AND e.street=:street AND e.region=:region "
				+ "AND e.house=:house";
		Query query = entityManager.createQuery(jpql, Address.class);
		query.setParameter("flat", address.getFlat());
		query.setParameter("loc", address.getLocality());
		query.setParameter("rep", address.getRepublic());
		query.setParameter("state", address.getState());
		query.setParameter("street", address.getStreet());
		query.setParameter("region", address.getRegion());
		query.setParameter("house", address.getHouse());
		
		@SuppressWarnings("unchecked")
		List<Address> list = (List<Address>)query.getResultList();
		int newAddressId = -1;
		
		for(Address addr : list) {
			newAddressId = addr.getId();
			break;
		}
		
		entityManager.close();
		
		return newAddressId;
	}

	@Override
	public void updatePerson(PersonDTO personDTO) {
		EntityManager entityManager = getEntityManager();
		Person person = entityManager.find(Person.class, personDTO.getId());
		
		entityManager.getTransaction().begin();
		person.initFieldsByDTO(personDTO);
		entityManager.getTransaction().commit();
	}

	@Override
	public void updateContract(ContractDTO contractDTO) {
		EntityManager entityManager = getEntityManager();
		Contract contract = entityManager.find(Contract.class, contractDTO.getId());
		
		entityManager.getTransaction().begin();
		contract.initFieldsByDTO(contractDTO);
		entityManager.getTransaction().commit();
	}

	@Override
	public void updateAddress(AddressDTO dto) {
		EntityManager entityManager = getEntityManager();
		Address address = entityManager.find(Address.class, dto.getId());
		
		entityManager.getTransaction().begin();
		address.initFieldsByDTO(dto);
		entityManager.getTransaction().commit();
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
	
}
