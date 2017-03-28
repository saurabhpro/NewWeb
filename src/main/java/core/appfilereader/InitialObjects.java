package core.appfilereader;

import core.model.appfilereadermodal.EmployeeBiometricDetails;
import core.model.appfilereadermodal.EmployeeHrnetDetails;
import core.model.employeemodal.BasicEmployeeDetails;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by kumars on 4/19/2016.
 *
 * @author Saurabh
 * @version 1.0
 */
public class InitialObjects {
	private static final long serialVersionUID = 1L;

	/**
	 * Map to store the record read from Biometric File with Reval Employee Id
	 * as the key
	 */
	public static Map<String, EmployeeBiometricDetails> empBiometricMap;

	/**
	 * Map to store the record read from Financial Force File with Reval
	 * Salesforce Id as the key
	 */
	public static Map<String, ArrayList<EmployeeHrnetDetails>> hrnetDetailsMap;

	/**
	 * Map to store the record read from AllEmployeeBasicRecord File with Reval
	 * Employee Id as the key
	 */
	public static Map<String, BasicEmployeeDetails> allEmployeeBasicRecordMap;
}
