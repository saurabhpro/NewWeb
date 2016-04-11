package filegen;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by kumars on 4/5/2016.
 */
public class CreateSingleRecordPDF extends DataParserForPDF {

    public static Document createPDF(String fileName, String key, String fileToUse) {
        Document document = null;
        FileCreatorModel ob = new FileCreatorModel();
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            addMetaData(document);

            setSingleObjectData(key, ob, fileToUse);

            addTitlePage(document, ob, fileToUse);

            createTable(document, ob);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }


}
