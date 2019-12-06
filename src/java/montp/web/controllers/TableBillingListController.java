package montp.web.controllers;

import montp.data.dao.BillDAO;
import montp.data.dao.ClientDAO;
import montp.data.dao.ProjectDAO;
import montp.data.model.Bill;
import montp.data.model.Client;
import montp.data.model.Project;
import montp.data.model.security.User;
import montp.web.FacesTools;
import montp.web.UserSession;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.TransactionalException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class TableBillListController implements Serializable {

    @Inject
    private BillDAO billDAO;
    @Inject
    private ProjectDAO projectDAO;
    @Inject
    private FilterViewController filterViewController;
    @Inject
    private UserSession userSession;
    @Named
    @Produces
    private Bill bill;
    private List<Project> projectList;

    @PostConstruct
    public void init() {
        projectList = projectDAO.getAllProject();
        createBill();
    }

    public void createBill() {
        this.bill = new Bill();
    }

    public void saveBill() {
        if (bill.getId() == null) {
            User u = userSession.getUser();
            Long billingNumber = u.getBillingNumber();

            bill.setBilling_number(billingNumber);
            billDAO.insertBill(bill);

            u.setBillingNumber(++billingNumber);
            userSession.getUserService().update(u, true);

            bill = new Bill();
            filterViewController.init();
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Project create");
        } else {
            billDAO.updateBill(bill);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "Modification add");
        }
    }

    public void delete(Bill bill) {
        try {
            billDAO.delete(bill);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO,
                    "deletion completed",
                    bill);
        } catch (TransactionalException txe) {
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR,
                    "delete error",
                    bill);
        }
    }

    public BillDAO getBillDAO() {
        return billDAO;
    }

    public void setBillDAO(BillDAO billDAO) {
        this.billDAO = billDAO;
    }

    public ProjectDAO getProjectDAO() {
        return projectDAO;
    }

    public void setProjectDAO(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }
}
