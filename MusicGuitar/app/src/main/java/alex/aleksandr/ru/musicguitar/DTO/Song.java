package alex.aleksandr.ru.musicguitar.DTO;

import android.database.Cursor;

import alex.aleksandr.ru.musicguitar.MusicDb;


public class Song {

    private String name;
    private long idSong;
    private String authorName;

    public static Song fromCursor(Cursor song) {
        String name = song.getString(song.getColumnIndex(MusicDb.getSongName()));
        long idSong = song.getLong(song.getColumnIndex("_id"));
        String authorName = song.getString(song.getColumnIndex(MusicDb.getSongAuthor()));
        return new Song(idSong, name, authorName);
    }

    private Song(long idSong, String name, String authorName) {
        this.name = name;
        this.idSong = idSong;
        this.authorName = authorName;
    }

    public long getIdSong() {
        return idSong;
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }
}
