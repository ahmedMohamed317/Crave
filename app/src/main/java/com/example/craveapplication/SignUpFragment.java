package com.example.craveapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    TextInputEditText editTextEmail , editTextpassword,editTextRepassword ;
    Button buttonSignUp;
    String email , password ;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView login;
    public SignUpFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        FirebaseApp.initializeApp(getContext().getApplicationContext());

        mAuth=FirebaseAuth.getInstance();

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //mAuth=FirebaseAuth.getInstance();
        super.onViewCreated(view, savedInstanceState);
    editTextEmail=view.findViewById(R.id.textInputEditTextEmailSignup);
    editTextpassword=view.findViewById(R.id.textInputEditTextPassSignup);
    editTextRepassword=view.findViewById(R.id.textInputEditTextConfirmPassSignUp);
    buttonSignUp=view.findViewById(R.id.signupBtn);
    progressBar = view.findViewById(R.id.progressBar);
    login  = view.findViewById(R.id.loginTxt);
    buttonSignUp.setOnClickListener(new View.OnClickListener() {
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
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);

                            if (task.isSuccessful()) {

                                Toast.makeText(getContext().getApplicationContext(), "Authentication success.",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext().getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);


            }
        });


    }
}