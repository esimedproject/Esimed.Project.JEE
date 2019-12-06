package montp.services;

import montp.data.dao.ClientDAO;
import montp.data.model.Client;
import montp.data.model.GenericEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import java.util.List;

@ApplicationScoped
public class ClientService extends GenericService<Client, ClientDAO> {

    public List<Client> getClient(){
        return dao.getAllClient();
    }
}
