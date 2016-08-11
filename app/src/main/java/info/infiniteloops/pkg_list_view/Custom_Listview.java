package info.infiniteloops.pkg_list_view;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.infiniteloops.R;

public class Custom_Listview extends AppCompatActivity {
    private PackageManager packageManager = null;
    private List<ApplicationInfo> applist = null;
    private ApplicationAdapter listadaptor = null;
    ListView list;
    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom__listview);
        list = (ListView) findViewById(R.id.list);
        packageManager = getPackageManager();
        search = (EditText) findViewById(R.id.search);
        new LoadApplications().execute();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                ApplicationInfo app = applist.get(position);
                try {
                    Intent intent = packageManager
                            .getLaunchIntentForPackage(app.packageName);

                    if (null != intent) {
                        startActivity(intent);
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Custom_Listview.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(Custom_Listview.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }

        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                listadaptor.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return applist;
    }
    private class LoadApplications extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            applist = checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
            listadaptor = new ApplicationAdapter(Custom_Listview.this,
                    R.layout.custom_single_item, applist);

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Void result) {
            list.setAdapter(listadaptor);
            progress.dismiss();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(Custom_Listview.this, null,
                    "Loading application info...");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public class ApplicationAdapter extends ArrayAdapter<ApplicationInfo> {
        private List<ApplicationInfo> applicationInfos = null;
        ArrayList<ApplicationInfo> arraylist;
        private Context context;
        private PackageManager packageManager;

        public ApplicationAdapter(Context context, int textViewResourceId,
                                  List<ApplicationInfo> applicationInfos) {
            super(context, textViewResourceId, applicationInfos);
            this.context = context;
            this.applicationInfos = applicationInfos;
            this.arraylist = new ArrayList<ApplicationInfo>();
            this.arraylist.addAll(applicationInfos);

            packageManager = context.getPackageManager();
        }

        @Override
        public int getCount() {
            return ((null != applicationInfos) ? applicationInfos.size() : 0);
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return ((null != applicationInfos) ? applicationInfos.get(position) : null);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (null == view) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.custom_single_item, null);
            }

            ApplicationInfo applicationInfo = applicationInfos.get(position);
            if (null != applicationInfo) {
                TextView appName = (TextView) view.findViewById(R.id.app_name);
                TextView packageName = (TextView) view.findViewById(R.id.app_paackage);
                ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);

                appName.setText(applicationInfo.loadLabel(packageManager));
                packageName.setText(applicationInfo.packageName);
                iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));
            }
            return view;
        }
        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            applicationInfos.clear();
            if (charText.length() == 0) {
                applicationInfos.addAll(arraylist);
            } else {
                for (ApplicationInfo wp : arraylist) {
                    if (wp.loadLabel(packageManager).toString().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        applicationInfos.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    };
}
