package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SongActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private MusicListDb db;

    private String nmAuthor;
    private int count;

    public static final String EXTRA_ID_AUTHOR = "alex.aleksandr.ru.musicguitar.extra_id_author";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        nmAuthor = getIntent().getStringExtra(EXTRA_ID_AUTHOR);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(nmAuthor);
        }

        db = new MusicListDb(this);
    }

    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.song_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = db.querySelectSong(MusicListDb.getSongAuthor() + "= ?", new String[]{nmAuthor});
        count = cursor.getCount();
        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
    }


    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(SongActivity.this, SongTextActivity.class);
            Cursor cursor = db.querySelectSong(MusicListDb.getSongAuthor() + "= ? AND "
                    + MusicListDb.getSongName() + "= ?", new String[]{nmAuthor, textView.getText().toString()});
            cursor.moveToFirst();
            long idSong = cursor.getLong(cursor.getColumnIndex("_id"));
            i.putExtra(SongTextActivity.EXTRA_ID_SONG_ID, idSong);
            i.putExtra(SongTextActivity.EXTRA_ID_SONG_COUNT, count);
            startActivity(i);
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

        private Cursor cursor;

        private RecyclerAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            cursor.moveToPosition(position);
            String nameAuthor = cursor.getString(cursor.getColumnIndex(MusicListDb.getSongName()));
            holder.textView.setText(nameAuthor);
        }

        @Override
        public int getItemCount() {
            int count = cursor.getCount();
            if (count == 0) {
                finish();
            }
            return count;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
