package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;


public class AuthorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private MusicListDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthorActivity.this, EditActivity.class);
                startActivity(i);

            }
        });


        db = new MusicListDb(this);
        db.getWritableDatabase();


        /*try {

            for (int i = 0; i < 15; i++) {
                db.addAuthor("NEW AUTHOR_" + i);
            }
        } catch (SQLException e) {
            Log.w("Warn", "Такие записи есть");

        } finally { */




    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) findViewById(R.id.author_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Cursor cursor = db.querySelectAuthor(null,null);
        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_author, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(AuthorActivity.this, AboutAppActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
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
            Intent i = new Intent(AuthorActivity.this, SongActivity.class);
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
            String nameAuthor = cursor.getString(cursor.getColumnIndex(MusicListDb.getAuthorName()));
            holder.textView.setText(nameAuthor);
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }
    }

}
