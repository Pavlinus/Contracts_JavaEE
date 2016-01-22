package ru.testapp.contract.client.Views;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.gwt.user.datepicker.client.DateBox;

import ru.testapp.contract.client.ClientFactoryImpl;
import ru.testapp.contract.client.ContractAddedEvent;
import ru.testapp.contract.client.ContractChangedEvent;
import ru.testapp.contract.client.ContractSelectedEvent;
import ru.testapp.contract.client.ContractSelectedHandler;
import ru.testapp.contract.client.ContractTableItem;
import ru.testapp.contract.client.EditDataService;
import ru.testapp.contract.client.EditDataServiceAsync;
import ru.testapp.contract.client.IContractEditor;
import ru.testapp.contract.client.LoadDataService;
import ru.testapp.contract.client.LoadDataServiceAsync;
import ru.testapp.contract.client.PersonChangedEvent;
import ru.testapp.contract.client.PersonChangedHandler;
import ru.testapp.contract.client.PersonEditEvent;
import ru.testapp.contract.client.dto.AddressDTO;
import ru.testapp.contract.client.dto.AreaCoefficientDTO;
import ru.testapp.contract.client.dto.ContractDTO;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.dto.RealtyTypeDTO;
import ru.testapp.contract.client.dto.YearCoefficientDTO;
import ru.testapp.contract.client.places.PersonEditorPlace;
import ru.testapp.contract.client.places.PersonListPlace;
import ru.testapp.contract.client.places.StartWindowPlace;

/**
 * @author pavlin
 * 
 * Defines contract editor view
 * 
 */
