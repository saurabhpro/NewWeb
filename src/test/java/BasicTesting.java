/*
 * Copyright (c) 2017.
 */

import servlets.main.BackEndLogicHelperUtility;

/**
 * Created by saurabhkumar on 28/03/17.
 */
public class BasicTesting {
	public static void main(String[] args) {
		BackEndLogicHelperUtility.readDataFromSourcesToInitialObjects();

		BackEndLogicHelperUtility.prepareFinalObject();

	}
}
