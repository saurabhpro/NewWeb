package core.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.combined.CombineFile;
import core.model.viewmodal.FinalObjectModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by Saurabh on 2/14/2016.
 */
public class JsonMapper {
    private ObjectMapper mapper = new ObjectMapper();
    // For testing
    private Map<String, FinalObjectModel> user;

    public void fromJsonToFormattedJson(String jsonInString) {
        // Convert object to JSON string and pretty print
        try {
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        FileWriter file;
        try {
            file = new FileWriter(new File(FILE_PATH + "JsonFiles\\formattedJson.json"));
            file.write(jsonInString);
            // System.out.println(jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fromJsonToObject(Object objectName) {
        mapper = new ObjectMapper();

        // read JSON from a file
        try {
            mapper.readValue(new File(FILE_PATH + "JsonFiles\\user.json"), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public JsonMapper toJsonFile(String fileName) {

        // For testing
        user = CombineFile.EmpCombinedMap;

        try {
            File jfile = new File(FILE_PATH + "JsonFiles\\unformattedJson.json");
            // Convert object to JSON string and save into file directly
            mapper.writeValue(jfile, user);

            // Convert object to JSON string
            mapper.writeValueAsString(user);
            // System.out.println(jsonInString);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
