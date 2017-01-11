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

import alex.aleksandr.ru.musicguitar.DAO.Song;
import alex.aleksandr.ru.musicguitar.DAO.SongText;

/**
 * Created by alex on 18.12.16.
 */

public class SongListFragment extends Fragment {

    public static final String EXTRA_NAME_AUTHOR_FRAGMENT = "alex.aleksandr.ru.musicguitar.name_author_fragment";
    public static final String EXTRA_SEARCH_FILTER_SONG = "alex.aleksandr.ru.musicguitar.search_filter_song";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Cursor cursor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String nameAuthor = getArguments().getString(EXTRA_NAME_AUTHOR_FRAGMENT);
        String searchFilter = getArguments().getString(EXTRA_SEARCH_FILTER_SONG);
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        MusicListDb db = MusicListDb.getMusicDataBase(getActivity());
        if (searchFilter.isEmpty()) {
            cursor = db.querySongByAuthorName(nameAuthor);
        } else {
            cursor = db.querySongByAuthorNameFilter(nameAuthor, searchFilter);
        }
/*
        int count = cursor.getCount();
        if (count == 0) {
            getActivity().finish();
        }
        */
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_song_recycler_view);
        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Song song;

        private RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(getActivity(), SongTextActivity.class);
            i.putExtra(SongTextActivity.EXTRA_ID_SONG, song.getIdSong());
            startActivity(i);
            //deleteFragment();
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
            Song song = Song.fromCursor(cursor);
            holder.textView.setText(song.getName());
            holder.song = song;
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }
    }
/*
    private void deleteFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
    */

}
