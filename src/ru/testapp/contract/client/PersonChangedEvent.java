package ru.testapp.contract.client;

import com.google.gwt.event.shared.GwtEvent;

import ru.testapp.contract.client.dto.PersonDTO;

/**
 * @author pavlin
 *
 * Used when person was changed
 * 
 */
public class PersonChangedEvent extends GwtEvent<PersonChangedHandler> {
	public static Type<PersonChangedHandler> TYPE = 
			new Type<PersonChangedHandler>(); 
	private PersonDTO person;
	
	public PersonChangedEvent(PersonDTO person) {
		this.person = person;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<PersonChangedHandler> 
		getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(PersonChangedHandler handler) {
		handler.onPersonChanged(this);
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

}
