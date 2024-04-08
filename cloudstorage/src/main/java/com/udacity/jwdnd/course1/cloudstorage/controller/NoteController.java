package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {

    @Autowired NoteService noteService;


    @PostMapping("/addNote")
    public String addNote(@ModelAttribute("note") Notes notes, Model model, Authentication authentication){
        String username = authentication.getName();
        noteService.addNewNote(username, model, notes);
        return "result";
    }

//    @PostMapping("/updateNote")
//    public String updateNote(@RequestParam("noteId") Integer noteId, @ModelAttribute("note") Notes notes, Model model, Authentication authentication){
//        noteService.updateNote(noteId, model);
//        return "result";
//    }

    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam("noteId") Integer noteId, @ModelAttribute("note") Notes notes, Model model){
        noteService.deleteNote(noteId);
        model.addAttribute("deleteSuccess", true);
        return "result";
    }
}
