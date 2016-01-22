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
import ru.testapp.contract.client.IEditPerson;
import ru.testapp.contract.client.PersonChangedEvent;
import ru.testapp.contract.client.PersonEditEvent;
import ru.testapp.contract.client.PersonEditHandler;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.places.ContractEditorPlace;

/**
 * @author pavlin
 * 
 * Defines person editor view
 * 
 */
public class PersonEditorView extends Composite implements IEditPerson {

	private static PersonEditorViewUiBinder uiBinder = GWT.create(PersonEditorViewUiBinder.class);
	private Presenter presenter;
	private DateTimeFormat dtf;
	private EventBus eventBus;
	private PersonDTO person;
	
	@UiField TextBox surname;
	@UiField TextBox name;
	@UiField TextBox secondName;
	@UiField(provided=true) DateBox birthDate;
	@UiField TextBox serial;
	@UiField TextBox number;
	@UiField Button save;
	@UiField Button cancel;

	interface PersonEditorViewUiBinder extends UiBinder<Widget, PersonEditorView> {}

	public PersonEditorView() {
		dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		DateBox.DefaultFormat df = new DateBox.DefaultFormat(dtf);
		
		birthDate = new DateBox();
		birthDate.setFormat(df);
		
		eventBus = ClientFactoryImpl.getEventBus();
		
		// handler for getting person DTO when event fires
		eventBus.addHandler(PersonEditEvent.TYPE, new PersonEditHandler() {

			@Override
			public void onPersonEdit(PersonEditEvent event) {
				person = event.getPerson();
				
				String[] personName = person.getFio().split(" ");
				
				name.setValue(personName[0]);
				surname.setValue(personName[1]);
				secondName.setValue(personName[2]);
				birthDate.setValue(person.getBirthDate());
				serial.setValue(String.valueOf(person.getPassportSerial()));
				number.setValue(String.valueOf(person.getPassportNumber()));
			}
		});
		
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	/**
	 * Handling save button click event
	 * @param event save button click
	 */
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		if(validateFields()) {
			StringBuilder fioBuilder = new StringBuilder();
			
			fioBuilder.append(name.getValue());
			fioBuilder.append(" ");
			fioBuilder.append(surname.getValue());
			fioBuilder.append(" ");
			fioBuilder.append(secondName.getValue());
			
			person.setFio(fioBuilder.toString());
			person.setBirthDate(birthDate.getValue());
			person.setPassportNumber(Integer.parseInt(number.getValue()));
			person.setPassportSerial(Integer.parseInt(serial.getValue()));
			
			// update person RPC
			updatePerson();
		}
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		presenter.goTo(new ContractEditorPlace("contractEditor"));
	}

	/**
	 * Validates data fields
	 * @return true if fields are valid and false otherwise
	 */
	boolean validateFields() {
		RegExp regExp = null;
		String symbols = "^[a-zA-Z]+$";
		String regSerial = "^[0-9]{4}$";
		String regNumber = "^[0-9]{6}$";
		
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
		
		regExp = RegExp.compile(regSerial);
		
		if(regExp.exec(String.valueOf(serial.getValue())) == null) {
			Window.alert("Серия паспорта введена некорректно");
			return false;
		}
		
		regExp = RegExp.compile(regNumber);
		
		if(regExp.exec(String.valueOf(number.getValue())) == null) {
			Window.alert("Номер паспорта введен некорректно");
			return false;
		}
		
		return true;
	}

	/**
	 * Updates person remotely
	 */
	void updatePerson() {
		EditDataServiceAsync rpc = GWT.create(EditDataService.class);
		
		rpc.updatePerson(person, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось сохранить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("Данные клиента сохранены успешно");
				eventBus.fireEvent(new PersonChangedEvent(person));
				presenter.goTo(new ContractEditorPlace("contractEditor"));
			}
		});
	}
}
