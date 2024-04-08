package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired FileMapper fileMapper;

    @Autowired UserService userService;

    public void insertFile(MultipartFile fileUpload, String username, Model model) throws IOException {

        if(fileUpload.isEmpty()) {
            model.addAttribute("emptyFile", "Sorry! the file that you upload is empty.");
            return;
        }

        File file = new File();
        Integer userId = userService.getUserId(username);

        try {
            file.setContentType(fileUpload.getContentType());
            file.setFileData(fileUpload.getBytes());
            file.setFileName(fileUpload.getOriginalFilename());
            file.setSize(Long.toString(fileUpload.getSize()));
            file.setUserId(userId);
        } catch (IOException e) {
            throw e;
        }

        if(isDuplicated(file.getFileName(), username)){
            model.addAttribute("duplicateFile", "Sorry! the file already uploaded.");
            return;
        }

        int rowAdded = fileMapper.insertFile(file);
        if( rowAdded > 0) {
            model.addAttribute("success", true);
            return;
        }
        model.addAttribute("resultError", "Sorry! something went wrong while you uploading the file.");
    }

    private boolean isDuplicated(String fileName, String username) {
        Integer userId = userService.getUserId(username);
        return fileMapper.getFile(fileName, userId) != null;
    }

    public List<File> getFiles(String username){
        Integer userId = userService.getUserId(username);
        return fileMapper.getAllFiles(userId);
    }

    public void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }

    public File getFileById(Integer fileId){
        return fileMapper.getFileById(fileId);
    }

}
