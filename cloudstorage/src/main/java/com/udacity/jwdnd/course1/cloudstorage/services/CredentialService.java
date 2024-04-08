package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    @Autowired UserService userService;

    @Autowired CredentialMapper credentialMapper;

    @Autowired EncryptionService encryptionService;

    public List<Credentials> listAllCredentials(String username){
        Integer userId = userService.getUserId(username);
        return credentialMapper.getAllCredentialByUserId(userId);
    }

    public void addNewCredential(String username, Model model, Credentials credentials){

        Integer userId = userService.getUserId(username);
        credentials.setUserId(userId);
        credentials.setKey(encodeKey());
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), credentials.getKey());
        credentials.setPassword(encryptedPassword);

        try {
            if (checkForUpdate(credentials.getCredentialId())) {
                credentialMapper.updateCredential(credentials);
                model.addAttribute("success", true);
            } else {
                int newCredential = credentialMapper.insertCredential(credentials);
                if (newCredential > 0) {
                    model.addAttribute("success", true);
                    return;
                } else {
                    model.addAttribute("resultError", "Sorry! something went wrong while you add new note.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String encodeKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    public boolean checkForUpdate(Integer credentialId){
        return credentialMapper.getCredentialById(credentialId) != null;
    }

    public void deleteCredential(Integer credentialId) {
        credentialMapper.deleteCredential(credentialId);
    }

    public String decryptedPassword(Credentials credentials) {
        String decryptedPassword = encryptionService.decryptValue(credentials.getPassword(), credentials.getKey());
        return decryptedPassword;
    }

}
