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
        // MarkDiscrepancy if an employee is absent and there is no entry in Hrnet file.
        for (FinalObjectModel finalObjectModel : EmpCombinedMap.values())
            // case where there is an entry
            if (finalObjectModel.employeeHrnetDetails == null)
                MarkDiscrepancyHelperUtility.setIfAbsentButNoLeaveApplied(finalObjectModel);
            else MarkDiscrepancyHelperUtility.setIfEntryPresentInEither(finalObjectModel);
    }
}
