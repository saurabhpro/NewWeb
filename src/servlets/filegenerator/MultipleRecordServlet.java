package servlets.filegenerator;

import servlets.filegenerator.utils.RecordBuilderHelperUtility;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumars on 4/11/2016.
 */
@WebServlet(name = "MultipleRecordServlet", urlPatterns = {"/multigen"})
public class MultipleRecordServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final ServletContext servletContext = request.getSession().getServletContext();

		final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");

		final String temporaryFilePath = tempDirectory.getAbsolutePath();
		List<String> listOfIds = new ArrayList<>();
		String tmp = request.getParameter("listOfIds");
		String[] temp2 = tmp.substring(1, tmp.length() - 1).split(",");

		for (String t : temp2) {
			if (!t.equals(""))
				listOfIds.add(t.substring(1, t.length() - 1));
			else
				listOfIds.add(null);
		}

		/*
		 * Explanation:
		 * 
		 * .* - anything \\\" - quote (escaped) (.*) - anything (captured) \\\"
		 * - another quote .* - anything However, it's a lot easier to not use
		 * regex:
		 */
		String fileToUse = request.getParameter("fileToUse");
		String excelOrPdf = request.getParameter("DownloadButtonType");

		try {

			// for (String s : listOfIds)
			// System.out.println(s);
			RecordBuilderHelperUtility.buildMultipleRecords(listOfIds, fileToUse, excelOrPdf, temporaryFilePath, response);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

/**
 * Calling document.newPage() tells iText to place subsequent objects on a new
 * page. The new page will only actually get created when you place the next
 * object. Also, newPage() only creates a new page if the current page is not
 * blank; otherwise, it's ignored; you can use setPageBlank(false) to overcome
 * that.
 * <p>
 * Refer below link for an example: http://itextpdf.com/examples/iia.php?id=99
 */