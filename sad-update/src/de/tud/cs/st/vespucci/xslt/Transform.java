package de.tud.cs.st.vespucci.xslt;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transform {

	/**
     * XSLT-Transformation durchführen und Ergebnis
     * an System.out schicken.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println(
                "Usage: java Transform [xmlfile] [xsltfile]");
            System.exit(1);
        }

        File xmlFile = new File(args[0]);
        File xsltFile = new File(args[1]);

        // JAXP liest Daten über die Source-Schnittstelle
        Source xmlSource = new StreamSource(xmlFile);
        Source xsltSource = new StreamSource(xsltFile);

        // das Factory-Pattern unterstützt verschiedene XSLT-Prozessoren
        TransformerFactory transFact =
                TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);

//        FileOutputStream fos = new FileOutputStream("output.xml", false);
        
//        trans.transform(xmlSource, new StreamResult(fos));
        trans.transform(xmlSource, new StreamResult(System.out));
        
//        fos.close();
    }

}
