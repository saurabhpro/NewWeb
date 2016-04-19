package core.model;

import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Month;
import java.time.Year;

/**
 * Created by Saurabh on 4/8/2016.
 */
public class ProjectConstants {

    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public final static String EMP_REVAL_IND_ID = "Emp Reval Id";
    public final static String EMP_FINANCIAL_FORCE_ID = "Emp FinancialForce Id";
    public final static String EMP_NAME = "Emp Name";
    public final static String EMP_EMAIL_ID = "Emp Email Id";

    public final static String CURRENT_DATE = "Current Date";
    public final static String EMP_CHECK_IN = "Check In";
    public final static String EMP_CHECK_OUT = "Check Out";
    public final static String EMP_WORK_HOURS_FOR_THIS_DAY = "Work Hours";

    public final static String EMP_LEAVE_REQUEST_TYPE = "Leave Request";
    public final static String EMP_LEAVE_START_DATE = "Leave Start Date";
    public final static String EMP_LEAVE_END_DATE = "Leave End Date";
    public final static String EMP_NUM_LEAVE_REQUESTED = "Number of Leave Requested";

    public final static String EMP_ATTENDANCE_STATUS_TYPE = "Attendance Status";

    public final static String EMP_AVERAGE_MONTHLY_WORK_HOURS = "Avg Monthly Work Hours";
    public final static String EMP_AVERAGE_MONTHLY_CHECK_IN = "Avg Monthly Check In Time";
    public final static String EMP_AVERAGE_MONTHLY_CHECK_OUT = "Avg Monthly Check Out Time";
    public final static String CLARIFICATION_NEEDED = "Clarification Needed";

    public final static String UNDEFINED = "NA";

    public static String FILE_PATH = getJsonFilePath();
    public static String BIOMETRIC_FILE_PATH = "C:\\ProjectFiles\\Biometric\\";
    public static String FINANCIAL_FORCE_FILE_PATH = "C:\\ProjectFiles\\Salesforce\\";
    public static String ALL_EMPLOYEE_RECORD_FILE_PATH = "C:\\ProjectFiles\\EmailList\\";
    public static String UPDATED_RECORD_OBJECTS = "C:\\ProjectFiles\\SerialObjects\\";

    public static String ALL_EMP_WORKERS_LIST = "AllWorkers";
    public static String DISCREPANCY_IN_WORKERS_LIST = "DiscrepancyInWorkers";
    public static String WEEKEND_WORKERS_LIST = "WeekendWorkers";
    public static String PUBLIC_HOLIDAY_WORKER_LIST = "PublicHolidayWorkers";
    public static Year YEAR;
    public static Month MONTH;
    private static String BIOMETRIC_FILE_NAME;
    private static String FINANCIAL_FORCE_FILE_NAME;
    private static String EMPLOYEE_RECORD_FILE_NAME;
    private static String PUBLIC_HOLIDAY_LIST_FILE = "NOT AVAILABLE FOR NOW";

    @Nullable
    private static String getJsonFilePath() {
        try {
            new FileWriter("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\web\\" + "Test.txt");
            return "C:\\Users\\kumars\\IdeaProjects\\NewWeb\\web\\";
        } catch (IOException e) {
            try {
                new FileWriter("C:\\Users\\Aroraa\\IdeaProjects\\NewWeb\\web\\" + "Test.txt");
                return "C:\\Users\\Aroraa\\IdeaProjects\\NewWeb\\web\\";
            } catch (IOException e1) {
                try {
                    new FileWriter("C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\web\\" + "Test.txt");
                    return "C:\\Users\\Saurabh\\Documents\\GitHub\\NewWeb\\web\\";
                } catch (IOException e2) {
                    try {
                        new FileWriter("C:\\Users\\AmritaArora\\IdeaProjects\\NewWeb\\web\\" + "Test.txt");
                        return "C:\\Users\\AmritaArora\\IdeaProjects\\NewWeb\\web\\";

                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            }
        }
        return null;
        //return "C:\\ProjectFiles\\";
    }

    public static String getBiometricFileName() {
        return BIOMETRIC_FILE_NAME;
    }

    public static void setBiometricFileName(String biometricFileName) {
        BIOMETRIC_FILE_NAME = biometricFileName;
    }

    public static String getFinancialForceFileName() {
        return FINANCIAL_FORCE_FILE_NAME;
    }

    public static void setFinancialForceFileName(String financialForceFileName) {
        FINANCIAL_FORCE_FILE_NAME = financialForceFileName;
    }

    public static String getEmployeeRecordFileName() {
        return EMPLOYEE_RECORD_FILE_NAME;
    }

    public static void setEmployeeRecordFileName(String employeeRecordFileName) {
        EMPLOYEE_RECORD_FILE_NAME = employeeRecordFileName;
    }

    public static String getPublicHolidayListFile() {
        return PUBLIC_HOLIDAY_LIST_FILE;
    }

    public static Month getMONTH() {
        return MONTH;
    }

    public static void setMONTH(Month MONTH) {
        ProjectConstants.MONTH = MONTH;
    }

    public static Year getYEAR() {
        return YEAR;
    }

    public static void setYEAR(Year YEAR) {
        ProjectConstants.YEAR = YEAR;
    }


}
