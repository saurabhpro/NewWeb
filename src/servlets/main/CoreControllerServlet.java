package servlets.main;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kumars on 4/5/2016.
 */
@WebServlet(name = "CoreControllerServlet", urlPatterns = {"/core"})
public class CoreControllerServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BackendLogicHelperUtility.readDataFromSourcesToInitialObjects();

		BackendLogicHelperUtility.prepareFinalObject();

		BackendLogicHelperUtility.generateReportsJson();

		// temporary function to copy json files generated and stored in web
		// local folder to artifact out folder
		//FileFolderWorker.copyFromWebToArtifacts();

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("MainPage.jsp");
		requestDispatcher.forward(request, response);
	}

}
