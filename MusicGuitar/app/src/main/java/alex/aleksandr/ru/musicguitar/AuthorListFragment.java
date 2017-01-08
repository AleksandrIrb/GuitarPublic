package alex.aleksandr.ru.musicguitar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import alex.aleksandr.ru.musicguitar.DAO.Author;

import static alex.aleksandr.ru.musicguitar.AuthorActivity.EXTRA_NAME_AUTHOR;

/**
 * Created by alex on 07.01.17.
 */

public class AuthorListFragment extends Fragment {

    public static final String EXTRA_IS_LAND_ORIENTATION = "alex.aleksandr.ru.musicguitar.is_land_orientation";
    public static final String EXTRA_SEARCH_FILTER = "alex.aleksandr.ru.musicguitar.search_filter";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private Cursor cursor;
    private boolean frameForSong;
    private SongListFragment songListFragment = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_author, container, false);
        MusicListDb db = MusicListDb.getMusicDataBase(getActivity());
        frameForSong = getArguments().getBoolean(EXTRA_IS_LAND_ORIENTATION);
        String filterSearch = getArguments().getString(EXTRA_SEARCH_FILTER);
        if(filterSearch.isEmpty()) {
            cursor = db.queryAuthorByName();
        } else {
            cursor = db.queryAuthorByNameFilter(filterSearch);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_author_recycler_view);
        recyclerAdapter = new RecyclerAdapter(cursor);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView;
        private Author author;

        private RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView;
        }

        @Override
        public void onClick(View view) {

            if(frameForSong) {

                FragmentManager fragmentManager = getFragmentManager();
                if(songListFragment != null)
                {
                    fragmentManager.beginTransaction().
                            remove(songListFragment).commit();
                }
                songListFragment = new SongListFragment();
                fragmentManager.beginTransaction().
                        add(R.id.frame_layout_for_song_in_author, songListFragment).commit();
                Bundle bundle = new Bundle();
                bundle.putString(SongListFragment.EXTRA_NAME_AUTHOR_FRAGMENT, author.getName());
                songListFragment.setArguments(bundle);
            } else {
                Intent i = new Intent(getActivity(), ContainerActivity.class);
                i.putExtra(EXTRA_NAME_AUTHOR, author.getName());
                startActivity(i);
            }
        }
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

        private Cursor cursor;

        private RecyclerAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            cursor.moveToPosition(position);
            Author author = Author.fromCursor(cursor);
            holder.textView.setText(author.getName());
            holder.author = author;
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }
    }
}
