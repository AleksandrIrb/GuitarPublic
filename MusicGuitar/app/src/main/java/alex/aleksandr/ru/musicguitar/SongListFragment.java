package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alex on 18.12.16.
 */

public class SongListFragment extends Fragment {

    public static final String EXTRA_NAME_AUTHOR_FRAGMENT = "alex.aleksandr.ru.musicguitar.name_author_fragment";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Cursor cursor;
    private Author author;
    private String nameAuthor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        nameAuthor = getArguments().getString(EXTRA_NAME_AUTHOR_FRAGMENT);
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        MusicListDb db = MusicListDb.getMusicDataBase(getActivity());
        cursor = db.querySelectSong(MusicListDb.getSongAuthor() + "= ?", new String[]{nameAuthor});
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_song_recycler_view);

        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

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
            Intent i = new Intent(getActivity(), SongTextActivity.class);
            i.putExtra(SongTextActivity.EXTRA_ID_SONG, Song.fromCursor(cursor).getIdSong());
            i.putExtra(SongTextActivity.EXTRA_SONG_COUNT, cursor.getCount());
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
            View view = LayoutInflater.from(getActivity())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            cursor.moveToPosition(position);
            holder.textView.setText(Song.fromCursor(cursor).getName());
        }

        @Override
        public int getItemCount() {
            int count = cursor.getCount();
            if (count == 0) {
                getActivity().finish();
            }
            return count;
        }
    }


}
