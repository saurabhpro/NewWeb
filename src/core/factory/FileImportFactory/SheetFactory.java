package core.factory.fileimportfactory;

import core.appfilereader.BiometricFileWorker;
import core.appfilereader.HrnetFileWorker;
import core.model.appfilereadermodal.FileOperations;

/**
 * Created by Saurabh on 3/3/2016.
 */
public class SheetFactory {
	public FileOperations dispatch(String type, String file) {

		switch (type) {
			case "Jxcel":
				return new BiometricFileWorker(file);
			case "XLS":
				// return new ApacheBiometricFileWorker(file);
				System.out.println("Not yet supported XLS by apache");
				break;
			case "XLSX":
				return new HrnetFileWorker(file);
		}
		return null;
	}

}
