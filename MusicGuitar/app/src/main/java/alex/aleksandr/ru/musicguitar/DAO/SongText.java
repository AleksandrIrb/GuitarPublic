package alex.aleksandr.ru.musicguitar.DAO;


import android.database.Cursor;

import alex.aleksandr.ru.musicguitar.MusicListDb;


public class SongText {

    private String name;
    private long idSong;
    private String authorName;
    private String textSong;

    public static SongText fromCursor(Cursor song) {
        String name = song.getString(song.getColumnIndex(MusicListDb.getSongName()));
        long idSong = song.getLong(song.getColumnIndex("_id"));
        String authorName = song.getString(song.getColumnIndex(MusicListDb.getSongAuthor()));
        String textSong = song.getString(song.getColumnIndex(MusicListDb.getSongText()));
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