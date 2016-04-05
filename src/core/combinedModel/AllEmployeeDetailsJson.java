package core.combinedModel;

import core.model.ListGenerator;
import core.model.FinalModel;
import core.model.JSONModelForWeb;
import core.model.ListGenerator;

/**
 * Created by kumars on 3/11/2016.
 */
public class AllEmployeeDetailsJson extends ListGenerator {
    @Override
    public void generate() {
        JSONModelForWeb jsonModelForWeb;
        for (FinalModel f : allEmpDetails.values()) {

            jsonModelForWeb = new JSONModelForWeb(f);
            filteredEmpDetails.put(f.getEmpId(), jsonModelForWeb);

        }
    }

}
