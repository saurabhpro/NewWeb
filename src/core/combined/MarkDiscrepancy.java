package core.combined;

import core.model.FinalObjectModel;

import java.util.Map;

/**
 * Created by AroraA on 17-02-2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */

public class MarkDiscrepancy {
    public static Map<String, FinalObjectModel> EmpCombinedMap;

    public void findDiscrepancy() {
        EmpCombinedMap = CombineFile.EmpCombinedMap;
        for (FinalObjectModel finalObjectModel : EmpCombinedMap.values()) {
            // MarkDiscrepancy if an employee is absent and there is no entry in Hrnet file.
            if (finalObjectModel.hrnetDetails == null) {
                MarkDiscrepancyHelperUtility.setIfAbsentButNoLeaveApplied(finalObjectModel);
            } else {
                // case where there is an entry
                MarkDiscrepancyHelperUtility.setIfEntryPresentInEither(finalObjectModel);
            }
        }
    }


}
