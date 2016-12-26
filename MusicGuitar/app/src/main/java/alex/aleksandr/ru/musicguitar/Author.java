package alex.aleksandr.ru.musicguitar;

import android.database.Cursor;


public class Author {

    private String name;
    private long id;

    static Author fromCursor(Cursor c) {
        String name = c.getString(c.getColumnIndex(MusicListDb.getAuthorName()));
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

    String getName() {
        return name;
    }

}