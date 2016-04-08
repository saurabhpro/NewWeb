package core.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Month;
import java.time.Year;

/**
 * Created by Saurabh on 4/8/2016.
 */
public class ProjectConstants {

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



    private static String BIOMETRIC_FILE_NAME;
    private static String FINANCIAL_FORCE_FILE_NAME;
    private static String EMPLOYEE_RECORD_FILE_NAME;
    private static String PUBLIC_HOLIDAY_LIST_FILE = "NOT AVAILABLE FOR NOW";

    public static String BIOMETRIC_FILE_PATH=".\\ExcelFiles\\march 8.xls";
    public static String FINANCIAL_FORCE_FILE=".\\ExcelFiles\\March FF report Final.xlsx";
    public static String ALL_EMPLOYEE_RECORD_FILE=".\\ExcelFiles\\Emails.xlsx";
    @NotNull
    @Contract(pure = true)
    public static String getAllEmployeeListFile() {
        return "AllWorkers";
    }
    @NotNull
    @Contract(pure = true)
    public static String getDiscrepancyListFile() {
        return "DiscrepancyInWorkers";
    }
    @NotNull
    @Contract(pure = true)
    public static String getWeekendWorkersListFile() {
        return "WeekendWorkers";
    }
    @NotNull
    @Contract(pure = true)
    public static String getPublicHolidayWorkersListFile() {
        return "PublicHolidayWorkers";
    }

    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static Year YEAR;
    public static Month MONTH;


}
