package alex.aleksandr.ru.musicguitar.DAO;

import android.database.Cursor;

import alex.aleksandr.ru.musicguitar.MusicDb;


public class Author {

    private String name;
    private long id;

    public static Author fromCursor(Cursor c) {
        String name = c.getString(c.getColumnIndex(MusicDb.getAuthorName()));
        long id = c.getLong(c.getColumnIndex("_id"));
        return new Author(id, name);
    }

    private Author(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}