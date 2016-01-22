package ru.testapp.contract.client.Views;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import ru.testapp.contract.client.ClientFactoryImpl;
import ru.testapp.contract.client.ContractAddedEvent;
import ru.testapp.contract.client.ContractAddedHandler;
import ru.testapp.contract.client.ContractChangedEvent;
import ru.testapp.contract.client.ContractChangedHandler;
import ru.testapp.contract.client.ContractSelectedEvent;
import ru.testapp.contract.client.ContractTableItem;
import ru.testapp.contract.client.IStartWindow;
import ru.testapp.contract.client.LoadDataService;
import ru.testapp.contract.client.LoadDataServiceAsync;
import ru.testapp.contract.client.places.ContractEditorPlace;

/**
 * @author pavlin
 * 
 * Defines start window view 
 */
public class StartWindowView extends Composite implements IStartWindow {
	private Presenter presenter;
	private EventBus eventBus;
	private int selectedObjectId;
	private ArrayList<ContractTableItem> tableData;

	interface StartWindowViewUiBinder extends UiBinder<Widget, StartWindowView> {}
	
	private static StartWindowViewUiBinder uiBinder = 
			GWT.create(StartWindowViewUiBinder.class);

	@UiField Button openContract;
	@UiField Button createContract;
	@UiField CellTable<ContractTableItem> table;
	
	public StartWindowView() {
		selectedObjectId = -1;
		tableData = new ArrayList<ContractTableItem>();
		
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		addEventHandlers();
	}

	@UiHandler("openContract")
	void onOpenContractClick(ClickEvent event) {
		presenter.goTo(new ContractEditorPlace("contractEditor"));
		eventBus.fireEvent(new ContractSelectedEvent(selectedObjectId));
	}
	
	@UiHandler("createContract")
	void onCreateContractClick(ClickEvent event) {
		presenter.goTo(new ContractEditorPlace("contractEditor"));
		eventBus.fireEvent(new ContractSelectedEvent(-1));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	/**
	 * Connect table columns with ContractTable Item fields
	 */
	private void initTable() {		
		TextColumn<ContractTableItem> colNum = 
				new TextColumn<ContractTableItem>() {

					@Override
					public String getValue(ContractTableItem object) {
						return object.getContractNumber() + "";
					}
			
		};
		table.addColumn(colNum, "Серия-Номер");
		
		TextColumn<ContractTableItem> colDate = 
				new TextColumn<ContractTableItem>() {

					@Override
					public String getValue(ContractTableItem object) {
						DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
						return dtf.format(object.getContractDate());
					}
			
		};
		table.addColumn(colDate, "Дата заключения");
		
		TextColumn<ContractTableItem> colName = 
				new TextColumn<ContractTableItem>() {

					@Override
					public String getValue(ContractTableItem object) {
						return object.getPersonName();
					}
			
		};
		table.addColumn(colName, "Страхователь");
		
		TextColumn<ContractTableItem> colBonus = 
				new TextColumn<ContractTableItem>() {

					@Override
					public String getValue(ContractTableItem object) {
						return object.getBonus() + "";
					}
			
		};
		table.addColumn(colBonus, "Премия");
		
		TextColumn<ContractTableItem> colPeriod = 
				new TextColumn<ContractTableItem>() {

					@Override
					public String getValue(ContractTableItem object) {
						DateTimeFormat dtf = DateTimeFormat.getFormat("dd.MM.yyyy");
						StringBuilder builder = new StringBuilder();
						builder.append(dtf.format(object.getSince()));
						builder.append("-");
						builder.append(dtf.format(object.getTo()));
						
						return builder.toString();
					}
			
		};
		table.addColumn(colPeriod, "Срок действия");
		
		final SingleSelectionModel<ContractTableItem> model =
				new SingleSelectionModel<ContractTableItem>();
		table.setSelectionModel(model);
		model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				ContractTableItem item = model.getSelectedObject();
				
				if(item != null) {
					selectedObjectId = item.getId();
				}
			}
		});
		
		fillTable();
	}
	
	void addEventHandlers() {
		eventBus = ClientFactoryImpl.getEventBus();
		
		// add new contract event handler 
		eventBus.addHandler(ContractAddedEvent.TYPE, new ContractAddedHandler() {

			@Override
			public void onContractAdded(ContractAddedEvent event) {
				tableData.add(event.getContractItem());
				updateTable();
			}
		});
		
		// change contract data list when some contract changed
		eventBus.addHandler(ContractChangedEvent.TYPE, new ContractChangedHandler() {

			@Override
			public void onContractChanged(ContractChangedEvent event) {
				ContractTableItem changedItem = event.getContractItem();
				int itemId = changedItem.getId();
				
				for(ContractTableItem item : tableData) {
					if(item.getId() == itemId) {
						item.setBonus(changedItem.getBonus());
						item.setContractDate(changedItem.getContractDate());
						item.setContractNumber(changedItem.getContractNumber());
						item.setSince(changedItem.getSince());
						item.setTo(changedItem.getTo());
						item.setPersonName(changedItem.getPersonName());
						break;
					}
				}
				
				updateTable();
			}
		});
	}
	
	void updateTable() {
		table.setRowCount(tableData.size(), true);
		table.setRowData(0, tableData);
		table.redraw();
	}
	
	/**
	 * Get ContractTableItem list
	 */
	void fillTable() {
		LoadDataServiceAsync rpc = GWT.create(LoadDataService.class);
		
		rpc.listContractPerson(new AsyncCallback<ArrayList<ContractTableItem>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Не удалось загрузить данные");
			}

			@Override
			public void onSuccess(ArrayList<ContractTableItem> result) {
				tableData = result;
				updateTable();
			}
			
		});
	}
}
