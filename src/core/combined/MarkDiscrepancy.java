package core.combined;

import core.model.viewmodal.FinalObjectModel;

import java.io.Serializable;

/**
 * Created by AroraA on 17-02-2016. 6-03-2016 changed the Type from ABSENT to
 * UNACCOUNTED_ABSENCE.
 */

public class MarkDiscrepancy extends FinalObject implements Serializable {

    public void findDiscrepancy() {
        // MarkDiscrepancy if an employeemodal is absent and there is no entry in Hrnet file.
        for (FinalObjectModel finalObjectModel : EmpCombinedMap.values())
            // case where there is an entry
            if (finalObjectModel.employeeHrnetDetails == null)
                MarkDiscrepancyHelperUtility.setIfAbsentButNoLeaveApplied(finalObjectModel);
            else MarkDiscrepancyHelperUtility.setIfEntryPresentInEither(finalObjectModel);
    }
}
