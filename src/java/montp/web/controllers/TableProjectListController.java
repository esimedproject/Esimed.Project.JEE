package montp.web.controllers;

import montp.data.dao.ClientDAO;
import montp.data.model.Client;
import montp.web.FacesTools;
import org.primefaces.event.CellEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionalException;
import java.io.Serializable;

@ViewScoped
@Named
public class TableClientListController implements Serializable {

    @Inject
    private ClientDAO clientDAO;
    @Named
    @Produces
    private Client client;

    @PostConstruct
    public void init() {
        createClient();
    }

    public void createClient() {
        this.client = new Client();
    }

    public void saveClient() {
        if (client.getId() == null) {
            clientDAO.insertClient(client);
            client = new Client();
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Client create");
        } else {
            clientDAO.updateClient(client);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Modification add");
        }
    }

    public void delete(Client client) {
        try {
            clientDAO.delete(client);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "deletion completed",
                    client);
        } catch (TransactionalException txe) {
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR,
                    "delete error",
                    client);
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
}
