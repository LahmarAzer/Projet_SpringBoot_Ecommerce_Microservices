package tn.esprit.claimfacturesservice.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.claimfacturesservice.Service.pdf;

@RestController


@RequestMapping("/claimFacture/claim")
public class GetPdfController {

    private pdf createPdfFileService;

    @Autowired
    public GetPdfController(pdf createPdfFileService) {
        this.createPdfFileService = createPdfFileService;
    }

    @GetMapping("/pdfFile")
    public String getPdf(){
        return "redirect:/templates/getPdfFile.html";
    }

    @GetMapping("/createPdf")
    public String pdfFile(){
        createPdfFileService.createPdf();
        return "redirect:/pdfFile";
    }
}