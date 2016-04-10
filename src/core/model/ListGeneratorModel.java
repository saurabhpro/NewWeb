package core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.combined.MarkDiscrepancy;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by Saurabh on 3/12/2016.
 */
public abstract class ListGeneratorModel {
    protected final Map<String, FinalObjectModel> allEmpDetails = MarkDiscrepancy.EmpCombinedMap;
    protected Map<String, WebJSONModel> filteredEmpDetails;

    public abstract void generate();

    public void displayOnConsole() {
        filteredEmpDetails.values().forEach(WebJSONModel::displayAllDates);
    }

    public void createJSONList(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        Map<String, WebJSONModel> user = filteredEmpDetails;
        try {
            File jfile = new File(FILE_PATH + "JsonFiles\\" + fileName + ".json");
            /*
            System.out.println(jfile.getAbsolutePath());
            System.out.println(jfile.getCanonicalPath());
            System.out.println(jfile.getParent());
             */

            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
