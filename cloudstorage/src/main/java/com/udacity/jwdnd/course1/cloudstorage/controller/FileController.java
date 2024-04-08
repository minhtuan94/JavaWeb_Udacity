package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class FileController {

    @Autowired
    private HttpSession session;

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) throws IOException {
        String username = authentication.getName();
        try {
            fileService.insertFile(fileUpload, username, model);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "result";
    }

    @GetMapping( value="/viewFile", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> viewFile(@RequestParam("fileId") Integer fileId) {
            File file = fileService.getFileById(fileId);
            String fileName = file.getFileName();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                    .body(file.getFileData());
    }

    @GetMapping("/delete/file")
    public String deleteFile(@RequestParam("fileId") Integer fileId, @ModelAttribute("file") File file, Model model) {
        if (fileId == null){
            model.addAttribute("resultError");
            return "result";
        } else {
            fileService.deleteFile(fileId);
            model.addAttribute("success", true);
            return "result";
        }
    }
}
