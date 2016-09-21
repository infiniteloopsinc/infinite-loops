package info.infiniteloops.pkg_recycler_view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import info.infiniteloops.R;

public class ThreeRV extends AppCompatActivity {
    private RecyclerView vr_mRecyclerView, hr_mRecyclerView, vrb_mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayoutManager layoutManager;
    private static String LOG_TAG = "Simple_recycler_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_rv);

        //First Head RV
        vr_mRecyclerView = (RecyclerView) findViewById(R.id.vr_recycler_view);
        vr_mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        vr_mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        vr_mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        vr_mRecyclerView.addItemDecoration(itemDecoration);

        //Second HR RV

        hr_mRecyclerView = (RecyclerView) findViewById(R.id.hr_recycler_view);
        hr_mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        hr_mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        hr_mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecorationhr =
                new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL);
        hr_mRecyclerView.addItemDecoration(itemDecorationhr);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        //Third VR RV
        vrb_mRecyclerView = (RecyclerView) findViewById(R.id.vrb_recycler_view);
        vrb_mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        vrb_mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        vrb_mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecorationvrb =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        vrb_mRecyclerView.addItemDecoration(itemDecorationvrb);

        hr_mRecyclerView.setNestedScrollingEnabled(false);
        vrb_mRecyclerView.setNestedScrollingEnabled(false);
        hr_mRecyclerView.setNestedScrollingEnabled(false);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
                                                                          MyRecyclerViewAdapter.MyClickListener() {
                                                                              @Override
                                                                              public void onItemClick(int position, View v) {
                                                                                  Log.i(LOG_TAG, " Clicked on Item " + position);
                                                                              }
                                                                          });
    }

    private ArrayList<Rc_Data> getDataSet() {
        ArrayList results = new ArrayList<Rc_Data>();
        for (int index = 0; index < 50; index++) {
            Rc_Data obj = new Rc_Data("Title " + index,
                    "Short Description " + index);
            results.add(index, obj);
        }
        return results;
    }
}
