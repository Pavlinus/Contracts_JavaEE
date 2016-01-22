package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;

import ru.testapp.contract.client.dto.PersonDTO;


/**
 * @author pavlin
 * 
 * Class used to send to person editor the person
 * object instance
 */
public class PersonEditEvent extends GwtEvent<PersonEditHandler> {
	public static Type<PersonEditHandler> TYPE = 
			new Type<PersonEditHandler>(); 
	private PersonDTO person;
	
	public PersonEditEvent(PersonDTO person) {
		this.person = person;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PersonEditHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PersonEditHandler handler) {
		handler.onPersonEdit(this);
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

}
