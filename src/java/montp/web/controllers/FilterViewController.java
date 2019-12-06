package montp.web.controllers;

import montp.data.model.Client;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;

@Named("dtFilterView")
@ViewScoped
public class FilterView implements Serializable {

    @PostConstruct
    public void init() {

    }

    public boolean filterByPrice(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(getInteger(filterText)) > 0;
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }
        int filterInt = getInteger(filterText);

        Client client = (Client) value;
        return client.getName().toLowerCase().contains(filterText)
                || client.getContact_username().toLowerCase().contains(filterText)
                || client.getEmail().toLowerCase().contains(filterText)
                || client.getAddress().toLowerCase().contains(filterText)
                || client.getPhone_number().toLowerCase().contains(filterText)
                || (client.isSold() ? "Company" : "User").contains(filterText);
    }

    private int getInteger(String string) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
