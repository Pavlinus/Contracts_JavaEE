package ru.testapp.contract.client.Activities;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import ru.testapp.contract.client.ClientFactory;
import ru.testapp.contract.client.IContractEditor;

/**
 * @author pavlin
 * 
 * Defines contract editor activity
 * 
 */
public class ContractEditorActivity extends AbstractActivity
	implements IContractEditor.Presenter {
	
	private ClientFactory clientFactory;
	
	public ContractEditorActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IContractEditor editor = clientFactory.getContractEditor();
		editor.setPresenter(this);
		panel.setWidget(editor.asWidget());
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

}
