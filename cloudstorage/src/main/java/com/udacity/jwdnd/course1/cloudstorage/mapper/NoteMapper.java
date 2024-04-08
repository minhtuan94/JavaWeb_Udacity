package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Notes> getAllNotesByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Notes getNoteById(Integer noteId);

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Notes notes);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    void updateNote(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);

}
