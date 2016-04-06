package core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.combined.MarkDiscrepancy;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Saurabh on 3/12/2016.
 */
public abstract class ListGeneratorModel {
    protected final Map<String, FinalObjectModel> allEmpDetails = MarkDiscrepancy.EmpCombinedMap;
    protected final Map<String, WebJSONModel> filteredEmpDetails = new TreeMap<>();

    public abstract void generate();

    public void displayOnConsole() {
        filteredEmpDetails.values().forEach(WebJSONModel::displayAllDates);
    }

    public void createJSONList(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        // For testing
        Map<String, WebJSONModel> user = filteredEmpDetails;

        try {
            File jfile = new File("C:\\Users\\kumars\\IdeaProjects\\NewWeb\\web\\json\\" + fileName + ".json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
