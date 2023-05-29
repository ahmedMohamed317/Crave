package com.example.craveapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    TextInputEditText editTextEmail , editTextpassword ;
    Button buttonLogin;
    String email , password ;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView signup;
    FirebaseUser currentUser;
    TextView skip;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(getContext().getApplicationContext());
        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
         //Check if user is signed in (non-null) and update UI accordingly.

        if(currentUser != null){
            //should open next activity whhic is home as teh user is already looged in
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        editTextEmail=view.findViewById(R.id.textInputEditTextEmailLogin);
        editTextpassword=view.findViewById(R.id.textInputEditTextPasslogin);
        buttonLogin=view.findViewById(R.id.loginBtn);
        progressBar = view.findViewById(R.id.progressBarLogin);
        signup  = view.findViewById(R.id.signupTxt);
        skip = view.findViewById(R.id.skip);

        skip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                email = editTextEmail.getText().toString();
                password = editTextpassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getContext().getApplicationContext(), "Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getContext().getApplicationContext(), "Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity().getApplicationContext(), "Login Success.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity().getApplicationContext(), "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);


            }
        });

    }
}