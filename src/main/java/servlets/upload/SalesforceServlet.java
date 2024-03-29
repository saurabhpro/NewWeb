package servlets.upload;

import com.oreilly.servlet.MultipartRequest;
import core.utils.FileFolderWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static core.model.ProjectConstants.FINANCIAL_FORCE_FILE_PATH;

/**
 * Created by AroraA on 07-03-2016.
 */
@WebServlet(name = "servlets.upload.SalesforceServlet", urlPatterns = {"/upSalesforce"})
public class SalesforceServlet extends HttpServlet {
	private String filename;
	private String salesforceName;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSalesforceName() {
		return salesforceName;
	}

	public void setSalesforceName(String salesforceName) {
		this.salesforceName = salesforceName;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		File salesforceFilePath = new File(FINANCIAL_FORCE_FILE_PATH);
		if (!salesforceFilePath.exists()) {
			FileFolderWorker.makeDirectory(salesforceFilePath);
		} else {
			FileFolderWorker.cleanDirectory(salesforceFilePath);
		}
		response.setContentType("text/html");
		//PrintWriter out = response.getWriter();
		//HttpSession session = request.getSession();
		MultipartRequest m = new MultipartRequest(request, salesforceFilePath.toString());

		// m = new MultipartRequest(request, salesforceFilePath.toString(),
		// (1024 * 1024 * 2));
		filename = m.getFilesystemName("salesforceFile");
		//session.setAttribute("salesforceName", filename);
		// System.out.println(nameForUI);
		System.out.println(filename);

		Cookie cookie = new Cookie("salesforceFile", filename);
		cookie.setMaxAge(3600 * 24 * 365 * 5);
		response.addCookie(cookie);

		//out.println(filename + "Successfully Uploaded");

		response.sendRedirect("./MainPage.jsp#/UploadFiles");
	}
}
