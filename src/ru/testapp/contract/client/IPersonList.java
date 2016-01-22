package ru.testapp.contract.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface IPersonList extends IsWidget {
void setPresenter(Presenter presenter);
	
	public interface Presenter {
		void goTo(Place place);
	}
}
