package servlets.upload;

import com.oreilly.servlet.MultipartRequest;
import core.utils.FileFolderWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static core.model.ProjectConstants.ALL_EMPLOYEE_RECORD_FILE_PATH;

/**
 * Created by AroraA on 07-03-2016.
 */
@WebServlet(name = "servlets.upload.EmailListServlet", urlPatterns = {"/upEmailList"})
public class EmailListServlet extends HttpServlet {
    MultipartRequest m;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    String filename;

    public String getEmailName() {
        return emailName;
    }

    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    String emailName;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        File emailListFilePath = new File(ALL_EMPLOYEE_RECORD_FILE_PATH);

        String tmp = (String) request.getAttribute("emailListFile");
        System.out.println(tmp);
        if (!emailListFilePath.exists()) {
            FileFolderWorker.makeDirectory(emailListFilePath);
        } else {
            FileFolderWorker.cleanDirectory(emailListFilePath);
        }
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        /**
         * Constructs a new MultipartRequest to handle the specified request, saving any uploaded files to
         * the given directory, and limiting the upload size to the specified length. If the content is too large,
         * an IOException is thrown. This constructor actually parses the multipart/form-data and throws
         * an IOException if there's any problem reading or parsing the request.
         */
        m = new MultipartRequest(request, emailListFilePath.toString(), (1024 * 1024 * 2));
        filename = m.getFilesystemName("emailListFile");
        session.setAttribute("emailName", filename);
        //System.out.println(nameForUI);
        System.out.println(filename);
        // out.println(filename + "Successfully Uploaded");

        response.sendRedirect("./MainPage.jsp#/UploadFiles");
    }
}


/**
 * The com.oreilly.servlet.multipart package contains a FileRenamePolicy interface that can be used when you want to implement a particular file-renaming policy with file uploads.
 * <p>
 * The DefaultFileRenamePolicy class renames an uploaded file whose name conflicts with an existing file by adding a number to the uploaded filename. For example, if index.txt already exists, then the DefaultFileRenamePolicy class renames the uploaded file index1.txt; and if a second file is uploaded with the same name it will be renamed index2.txt, and so on.
 * <p>
 * If you want to implement your own renaming policy, then create your own class that implements the FileRenamePolicy interface, then implement the class's rename(java.io.File file) method to initiate the renaming action.
 * <p>
 * This code sample shows a MultipartRequest constructor from Example 8-3. This time, the constructor adds a DefaultFileRenamePolicy object as a constructor parameter:
 */