package montp.web.controllers;

import montp.data.dao.ClientDAO;
import montp.data.dao.ProjectDAO;
import montp.data.model.Client;
import montp.data.model.Project;
import montp.locale.Messages;
import montp.web.FacesTools;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionalException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class TableProjectListController implements Serializable {

    @Inject
    private ClientDAO clientDAO;
    @Inject
    private ProjectDAO projectDAO;
    @Named
    @Produces
    private Project project;
    private List<Client> clientList;

    @PostConstruct
    public void init() {
        clientList = clientDAO.getAllClient();
        createProject();
    }

    public void createProject() {
        this.project = new Project();
    }

    public void saveProject() {
        if (project.getId() == null) {
            projectDAO.insertProject(project);
            project = new Project();
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Project create");
        } else {
            projectDAO.updateProject(project);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Modification add");
        }
    }

    public void delete(Project project) {
        try {
            projectDAO.delete(project);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "deletion completed",
                    project);
        } catch (TransactionalException txe) {
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR,
                    "delete error",
                    project);
        }
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }
}
