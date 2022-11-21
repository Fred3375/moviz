package com.dam.moviz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.dam.moviz.commons.Utils;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SigninActivity extends AppCompatActivity {

    private View baseView;

    private void initUI(){
        baseView = findViewById(R.id.mainLayout);
    }

    private void signUpActivity(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build()
        );

        Intent signIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                // Ajout de design
                .setLogo(R.drawable.logo)
                .setTheme(R.style.LoginTheme)
                .setTosAndPrivacyPolicyUrls("htpps://google.fr", "https://yahoo.fr")
                .setIsSmartLockEnabled(true)
                .build();

        signLauncher.launch(signIntent);
    }

    private final ActivityResultLauncher<Intent> signLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> onSignResult(result)
            //new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
            //    @Override
            //    public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
            //        onSignResult(result);
            //    }
            //}
    );

    @SuppressWarnings("ConstantConditions")
    private void onSignResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse reponse = result.getIdpResponse();
        if(result.getResultCode() == RESULT_OK) {
            // connecté
            Utils.showSnackBar(baseView, getString(R.string.connected));
        } else {
            // pas connecté
            if(reponse == null){
                Utils.showSnackBar(baseView, getString(R.string.error_canceled));
            } else if (reponse.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                Utils.showSnackBar(baseView, getString(R.string.no_internet));
            } else if (reponse.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                Utils.showSnackBar(baseView, getString(R.string.unknown_error));
            }
        }
    }

    public void startSignUpActivity(View view){
        Log.i("TAG", "startSignUpActivity: ");
        signUpActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            // Toast.makeText(this, "TOAST", Toast.LENGTH_SHORT).show();
            // Utils.showSnackBar(baseView, "Déjà connecté");
            startActivity(new Intent(SigninActivity.this, HomeActivity.class));
        }
    }
}