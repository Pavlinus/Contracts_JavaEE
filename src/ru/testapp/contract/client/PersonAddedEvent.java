package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;

import ru.testapp.contract.client.dto.PersonDTO;

/**
 * @author pavlin
 *
 * Used when new person was added
 * 
 */
public class PersonAddedEvent extends GwtEvent<PersonAddedHandler> {
	public static Type<PersonAddedHandler> TYPE = 
			new Type<PersonAddedHandler>(); 
	
	private PersonDTO person;
	
	public PersonAddedEvent(PersonDTO person) {
		this.setPerson(person);
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PersonAddedHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PersonAddedHandler handler) {
		handler.onPersonAdded(this);
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

}
