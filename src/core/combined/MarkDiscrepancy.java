package core.combined;

import core.model.viewmodal.FinalObjectModel;

/**
 * Created by AroraA on 17-02-2016.
 *
 * @author Amrita & Saurabh
 * @version 1.0
 *          Class to find discrepancy and set the discrepancy flag
 */

public class MarkDiscrepancy extends FinalObject {

    /**
     * Method to find discrepancy and set the discrepancy flag
     *
     * @see MarkDiscrepancyHelperUtility
     */
    @Override
    public final void findDiscrepancy() {
        // MarkDiscrepancy if an employeemodal is absent and there is no entry in Hrnet file.
        for (FinalObjectModel finalObjectModel : EmpCombinedMap.values())

            if (finalObjectModel.employeeHrnetDetails == null)
                MarkDiscrepancyHelperUtility.setIfAbsentButNoLeaveApplied(finalObjectModel);
            else /*case where there is an entry*/
                MarkDiscrepancyHelperUtility.setIfEntryPresentInEither(finalObjectModel);
    }
}
