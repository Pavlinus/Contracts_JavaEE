package ru.testapp.contract.client.Views;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import ru.testapp.contract.client.ClientFactoryImpl;
import ru.testapp.contract.client.IPersonList;
import ru.testapp.contract.client.LoadDataService;
import ru.testapp.contract.client.LoadDataServiceAsync;
import ru.testapp.contract.client.PersonAddedEvent;
import ru.testapp.contract.client.PersonAddedHandler;
import ru.testapp.contract.client.PersonChangedEvent;
import ru.testapp.contract.client.PersonChangedHandler;
import ru.testapp.contract.client.dto.PersonDTO;
import ru.testapp.contract.client.places.ContractEditorPlace;
import ru.testapp.contract.client.places.NewPersonPlace;

/**
 * @author pavlin
 * 
 * Defines person list view
 */
public class PersonListView extends Composite
	implements IPersonList {

	private static PersonListUiBinder uiBinder = GWT.create(PersonListUiBinder.class);
	private EventBus eventBus;
	private PersonDTO personSelected;
	private Presenter presenter;
	private ArrayList<PersonDTO> tableData;
	
	@UiField TextBox surname;
	@UiField TextBox name;
	@UiField TextBox secondName;
	@UiField Button create;
	@UiField Button select;
	@UiField Button close;
	@UiField Button find;
	@UiField CellTable<PersonDTO> personTable;

	interface PersonListUiBinder extends UiBinder<Widget, PersonListView> {}
	
	public PersonListView() {
		eventBus = ClientFactoryImpl.getEventBus();
		personSelected = null;
		tableData = new ArrayList<PersonDTO>();
		
		eventBus.addHandler(PersonAddedEvent.TYPE, new PersonAddedHandler() {

			@Override
			public void onPersonAdded(PersonAddedEvent event) {
				tableData.add(event.getPerson());
				
				updateTable(tableData);
			}
		});
		
		eventBus.addHandler(PersonChangedEvent.TYPE, new PersonChangedHandler() {

			@Override
			public void onPersonChanged(PersonChangedEvent event) {
				PersonDTO changedItem = event.getPerson();
				int itemId = changedItem.getId();
				
				for(PersonDTO item : tableData) {
					if(item.getId() == itemId) {
						item.setBirthDate(changedItem.getBirthDate());
						item.setFio(changedItem.getFio());
						item.setPassportNumber(changedItem.getPassportNumber());
						item.setPassportSerial(changedItem.getPassportSerial());
						break;
					}
				}
				
				updateTable();
			}
		});
		
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		fillTable();
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("create")
	void onCreateClick(ClickEvent event) {
		presenter.goTo(new NewPersonPlace("newPerson"));
	}
	
	@UiHandler("select")
	void onSelectClick(ClickEvent event) {
		if(personSelected != null) {
			eventBus.fireEvent(new PersonChangedEvent(personSelected));
			presenter.goTo(new ContractEditorPlace("contractEditor"));
		} else {
			Window.alert("Выберите одного клиента из таблицы");
		}
	}
	
	@UiHandler("close")
	void onCloseClick(ClickEvent event) {
		presenter.goTo(new ContractEditorPlace("contractEditor"));
	}
	
	/**
	 * Filter data at the cell table
	 * @param event find button click
	 */
	@UiHandler("find")
	void onFindClick(ClickEvent event) {
		ArrayList<PersonDTO> result = new ArrayList<PersonDTO>();
		StringBuilder builder = new StringBuilder();
		builder.append(surname.getValue());
		builder.append(" ");
		builder.append(name.getValue());
		builder.append(" ");
		builder.append(secondName.getValue());
		
		// if fields are empty
		if(builder.toString().trim().equals("")) {
			result = tableData;
		} else {
			// looking for equal values
			for(PersonDTO item : tableData) {
				if(item.getFio().equals(builder.toString())) {
					result.add(item);
				}
			}
		}
		
		updateTable(result);
	}
	
	/**
	 * Connect table fields with PersonDTO class fields
	 */
	private void initTable() {		
		TextColumn<PersonDTO> colFio = 
				new TextColumn<PersonDTO>() {

					@Override
					public String getValue(PersonDTO object) {
						return object.getFio();
					}
			
		};
		personTable.addColumn(colFio, "ФИО");
		
		TextColumn<PersonDTO> colBirth = 
				new TextColumn<PersonDTO>() {

					@Override
					public String getValue(PersonDTO object) {
						DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
						Date date = object.getBirthDate();
						
						if(date == null) {
							return "no date";
						}
						
						return dtf.format(object.getBirthDate());
					}
			
		};
		personTable.addColumn(colBirth, "Дата рождения");
		
		TextColumn<PersonDTO> colPassport = 
				new TextColumn<PersonDTO>() {

					@Override
					public String getValue(PersonDTO object) { 
						return object.getPassportSerial() + " №" + object.getPassportNumber();
					}
			
		};
		personTable.addColumn(colPassport, "Паспортные данные");
		
		final SingleSelectionModel<PersonDTO> model =
				new SingleSelectionModel<PersonDTO>();
		personTable.setSelectionModel(model);
		model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				PersonDTO item = model.getSelectedObject();
				
				if(item != null) {
					personSelected = item;
				}
			}
		});
		
		personTable.setVisibleRange(1, 10);
	}
	
	void updateTable() {
		personTable.setRowCount(tableData.size(), true);
		personTable.setRowData(1, tableData);
		personTable.redraw();
	}
	
	void updateTable(ArrayList<PersonDTO> list) {
		personTable.setRowCount(list.size(), true);
		personTable.setRowData(1, list);
		personTable.redraw();
	}
	
	/**
	 * Get list of existing persons and fill table
	 */
	private void fillTable() {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.listPerson(new AsyncCallback<ArrayList<PersonDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<PersonDTO> result) {
				tableData = result;
				updateTable();
			}
		});
	}
}
