package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SongActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private MusicListDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_song);
        setSupportActionBar(toolbar);

        db = new MusicListDb(this);
        db.getWritableDatabase();

    }

    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.song_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor = db.querySelectSong(MusicListDb.getSongAuthor() + "= ?", new String[] {"new"});
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
            LayoutInflater li = getLayoutInflater();
            View view = li.inflate(android.R.layout.simple_list_item_1, parent, false);
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
            return cursor.getCount();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_edit_close) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
