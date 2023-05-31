package com.example.craveapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.craveapplication.R;
import com.example.craveapplication.roomDatabase.AppDatabase;
import com.example.craveapplication.searchResult.view.SearchResultActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment {
    TextInputEditText editTextEmail, editTextPassword, editTextRepassword;
    Button buttonSignUp;
    String email, password;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    ProgressBar progressBar;
    TextView login;
    TextView skip;
    ImageView googleBtn;
    ImageView githubBtn;
    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient googleSignInClient;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.textInputEditTextEmailSignup);
        editTextPassword = view.findViewById(R.id.textInputEditTextPassSignup);
        editTextRepassword = view.findViewById(R.id.textInputEditTextConfirmPassSignUp);
        buttonSignUp = view.findViewById(R.id.signupBtn);
        progressBar = view.findViewById(R.id.progressBar);
        login = view.findViewById(R.id.loginTxt);
        googleBtn = view.findViewById(R.id.googleSignIN);
        githubBtn = view.findViewById(R.id.githubSignIN);
        skip = view.findViewById(R.id.skip);
        githubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String githubEmail = editTextEmail.getText().toString();
                if ( githubEmail.isEmpty())
                {
                   Toast.makeText(getContext(),"Please enter a valid email", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                    provider.addCustomParameter("login", "email");

                    List<String> scopes =
                            new ArrayList<String>() {
                                {
                                    add("user:email");
                                }
                            };
                    provider.setScopes(scopes);

                    Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                    if (pendingResultTask != null) {
                        // There's something already here! Finish the sign-in for your user.
                        pendingResultTask
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                // User is signed in.
                                                // IdP data available in
                                                // authResult.getAdditionalUserInfo().getProfile().
                                                // The OAuth access token can also be retrieved:
                                                // ((OAuthCredential)authResult.getCredential()).getAccessToken().
                                                // The OAuth secret can be retrieved by calling:
                                                // ((OAuthCredential)authResult.getCredential()).getSecret().
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "can't sign up",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                    } else {
                        mAuth
                                .startActivityForSignInWithProvider(/* activity= */ getActivity(), provider.build())
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                                intent.putExtra("userEmail",email);
                                                getContext().startActivity(intent);                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure.
                                            }
                                        });
                    }


                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        googleBtn.setOnClickListener(v -> signIn());

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    intent.putExtra("userEmail",email);
                                    getContext().startActivity(intent);
                                    AppDatabase appDatabase = AppDatabase.getInstance(getContext());
                                    appDatabase.clearAllTables();


                                } else {
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPassContainNumber(s.toString())) {
                    editTextPassword.setError(getString(R.string.minmum_one_number));
                } else if (!isPassContainSpecialChar(s.toString())) {
                    editTextPassword.setError(getString(R.string.minimum_one_special_symbol));
                } else if (!isPassContainUpperCase(s.toString())) {
                    editTextPassword.setError(getString(R.string.minimum_one_uppercase));
                } else if (!isPassLengthGT8(s.toString())) {
                    editTextPassword.setError(getString(R.string.At_least__8_character));
                } else {
                    editTextPassword.setError(null);
                }
            }
        });


        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(s.toString())) {
                    editTextEmail.setError(getString(R.string.please_enter_valid_email));
                } else {
                   editTextEmail.setError(null);
                }
            }
        });
        editTextRepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!checkPassEquality()) {
                    editTextRepassword.setError(getString(R.string.confirm_password_doesnt_match_password));
                } else {
                    editTextPassword.setError(null);
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
//whvpLiN0IEOX1KHCOXxS
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                String errorMessage = e.getMessage();
                int errorCode = e.getStatusCode();

                Toast.makeText(getContext(), "Google Sign-In failed: " + errorMessage, Toast.LENGTH_SHORT).show();

            }
        }
    }
    //915159811631-h2oekpejiegm7lh9aqfs3uig1fgrm31f.apps.googleusercontent.com

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Google Sign-In successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "Google Sign-In failed2.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public boolean isPassContainSpecialChar(String pass) {
        String specialCharRegex = ".*[@#!$%^&+=].*";
        String UpperCaseRegex = ".*[A-Z].*";
        String NumberRegex = ".*[0-9].*";
        return pass.matches(specialCharRegex);
    }

    public boolean isPassContainUpperCase(String pass) {
        String UpperCaseRegex = ".*[A-Z].*";
        return pass.matches(UpperCaseRegex);
    }

    public boolean isPassContainNumber(String pass) {
        String NumberRegex = ".*[0-9].*";
        return pass.matches(NumberRegex);
    }


    public boolean isPassLengthGT8(String pass) {
        return pass.length() >= 8;
    }


    private boolean isValidEmail(String s) {
        String email = s.trim();
        String emailPattern = "[a-zA-Z\\d._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private boolean checkPassEquality() {
        return editTextRepassword.getText().toString().equals(editTextPassword.getText().toString());
    }
}
