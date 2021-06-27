import com.sun.org.apache.bcel.internal.classfile.Constant;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Main {

    // Initializing a Dictionary
    public static Map<Integer, String> dict_PDF_Content = new HashMap<Integer, String>();

    public static void main(String[] args)
    {
        String reqTextInPDF="B2CNOTI Case4201905280738396";
        String pdfFile="H:\\WORKSPACE\\PDF_Automation\\Test.pdf";


        //Verification Text
        String expAmountDue="Amount due: € 305.29";
        String expDueDate="Due date: 12/07/2019";
        String expBaseBankNumber="BASE bank account number: XXXXXXX";
        String expAccountName="Account name: TEST NAME";

        readPDFContent(pdfFile);

        int dictSize=dict_PDF_Content.size();

        /// Verify the expected with Actual
        for(int j=1;j<=dictSize;j++)
        {
            String actualText = dict_PDF_Content.get(j);
            if(actualText.equalsIgnoreCase(expAmountDue))
            {
                System.out.println("Amount is validated successfully, " + expAmountDue);
            }
            else if(actualText.equalsIgnoreCase(expDueDate))
            {
                System.out.println("Due Date is validated successfully, " + expDueDate);
            }
            else if(actualText.equalsIgnoreCase(expBaseBankNumber))
            {
                System.out.println("Base Bank Number is validated successfully, " + expBaseBankNumber);
            }
            else if(actualText.equalsIgnoreCase(expAccountName))
            {
                System.out.println("Account Name is validated successfully, " + expAccountName);
            }

        }

        System.out.println("*******************************************************************************");
        for (Integer keys : dict_PDF_Content.keySet())
        {
            System.out.println(dict_PDF_Content.get(keys));
        }

    }

    public static void readPDFContent(String pdfFile)
    {
        int i=0;

        try (PDDocument document = PDDocument.load(new File(pdfFile))) {

            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

                dict_PDF_Content.clear();

                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");

                for (String line : lines)
                {
                    i=i+1;
//                    System.out.println(line);
                    dict_PDF_Content.put(i,line);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
