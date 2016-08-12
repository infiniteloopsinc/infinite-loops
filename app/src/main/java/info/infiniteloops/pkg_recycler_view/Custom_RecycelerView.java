package info.infiniteloops.pkg_recycler_view;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.futuremind.recyclerviewfastscroll.FastScrollBubble;
import com.futuremind.recyclerviewfastscroll.FastScroller;

import java.util.ArrayList;
import java.util.List;

import info.infiniteloops.R;

public class Custom_RecycelerView extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView recyclerview;
    ExpandableListAdapter mAdapter;
    List<ExpandableListAdapter.Item> data;
    FastScroller fastScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rcycler_view);

        recyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        data = new ArrayList<>();

        for(int i=0;i<100;i++) {
            ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Topic "+i);

            places.invisibleChildren = new ArrayList<>();
            places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Child "+i));
            data.add(places);

        }
        recyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new ExpandableListAdapter(data);

        recyclerview.setAdapter(mAdapter);
        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerview);

    }

    public FastScroller getFastScroller(){
        return fastScroller;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return true;
    }
    @Override
    public boolean onQueryTextChange(String query) {
        final List<ExpandableListAdapter.Item> filteredModelList = filter(data, query);
        mAdapter.animateTo(filteredModelList);
        recyclerview.scrollToPosition(0);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private List<ExpandableListAdapter.Item> filter(List<ExpandableListAdapter.Item> models, String query) {
        query = query.toLowerCase();

        final List<ExpandableListAdapter.Item> filteredModelList = new ArrayList<>();
        for (ExpandableListAdapter.Item model : models) {
            final String text = model.text.toString().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
