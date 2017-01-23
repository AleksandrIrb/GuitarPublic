package alex.aleksandr.ru.musicguitar.DTO;


import android.database.Cursor;

import alex.aleksandr.ru.musicguitar.MusicDb;


public class SongText {

    private String name;
    private long idSong;
    private String authorName;
    private String textSong;

    public static SongText fromCursor(Cursor song) {
        String name = song.getString(song.getColumnIndex(MusicDb.getSongName()));
        long idSong = song.getLong(song.getColumnIndex("_id"));
        String authorName = song.getString(song.getColumnIndex(MusicDb.getSongAuthor()));
        String textSong = song.getString(song.getColumnIndex(MusicDb.getSongText()));
        return new SongText(idSong, name, textSong, authorName);
    }

    private SongText(long idSong, String name, String textSong, String authorName) {
        this.name = name;
        this.idSong = idSong;
        this.authorName = authorName;
        this.textSong = textSong;
    }


    public long getIdSong() {
        return idSong;
    }

    public String getName() {
        return name;
    }

    public String getTextSong() {
        return textSong;
    }

    public String getAuthorName() {
        return authorName;
    }
}