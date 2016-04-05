package core.combinedModel;

import core.model.*;

/**
 * Created by kumars on 3/11/2016.
 */
public class OnlyDiscrepancyDetailsJson extends ListGenerator {
    @Override
    public void generate() {
        JSONModelForWeb jsonModelForWeb;
        for (FinalModel f : allEmpDetails.values()) {
            if (f.getIfClarificationNeeded()) {
                jsonModelForWeb = new JSONModelForWeb(f, "Discrepancy");
                filteredEmpDetails.put(f.getEmpId(), jsonModelForWeb);
            }
        }
    }
}
