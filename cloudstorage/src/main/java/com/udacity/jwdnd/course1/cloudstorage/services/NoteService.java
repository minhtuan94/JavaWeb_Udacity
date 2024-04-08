package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class NoteService {

    @Autowired UserService userService;

    @Autowired NoteMapper noteMapper;

    public List<Notes> listAllNotes(String username){
        Integer userId = userService.getUserId(username);
        return noteMapper.getAllNotesByUserId(userId);
    }

    public void addNewNote(String username, Model model, Notes notes) {
        Integer userId = userService.getUserId(username);
        notes.setUserId(userId);
        try {
            if (checkUpdate(notes.getNoteId())){
                noteMapper.updateNote(notes);
                model.addAttribute("success", true);
            } else {
                int newNote = noteMapper.insertNote(notes);
                if (newNote > 0) {
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

    public boolean checkUpdate(Integer noteId){
        return noteMapper.getNoteById(noteId) != null;
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }

}
