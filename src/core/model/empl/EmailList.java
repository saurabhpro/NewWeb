package core.model.empl;

import core.model.empl.BasicEmployeeDetails;

/**
 * Created by kumars on 3/1/2016.
 */
public class EmailList extends BasicEmployeeDetails {
    private String mailId;

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }
}
