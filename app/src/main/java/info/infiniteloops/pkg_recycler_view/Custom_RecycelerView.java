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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.infiniteloopsinc.gk.adapter.ExpandableListAdapter;
import com.infiniteloopsinc.gk.externaldb.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;

public class Custom_RecycelerView extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView recyclerview;
    ExpandableListAdapter mAdapter;
    List<ExpandableListAdapter.Item> data;
    FastScroller fastScroller;
    FastScrollBubble fastScrollBubble;
    InterstitialAd interAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__series__question);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView mAdView = (AdView) findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        interAd = new InterstitialAd(this);
        interAd.setAdUnitId("ca-app-pub-9340789140623348/9474638117");
        interAd.loadAd(adRequest);
        interAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                displayInterstitial();
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Boolean b= getIntent().getBooleanExtra("isWorld",false);
        if(b) {
            quesList = databaseAccess.getAllWorldQuestions_answer();
        }else{
            quesList = databaseAccess.getAllIndianQuestions_answer();

        }
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        data = new ArrayList<>();

        for(int i=0;i<quesList.size();i++) {
            ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, quesList.get(i).getQuestion(),false,quesList.get(i).getId());

            places.invisibleChildren = new ArrayList<>();
            if(quesList.get(i).getRight_answer().toString().equals("A"))
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_one(),true,quesList.get(i).getId()));
            else
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_one(),false,quesList.get(i).getId()));

            if(quesList.get(i).getRight_answer().toString().equals("B"))
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_two(),true,quesList.get(i).getId()));
            else
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_two(),false,quesList.get(i).getId()));
            if(quesList.get(i).getRight_answer().toString().equals("C"))
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_three(),true,quesList.get(i).getId()));
            else
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_three(),false,quesList.get(i).getId()));
            if(quesList.get(i).getRight_answer().toString().equals("D"))
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_four(),true,quesList.get(i).getId()));
            else
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, quesList.get(i).getOpt_four(),false,quesList.get(i).getId()));
            data.add(places);

        }
        recyclerview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new ExpandableListAdapter(data);

        recyclerview.setAdapter(mAdapter);
        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerview);
        fastScroller.setBubbleColor(0xffff0000);
        fastScroller.setHandleColor(0xffff0000);
        fastScroller.setBubbleTextAppearance(R.style.StyledScrollerTextAppearance);

    }
    public void displayInterstitial(){
        if(interAd.isLoaded()) {
            interAd.show();
        }

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
