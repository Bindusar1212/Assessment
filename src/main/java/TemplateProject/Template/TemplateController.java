package TemplateProject.Template;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadTemplate() {
        Resource templateFile = templateService.generateTemplateFile();
        
        return ResponseEntity.ok()
            .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=template.xlsx")
            .body(templateFile);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile file) {
        templateService.processTemplate(file);
        return ResponseEntity.ok("Template uploaded and processed successfully.");
    }
}