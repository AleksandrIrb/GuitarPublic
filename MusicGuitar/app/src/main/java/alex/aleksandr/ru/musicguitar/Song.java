package alex.aleksandr.ru.musicguitar;

import android.database.Cursor;


public class Song {

    private String name;
    private long idSong;
    private String authorName;


    private Song(long idSong, String name, String authorName) {
        this.name = name;
        this.idSong = idSong;
        this.authorName = authorName;
    }

    static Song fromCursor(Cursor song) {

        String name = song.getString(song.getColumnIndex(MusicListDb.getSongName()));
        long idSong = song.getLong(song.getColumnIndex("_id"));
        String authorName = song.getString(song.getColumnIndex(MusicListDb.getSongAuthor()));

        return new Song(idSong, name, authorName);
    }

    public long getIdSong() {
        return idSong;
    }

    String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }
}
