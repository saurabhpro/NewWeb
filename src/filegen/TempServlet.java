package filegen;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by AmritaArora on 4/9/2016.
 */

@WebServlet(name = "TempServlet", urlPatterns = {"/temp"})
public class TempServlet extends HttpServlet {
    String[] filteredArray;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("hello");
        filteredArray= request.getParameterValues("filteredArray");
        for(int i=0; i<filteredArray.length;i++)
            System.out.println(filteredArray[i]);
        //System.out.println(Arrays.toString(filteredArray));
    }
}
