package core.view;

import core.model.viewmodal.FinalObjectModel;
import core.model.viewmodal.ListGeneratorModel;
import core.model.viewmodal.WebJSONModel;

import java.util.TreeMap;

/**
 * Created by kumars on 3/11/2016.
 */
public class AllEmployeeDetailsJson extends ListGeneratorModel {
    @Override
    public void generate() {
        WebJSONModel webJSONModel;
        filteredEmpDetails = new TreeMap<>();
        for (FinalObjectModel f : allEmpDetails.values()) {

            webJSONModel = new WebJSONModel(f);
            filteredEmpDetails.put(f.getEmpId(), webJSONModel);

        }
    }

}
