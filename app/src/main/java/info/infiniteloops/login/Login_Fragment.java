package info.infiniteloops.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import info.infiniteloops.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class Login_Fragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private CallbackManager callbackManager;
    private TextView textView;
    private static final String TAG = Login_Fragment.class.getSimpleName();

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    //Signin button
    private SignInButton signInButton;
    private ProgressDialog pDialog;
    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;
    SessionManager session;
    String email;
    public Login_Fragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Profile profile = Profile.getCurrentProfile();
                        displayMessage(profile);

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }

        });

    session = new SessionManager(getContext());
        // Progress dialog
        pDialog = new ProgressDialog(getContext());
        pDialog.setCancelable(false);

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity

        }
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayMessage(newProfile);
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();


    }
    //GPLUS LOGIN
    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Calling signin
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    //END GPLUS LOGIN
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.main_fragment, container, false);

        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);

        textView = (TextView) view.findViewById(R.id.textView);
        loginButton.setFragment(this);
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    email = object.getString("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

                Profile profile = Profile.getCurrentProfile();
                displayMessage(profile);

            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }
    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        displayMessage(profile);
    }

    //Get Plus Data
    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            session.setLogin(true);
            GoogleSignInAccount acct = result.getSignInAccount();
            //Displaying name and email
            textView.setText(acct.getDisplayName());
        } else {
            //If login fails
            //Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    //Get FaceBook Data
    private void displayMessage(Profile profile){
        if(profile != null){
            textView.setText(profile.getName());
            session.setLogin(true);
        } else {
            //If login fails
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

}