package tn.esprit.claimfacturesservice.Service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.claimfacturesservice.Entities.Facture;
import tn.esprit.claimfacturesservice.Repository.FactureRepository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class pdf {

@Autowired
FactureRepository factureRepository;
    public void createPdf() {
        String filePdf = "C:/Users/TouTa/OneDrive/Desktop/pdf/facturepdffile.pdf";
//        ArrayList<Student> studentList = new ArrayList();
//        Student s1 = new Student(1,"Sachine","Sachine@gmail.com");
//        Student s2 = new Student(2, "Rahul","Rahul@gmail.com");
//        studentList.add(s1);
//        studentList.add(s2);
        List<Facture> factureList = factureRepository.findAll();
        try {
            PdfWriter writer = new PdfWriter(filePdf);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            Paragraph p1 = new Paragraph("Reservation List");
            float[] columnWidth = {200f, 200f, 200f, 200f};
            Table table = new Table(columnWidth);
            table.addCell(new Cell().add("IdFacture"));
            table.addCell(new Cell().add("Totale Price "));
            table.addCell(new Cell().add("Datefacture"));
            table.addCell(new Cell().add("Reference"));
           // table.addCell(new Cell().add("Delivery Price"));
           // table.addCell(new Cell().add("Delivery Adress"));
           // table.addCell(new Cell().add("getDelivery Men"));
            //

            for (Facture factureData : factureList) {
                table.addCell(new Cell().add(String.valueOf((factureData.getIdFacture()))));
                table.addCell(new Cell().add(String.valueOf(factureData.getPriceTotal())));
                table.addCell(new Cell().add(String.valueOf(factureData.getDatefacture())));
                table.addCell(new Cell().add(String.valueOf(factureData.getReference())));
              // table.addCell(new Cell().add(String.valueOf(factureData.getDelivery().getDeliveryPrice())));
              //  table.addCell(new Cell().add(String.valueOf(factureData.getDelivery().getAdress())));
              //  table.addCell(new Cell().add(String.valueOf(factureData.getDelivery().getDeliveryMen())));
            }

            document.add(p1);
            document.add(table);
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
