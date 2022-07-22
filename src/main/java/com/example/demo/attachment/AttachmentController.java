package com.example.demo.attachment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequestMapping("test/api/img")
public class AttachmentController {


    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<?> add(MultipartHttpServletRequest request) throws IOException {
        try {

            MultipartFile files = request.getFile("files");
            Attachment add = attachmentService.add(files);

            return ResponseEntity.status(200).body(add == null ? "QO`SHILMADI" : "QO`SHILDI");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Integer id) throws IOException {
        try {
            Attachment add = attachmentService.get(id);

            return ResponseEntity.status(200).body(add);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("/files")
    public ResponseEntity<?> get (){
        try {
            return ResponseEntity.status(200).body(attachmentService.getAll());
        }
        catch (Exception e){
            return ResponseEntity.status(301).body("Ishlamadi");
        }
    }
}
