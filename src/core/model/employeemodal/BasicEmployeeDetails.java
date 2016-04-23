package core.model.employeemodal;

import core.appfilereader.AllEmployeesBasicData;

import java.io.Serializable;

import static core.model.ProjectConstants.*;

/**
 * Created by kumars on 2/29/2016.
 */
public class BasicEmployeeDetails implements Serializable {
    final long serialVersionUID = 1L;
    private String name;
    private String empId;
    private String salesForceId;
    private String emailId;

    /**
     * @param name
     * @param empId
     * @param salesForceId
     * @param emailId
     */
    public BasicEmployeeDetails(String name, String empId, String salesForceId, String emailId) {
        this.name = name;
        this.empId = empId;
        this.salesForceId = salesForceId;
        this.emailId = emailId;
    }

    /**
     *
     */
    public BasicEmployeeDetails() {
    }

    /**
     *
     */
    public void displayBasicInfo() {
        System.out.print(EMP_REVAL_IND_ID + " : " + this.getEmpId());
        System.out.print("\t" + EMP_NAME + " : " + this.getName());
        System.out.print("\t" + EMP_EMAIL_ID + " : " + this.getEmailId());
        System.out.println("\t" + EMP_FINANCIAL_FORCE_ID + " : " + this.getSalesForceId());
    }

    /**
     * @return
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId.toLowerCase();
    }

    /**
     * @return
     */
    public String getEmpId() {
        return empId;
    }

    /**
     * @param empId
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name for reval employee
     *
     * @param name the reval employees name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the name for reval employee
     *
     * @return the reval employees name
     */
    public String getSalesForceId() {
        return salesForceId;
    }

    /**
     * gets the salesforce id for reval employee
     *
     * @param salesForceId The salesforce Id usable for whole Reval
     */
    public void setSalesForceId(String salesForceId) {
        this.salesForceId = salesForceId;
    }

    /**
     * @param empId The Reval Id for Reval India employees
     * @return the SalesForce Id returned corresponding to the Reval Id
     */
    public String getSalesForceId(String empId) {
        BasicEmployeeDetails tempSalesForceId = AllEmployeesBasicData.allEmployeeBasicRecordMap.get(empId);
        if (tempSalesForceId != null && tempSalesForceId.getSalesForceId() != null)
            return tempSalesForceId.getSalesForceId();

        else
            return null;
    }

}
