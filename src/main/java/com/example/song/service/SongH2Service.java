/*
 * 
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here

package com.example.song.service;

import com.example.song.model.Song;
import com.example.song.model.SongRowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;

@Service
public class SongH2Service {

    @Autowired
    private JdbcTemplate db;

    public ArrayList<Song> getSongs() {
        return (ArrayList<Song>) db.query("select * from PLAYLIST", new SongRowMapper());
    }

    public Song getSongById(int songId) {
        try {
            return db.queryForObject("select * from PLAYLIST where songId = ?", new SongRowMapper(), songId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Song addSong(Song song) {
        db.update("insert into PLAYLIST(songName, lyricist, singer,musicDirector) values (?,?,?,?)", song.getSongName(),
                song.getLyricist(), song.getSinger(),song.getMusicDirector());
        return db.queryForObject("select * from PLAYLIST where songName = ? and lyricist = ? and singer = ? and musicDirector = ? ", new SongRowMapper(),
                song.getSongName(), song.getLyricist(),song.getSinger(),song.getMusicDirector());
    }
    

    public void deleteSong(int songId) {
        db.update("delete from PLAYLIST where songId = ?", songId);
    }

    public Song updateSong(int songId, Song song) {
        if (song.getSongName() != null) {
            db.update("update PLAYLIST set songName = ? where songId =?", song.getSongName(), songId);
        }
        if (song.getLyricist() != null) {
            db.update("update PLAYLIST set lyricist = ? where songId = ?", song.getLyricist(), songId);
        }
        if (song.getSinger() != null) {
            db.update("update PLAYLIST set  singer= ? where songId = ?", song.getSinger(), songId);
        }
        if (song.getMusicDirector() != null) {
            db.update("update PLAYLIST set  musicDirector = ? where songId = ?", song.getMusicDirector(), songId);
        }
        return getSongById(songId);
    }

}

