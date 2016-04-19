package servlets.filegenerator.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import servlets.filegenerator.FileCreatorModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

import static core.model.ProjectConstants.FILE_PATH;

/**
 * Created by kumars on 4/11/2016.
 */
public class CreateMultipleRecordPDF extends DataParserForPDF {

    public static Document createPDF(String fileName, List<String> listOfIds, String fileToUse) {
        Document document = null;
        FileCreatorModel ob;
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);

            if (listOfIds.get(0) == null) {
                try {
                    JSONParser parser = new JSONParser();
                    Object a = parser.parse(new FileReader(FILE_PATH + "JsonFiles\\" + fileToUse + ".json"));
                    JSONObject jsonObject = (JSONObject) a;
                    Set s = jsonObject.keySet();

                    for (Object value : s) {
                        ob = new FileCreatorModel();
                        addData(jsonObject, ob, (String) value);

                        addTitlePage(document, ob, fileToUse);

                        createTable(document, ob);
                        document.newPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                DataParserForPDF.setMultipleObject(listOfIds, document, fileToUse);

            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }


}
