package com.example.demo.attachment;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public record AttachmentService(
        AttachmentRepository attachmentRepository) {


    public Attachment add(MultipartFile file) throws IOException {
        try {
            return attachmentRepository.save(uploadSystem(file));
        } catch (Exception e) {
            return null;
        }
    }

        public static final String uploadDirectories = "files";
//    public static final String uploadDirectories = "C:\\Users\\User\\Documents\\GitHub/files";

    public Attachment uploadSystem(MultipartFile file) throws IOException {


        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            Attachment attachment = new Attachment();


            if (originalFilename == null) {

                originalFilename = "123456";
            }

            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());


            //FILENING CONTENTINI OLISH UCHUN KERAK
            String[] split = originalFilename.split("\\.");

            //rasm nameni unique qilish uchun kerak
            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
            attachment.setName(name);

            //papka saqlanadigan yo'l
            Path path = Paths.get(uploadDirectories + "/" + name);

            attachment.setPath(path.toString());

            Attachment saveAttachment = attachmentRepository.save(attachment);

            Files.copy(file.getInputStream(), path);

            return saveAttachment;
        }
        return null;
    }

    public Attachment get(Integer id) {
        return attachmentRepository.findById(id).get();
    }

    public List<Attachment> getAll(){
        return attachmentRepository.findAll();
    }
}
