package com.example.demo.attachment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/test/api/img")
public class AttachmentController {

private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentRepository attachmentRepository, AttachmentService attachmentService) {
        this.attachmentRepository = attachmentRepository;
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


    @GetMapping("/image/{id}")
    public void getImageDynamicType(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            response.setHeader("Content-Disposition","attachment; filename=\""+attachment.getFileOriginalName()+"\"");
            response.setContentType(attachment.getContentType());
            FileInputStream fileInputStream=new FileInputStream("/home/uploads"+"/"+attachment.getName());
            FileCopyUtils.copy(fileInputStream,response.getOutputStream());
        }
    }


}