public class ContractEditorView extends Composite 
	implements IContractEditor {

	private EventBus eventBus;
	private PersonDTO person;
	private AddressDTO address;
	private ContractDTO contract;
	private ArrayList<RealtyTypeDTO> realtyCoeffList;
	private ArrayList<YearCoefficientDTO> yearCoeffList;
	private ArrayList<AreaCoefficientDTO> areaCoeffList;
	private DateTimeFormat dtf;
	private Presenter presenter;
	private int contractId = -1;
	
	@UiField TextBox insuranceSum;
	@UiField TextBox builtYear;
	@UiField TextBox area;
	@UiField TextBox locality;
	@UiField TextBox building;
	@UiField TextBox flat;
	@UiField TextBox postIndex;
	@UiField TextBox street;
	@UiField TextBox region;
	@UiField TextBox housing;
	@UiField TextBox republic;
	@UiField TextBox house;
	@UiField TextBox contractNum;
	@UiField TextBox bonus;
	@UiField TextBox personName;
	@UiField TextBox passportSerial;
	@UiField TextBox passportNum;
	@UiField TextArea comment;
	@UiField(provided=true) DateBox pickerSince;
	@UiField(provided=true) DateBox pickerTo;
	@UiField(provided=true) DateBox birthDate;
	@UiField(provided=true) DateBox calcDate;
	@UiField(provided=true) DateBox contractDate;
	@UiField ListBox realtyType;
	@UiField ListBox state;
	@UiField Button save;
	@UiField Button back;
	@UiField Button calculate;
	@UiField Button selectPerson;
	@UiField Button editPerson;
	
	private static ContractEditorUiBinder uiBinder = GWT.create(ContractEditorUiBinder.class);

	interface ContractEditorUiBinder extends UiBinder<Widget, ContractEditorView> {}

	public ContractEditorView() {
		person = new PersonDTO();
		address = new AddressDTO();
		contract = new ContractDTO();
		dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
		
		initDateBoxes();
		initWidget(uiBinder.createAndBindUi(this));
		
		personName.setEnabled(false);
		
		addEventHandlers();
		initRealtyListBox();
		initStateListBox();
		initAreaCoefficientList();
		initYearCoefficientList();
	}

	/**
	 * Initialize event handlers
	 * 	- ContractSelectedEvent
	 * 	- PersonSelectedEvent
	 */
	private void addEventHandlers() {
		eventBus = ClientFactoryImpl.getEventBus();
		
		eventBus.addHandler(ContractSelectedEvent.TYPE, 
				new ContractSelectedHandler() {
					@Override
					public void onContractSelected(ContractSelectedEvent event) {
						contractId = event.getContractId();
						
						// if opening existing contract
						if(contractId != -1) {
							contractNum.setEnabled(false);
							loadContractData(contractId);
						} else {
							setDefaults();
						}
					}
		});
		
		eventBus.addHandler(PersonChangedEvent.TYPE, new PersonChangedHandler() {

			@Override
			public void onPersonChanged(PersonChangedEvent event) {				
				person = event.getPerson();
				personName.setValue(person.getFio());
				birthDate.setValue(person.getBirthDate());
				passportSerial.setValue(String.valueOf(person.getPassportSerial()));
				passportNum.setValue(String.valueOf(person.getPassportNumber()));
			}
		});
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("selectPerson")
	void onSelectPersonClick(ClickEvent event) {
		presenter.goTo(new PersonListPlace("personList"));
	}
	
	@UiHandler("editPerson")
	void onEditPersonClick(ClickEvent event) {
		if(personName.getValue() == "") {
			Window.alert("Выберите клиента для внесения изменений");
			return;
		}
		
		presenter.goTo(new PersonEditorPlace("personEditor"));
		eventBus.fireEvent(new PersonEditEvent(person));
	}
	
	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		presenter.goTo(new StartWindowPlace("start"));
	}
	
	/**
	 * 
	 * Handling calculate button click
	 * 
	 * @param event click button event
	 */
	@UiHandler("calculate")
	void onCalculateClick(ClickEvent event) {
		Date since = pickerSince.getValue();
		Date to = pickerTo.getValue();
		int days = 0;
		int sum = 0;
		float result = 0;
		float realty = 0;
		float year = 0;
		float area = 0;
		
		// check bonus parameters
		if(!validateBonusParameters()) {
			return;
		}
		
		days = CalendarUtil.getDaysBetween(since, to);
		
		if(days <= 0) {
			Window.alert("Количество дней не может быть меньше или равно ноля");
			return;
		}
		
		sum = Integer.parseInt(insuranceSum.getValue());
		
		// get float coefficient values
		realty = getRealtyFloatValue();
		year = getYearFloatValue();
		area = getAreaFloatValue();
 
		// bonus calculation formula
		result = (sum / days) * realty * year * area;
		
		bonus.setValue(NumberFormat.getFormat("#######.00").format(result));
		calcDate.setValue(getCurrentDate());
	}
	
	/**
	 * Get coefficient corresponding to selected realty type
	 * @return realty coefficient
	 */
	float getRealtyFloatValue() {
		float value = 0;
		String realty = realtyType.getSelectedValue();
		
		for(RealtyTypeDTO dto : realtyCoeffList) {
			if(dto.getName().equals(realty)) {
				value = dto.getCoefficient();
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * Get coefficient corresponding to selected year
	 * @return
	 */
	float getYearFloatValue() {
		float value = 0;
		float last = 0;
		int year = Integer.parseInt(builtYear.getValue());
		
		for(YearCoefficientDTO dto : yearCoeffList) {
			last = dto.getCoefficient();

			if(year <= dto.getYear()) {
				value = last;
				break;
			}
		}
		
		// if year bigger than right edge value
		if(value == 0) {
			value = last;
		}
		
		return value;
	}
	
	/**
	 * Get coefficient corresponding to selected area size
	 * @return
	 */
	float getAreaFloatValue() {
		float value = 0;
		float last = 0;
		float areaSize = Float.parseFloat(area.getValue().replace(",", "."));
		
		for(AreaCoefficientDTO dto : areaCoeffList) {
			last = dto.getCoefficient();

			if(areaSize <= dto.getSize()) {
				value = last;
				break;
			}
		}
		
		// if area size is bigger than right edge value
		if(value == 0) {
			value = last;
		}
		
		return value;
	}
	
	/**
	 * Handling save button click event.
	 * Validate form data and make RPC query to remote server
	 * to save data
	 * @param event save button click
	 */
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		// if date of contract is not set
		if(contractDate.getValue() == null) {
			contractDate.setValue(getCurrentDate());
		}
		
		if(validateFormData()) {
			try {
				initAddress();
				initPerson();
				initContract();
			} catch(NumberFormatException e) {
				Window.alert("Ошибка сохранения данных");
			} catch (Throwable e) {
				Window.alert("Ошибка сохранения данных");
			}
		} else {
			return;
		}
		
		EditDataServiceAsync rpc = GWT.create(EditDataService.class);
		
		// if creating new contract
		if(contractId == -1) {
			addContract();
		// if opened existing contract
		} else {
			rpc.updateAddress(address, new QueryEditCallBack<Void>());
			rpc.updateContract(contract, new ContractChangedCallBack<Void>());
		}
		
		updatePerson();
	}
	
	
	/**
	 * Initialize Person object
	 * @throws NumberFormatException
	 */
	void initPerson() throws NumberFormatException {		
		person.setFio(personName.getValue());
		person.setBirthDate(birthDate.getValue());
		person.setPassportSerial(Integer.parseInt(passportSerial.getValue()));
		person.setPassportNumber(Integer.parseInt(passportNum.getValue()));
	}
	
	/**
	 * Initialize Contract object
	 * @throws NumberFormatException
	 */
	void initContract() throws NumberFormatException {
		contract.setRealty_id(realtyType.getSelectedIndex());
		contract.setInsuranceSum(Integer.parseInt(insuranceSum.getValue()));
		contract.setDateSince(pickerSince.getValue());
		contract.setDateTo(pickerTo.getValue());
		contract.setBuiltYear(Integer.parseInt(builtYear.getValue()));
		contract.setArea(area.getValue());
		contract.setCalculationDate(calcDate.getValue());
		contract.setBonus(bonus.getValue());
		contract.setContractNumber(Integer.parseInt(contractNum.getValue()));
		contract.setContractDate(contractDate.getValue());
		contract.setComment(comment.getValue());
	}
	
	/**
	 * Initialize Address object
	 */
	void initAddress() throws NumberFormatException {		
		address.setState(state.getSelectedValue());
		address.setPostIndex(postIndex.getValue());
		address.setRepublic(republic.getValue());
		address.setRegion(region.getValue());
		address.setLocality(locality.getValue());
		address.setStreet(street.getValue());
		address.setHouse(Integer.parseInt(house.getValue()));
		address.setHousing(housing.getValue());
		address.setBuilding(building.getValue());
		address.setFlat(Integer.parseInt(flat.getValue()));
	}
	
	/**
	 * Run data form validation
	 * @return true if all data is valid, false otherwise
	 */
	boolean validateFormData() {
		final String intDigit = "^[0-9]+$";
		final String contractNumber = "^[0-9]{6}$";
		final String passpSerial = "^[0-9]{4}$";
		final String passpNum = "^[0-9]{6}$";
		final String floatDigit = "^[0-9]+.[0-9]{2}$";
		
		RegExp regExp = null;
		
		// check bonus parameters
		if(!validateBonusParameters()) {
			return false;
		}
		
		regExp = RegExp.compile(contractNumber);
		if(regExp.exec(contractNum.getValue()) == null) {
			Window.alert("Номер договора должен состоять из 6 цифр");
			return false;
		}
		
		regExp = RegExp.compile(passpSerial);
		if(regExp.exec(passportSerial.getValue()) == null) {
			Window.alert("Серия паспорта введена некорректно");
			return false;
		}
		
		regExp = RegExp.compile(passpNum);
		if(regExp.exec(passportNum.getValue()) == null) {
			Window.alert("Номер паспорта введен некорректно");
			return false;
		}
		
		regExp = RegExp.compile(intDigit);
		if(regExp.exec(flat.getValue()) == null) {
			Window.alert("Номер квартиры введен некорректно");
			return false;
		}
		
		if(birthDate.getValue() == null) {
			Window.alert("Введите дату рождения");
			return false;
		}
		
		if(state.getValue(0).equals("")) {
			Window.alert("Укажите государство");
			return false;
		}
		
		if(republic.getValue().equals("")) {
			Window.alert("Укажите область");
			return false;
		}
		
		if(locality.getValue().equals("")) {
			Window.alert("Укажите населенный пункт");
			return false;
		}
		
		if(street.getValue().equals("")) {
			Window.alert("Укажите укажите улицу");
			return false;
		}
		
		if(flat.getValue().equals("")) {
			Window.alert("Укажите номер квартиры");
			return false;
		}
		
		regExp = RegExp.compile(floatDigit);
		if(regExp.exec(bonus.getValue()) == null) {
			Window.alert("Премия введена некорректно");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check main parameters for bonus calculation
	 * @return true if parameters are valid and false otherwise
	 */
	boolean validateBonusParameters() {
		final String intDigit = "^[0-9]+$";
		final String year = "^[1-2]{1}[0-9]{3}$";
		final String areaFloat = "^[0-9]+.[0-9]{1}$";
		
		RegExp regExp = null;
		Date calendarDate = getCurrentDate();
		
		regExp = RegExp.compile(intDigit);
		if(regExp.exec(insuranceSum.getValue()) == null) {
			Window.alert("Значение страховой суммы должно быть целым числом");
			return false;
		}
		
		regExp = RegExp.compile(year);
		if(regExp.exec(builtYear.getValue()) == null) {
			Window.alert("Год постройки введен некорректно");
			return false;
		} else {
			int formYear = Integer.parseInt(builtYear.getValue());
			
			if(formYear > getDateYear(calendarDate)) {
				Window.alert("Год постройки не может быть больше текущего года");
				return false;
			}
		}
		
		regExp = RegExp.compile(areaFloat);
		if(regExp.exec(area.getValue()) == null) {
			Window.alert("Значение площади введено некорректно. (Пример: 45.5)");
			return false;
		}
		
		// check if `Since` date >= current date
		if(!validDate(pickerSince.getValue(), calendarDate, false)) {
			return false;
		}
		
		// check if `To` date > `Since` date 
		// and is no longer of a year
		if(!validDate(pickerSince.getValue(), pickerTo.getValue(), true)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get year of the date
	 * @param date any input Date object
	 * @return year of the date
	 */
	int getDateYear(Date date) {
		DateTimeFormat stf = DateTimeFormat.getFormat("yyyy");
		String value = stf.format(date);
		
		return Integer.parseInt(value);	
	}
	
	/**
	 * Get current Date object using TimeZone
	 * @return current date object
	 */
	Date getCurrentDate() {
		Date date = new Date();
		dtf.format(date, TimeZone.createTimeZone(0));
		
		return date;
	}
	
	/**
	 * Check `pickerSince` and `pickerTo` for correct values
	 * @param dateSince start Date object
	 * @param date_2 second Date object
	 * @param nextYear when need to compare with next year
	 * @return true if date is valid and false otherwise
	 */
	boolean validDate(Date dateSince, Date date_2, boolean nextYear) {
		if(dateSince == null) {
			Window.alert("Поле начала срока не должно быть пустым");
			return false;
		}
		
		// need to compare with after year value
		if(nextYear) {
			if(date_2 == null) {
				Window.alert("Поле окончания срока не должно быть пустым");
				return false;
			}
			
			if(!date_2.after(dateSince)) {
				Window.alert("Дата окончания срока не может быть раньше или равной дате начала");
				return false;
			}
			
			// year after `dateSince`
			CalendarUtil.addMonthsToDate(dateSince, 12);
			
			if(date_2.after(dateSince)) {
				Window.alert("Срок контракта не должен превышать одного года");
				return false;
			}
		} else {
			CalendarUtil.resetTime(date_2);

			if(dateSince.before(date_2)) {
				Window.alert("Дата начала срока не может быть раньше текущей даты");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Initialize all DateBox objects
	 */
	void initDateBoxes() {
		pickerSince = new DateBox();
		pickerTo = new DateBox();
		birthDate = new DateBox();
		calcDate = new DateBox();
		contractDate = new DateBox();
		
		setDateBoxFormat();
		
		calcDate.setEnabled(false);
		contractDate.setEnabled(false);
	}
	
	/**
	 * Set formatter to DateBox object
	 * @param format date format representation
	 */
	private void setDateBoxFormat() {
		DateBox.DefaultFormat df = new DateBox.DefaultFormat(dtf);
		pickerSince.setFormat(df);
		pickerTo.setFormat(df);
		birthDate.setFormat(df);
		calcDate.setFormat(df);
		contractDate.setFormat(df);
	}
	
	/**
	 * Make the RPC request for realty type list values
	 */
	void initRealtyListBox() {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.listRealtyCoefficient(new AsyncCallback<ArrayList<RealtyTypeDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось загрузить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<RealtyTypeDTO> result) {
				realtyCoeffList = result;
				realtyType.clear();
				
				for(RealtyTypeDTO coeff : realtyCoeffList) {
					realtyType.addItem(coeff.getName());
				}
			}
		});
	}
	
	/**
	 * Make the RPC request for area coefficients list values
	 */
	void initAreaCoefficientList() {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.listAreaCoefficient(new AsyncCallback<ArrayList<AreaCoefficientDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось загрузить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<AreaCoefficientDTO> result) {
				areaCoeffList = result;
			}
		});
	}
	
	/**
	 * Make the RPC request for year coefficients list values
	 */
	void initYearCoefficientList() {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.listYearCoefficient(new AsyncCallback<ArrayList<YearCoefficientDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось загрузить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<YearCoefficientDTO> result) {
				yearCoeffList = result;
			}
		});
	}
	
	/**
	 * Set default values to form widgets when create new contract
	 */
	void setDefaults() {
		pickerSince.setValue(getCurrentDate());
		insuranceSum.setValue("");
		builtYear.setValue("");
		area.setValue("");
		locality.setValue("");
		building.setValue("");
		flat.setValue("");
		postIndex.setValue("");
		street.setValue("");
		region.setValue("");
		housing.setValue("");
		republic.setValue("");
		house.setValue("");
		bonus.setValue("");
		personName.setValue("");
		passportSerial.setValue("");
		passportNum.setValue("");
		comment.setValue("");
		pickerTo.setValue(null);
		birthDate.setValue(null);
		calcDate.setValue(null);
		contractDate.setValue(null);
		contractNum.setEnabled(false);
		
		// Get next free contract number
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		rpc.getFreeContractNumber(new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				contractNum.setValue("");
			}

			@Override
			public void onSuccess(Integer result) {
				contractNum.setValue(String.valueOf(result));
			}
		});
	}

	/**
	 * Initialize state list box
	 */
	private void initStateListBox() {
		state.addItem("USA");
		state.addItem("England");
		state.addItem("Russia");
		state.addItem("Africa");
		state.addItem("Spain");
	}
	
	/**
	 * RPC request to add new contract data
	 */
	private void addContract() {
		EditDataServiceAsync rpc = GWT.create(EditDataService.class);
		
		rpc.addAddress(address, new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось сохранить данные: " + caught.getMessage());
			}

			@Override
			public void onSuccess(Integer result) {
				int realtyId = realtyType.getSelectedIndex();
				address.setId(result);
				contract.setAddress_id(address.getId());
				contract.setPerson_id(person.getId());
				contract.setRealty_id(realtyId);

				EditDataServiceAsync rpc = GWT.create(EditDataService.class);
				rpc.addContract(contract, new ContractChangedCallBack<Integer>());
			}
		});
	}
	
	/**
	 * Update person data RPC
	 */
	private void updatePerson() {
		EditDataServiceAsync rpc = GWT.create(EditDataService.class);
		
		rpc.updatePerson(person, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {}

			@Override
			public void onSuccess(Void result) {}
		});
	}
	
	/**
	 * @author pavlin
	 *
	 * Handle callback when contract was remotely saved
	 *
	 * @param <T> Integer when add new contract, Void when 
	 * contract was updated
	 */
	private class ContractChangedCallBack<T> implements AsyncCallback<T> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Не удалось сохранить данные: " + caught.getMessage());
		}

		@Override
		public void onSuccess(T result) {
			Window.alert("Данные сохранены успешно");
			ContractTableItem item = new ContractTableItem(contract, person);
			
			// if contract was added
			if(result instanceof Integer) {
				contract.setId((int)result);
				item = new ContractTableItem(contract, person);
				eventBus.fireEvent(new ContractAddedEvent(item));
			} else {
				eventBus.fireEvent(new ContractChangedEvent(item));
			}
		}
	}
	
	/**
	 * @author pavlin
	 * Default callback for address and person updates
	 * @param <T>
	 */
	private class QueryEditCallBack<T> implements AsyncCallback<T> {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Не удалось сохранить данные: " + caught.getMessage());
		}

		@Override
		public void onSuccess(T result) {
		}
	}

	/**
	 * Get contract data by id
	 * @param contractId contract id
	 */
	void loadContractData(int contractId) {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.getContract(contractId, new AsyncCallback<ContractDTO>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(ContractDTO result) {
				int personId = result.getPerson_id();
				int addressId = result.getAddress_id();
				contract = result;
				
				loadPersonData(personId);
				loadAddressData(addressId);
				
				insuranceSum.setValue(String.valueOf(result.getInsuranceSum()));
				realtyType.setSelectedIndex(result.getRealty_id());
				builtYear.setValue(String.valueOf(result.getBuiltYear()));
				area.setValue(result.getArea());
				pickerSince.setValue(result.getDateSince());
				pickerTo.setValue(result.getDateTo());
				calcDate.setValue(result.getCalculationDate());
				bonus.setValue(result.getBonus());
				contractNum.setValue(String.valueOf(result.getContractNumber()));
				contractDate.setValue(result.getContractDate());
				comment.setValue(result.getComment());
			}
			
		});
	}
	
	/**
	 * Get person data by id
	 * @param personId person id
	 */
	void loadPersonData(int personId) {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.getPerson(personId, new AsyncCallback<PersonDTO>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(PersonDTO result) {
				person = result;
				personName.setValue(result.getFio());
				birthDate.setValue(result.getBirthDate());
				passportSerial.setValue(String.valueOf(result.getPassportSerial()));
				passportNum.setValue(String.valueOf(result.getPassportNumber()));
			}
		});
	}
	
	/**
	 * Get address data by id 
	 * @param addressId address id
	 */
	void loadAddressData(int addressId) {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.getAddress(addressId, new AsyncCallback<AddressDTO>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(AddressDTO result) {
				address = result;
				
				for(int i = 0; i < state.getItemCount(); i++) {
					if(state.getValue(i).equals(result.getState())) {
						state.setSelectedIndex(i);
						break;
					}
				}
				
				postIndex.setValue(result.getPostIndex());
				republic.setValue(result.getRepublic());
				region.setValue(result.getRegion());
				locality.setValue(result.getLocality());
				street.setValue(result.getStreet());
				house.setValue(String.valueOf(result.getHouse()));
				housing.setValue(result.getHousing());
				building.setValue(result.getBuilding());
				flat.setValue(String.valueOf(result.getFlat()));
			}
			
		});
	}
}
