package core.view;

import core.model.FinalObjectModel;
import core.model.ListGeneratorModel;
import core.model.WebJSONModel;

/**
 * Created by kumars on 3/11/2016.
 */
public class OnlyDiscrepancyDetailsJson extends ListGeneratorModel {
    @Override
    public void generate() {
        WebJSONModel webJSONModel;
        for (FinalObjectModel f : allEmpDetails.values()) {
            if (f.getIfClarificationNeeded()) {
                webJSONModel = new WebJSONModel(f, "Discrepancy");
                filteredEmpDetails.put(f.getEmpId(), webJSONModel);
            }
        }
    }
}
