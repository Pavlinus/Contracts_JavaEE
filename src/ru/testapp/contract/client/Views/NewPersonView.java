package ru.testapp.contract.client.Views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import ru.testapp.contract.client.ClientFactoryImpl;
import ru.testapp.contract.client.EditDataService;
import ru.testapp.contract.client.EditDataServiceAsync;
import ru.testapp.contract.client.INewPerson;
import ru.testapp.contract.client.PersonAddedEvent;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.places.PersonListPlace;

/**
 * @author pavlin
 * 
 * Defines new person view
 * 
 */
public class NewPersonView extends Composite implements INewPerson {
	private static NewPersonViewUiBinder uiBinder = GWT.create(NewPersonViewUiBinder.class);
	private Presenter presenter;
	private DateTimeFormat dtf;
	private EventBus eventBus;
	private PersonDTO personDTO;
	
	@UiField TextBox surname;
	@UiField TextBox name;
	@UiField TextBox secondName;
	@UiField(provided=true) DateBox birthDate;
	@UiField Button save;
	@UiField Button cancel;

	interface NewPersonViewUiBinder extends UiBinder<Widget, NewPersonView> {
	}

	public NewPersonView() {
		dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		DateBox.DefaultFormat df = new DateBox.DefaultFormat(dtf);
		
		birthDate = new DateBox();
		birthDate.setFormat(df);
		
		eventBus = ClientFactoryImpl.getEventBus();
		
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * Handling save button click event
	 * @param event save button clicked
	 */
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		if(validateFields()) {
			personDTO = new PersonDTO();
			StringBuilder fioBuilder = new StringBuilder();
			
			fioBuilder.append(name.getValue());
			fioBuilder.append(" ");
			fioBuilder.append(surname.getValue());
			fioBuilder.append(" ");
			fioBuilder.append(secondName.getValue());
			
			personDTO.setFio(fioBuilder.toString());
			personDTO.setBirthDate(birthDate.getValue());
			
			// add person RPC
			addPerson(personDTO);
		}
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		presenter.goTo(new PersonListPlace("personList"));
	}
	
	/**
	 * Validate fields data
	 * @return true if data is valid and false otherwise
	 */
	boolean validateFields() {
		RegExp regExp = null;
		String symbols = "^[a-zA-Z]+$";
		
		regExp = RegExp.compile(symbols);
		
		if(regExp.exec(name.getValue().trim()) == null) {
			Window.alert("Поле Имя пусто или содержит неверные символы");
			return false;
		}
		
		if(regExp.exec(surname.getValue().trim()) == null) {
			Window.alert("Поле Фамилия пусто или содержит неверные символы");
			return false;
		}
		
		if(regExp.exec(secondName.getValue().trim()) == null) {
			Window.alert("Поле Отчество пусто или содержит неверные символы");
			return false;
		}
		
		if(birthDate.getValue() == null) {
			Window.alert("Не задана дата рождения");
			return false;
		}
		
		return true;
	}
	
	/**
	 * RPC for adding new person
	 * @param person data transfer object
	 */
	void addPerson(PersonDTO person) {
		EditDataServiceAsync rpc = GWT.create(EditDataService.class);
		
		rpc.addPerson(person, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось сохранить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Integer result) {
				Window.alert("Данные клиента сохранены успешно");
				personDTO.setId(result);
				eventBus.fireEvent(new PersonAddedEvent(personDTO));
				presenter.goTo(new PersonListPlace("personList"));
			}
		});
	}
}
