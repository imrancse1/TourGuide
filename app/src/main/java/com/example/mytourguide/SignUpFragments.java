package com.example.mytourguide;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mytourguide.Interfaces.FragmentTransectionService;
import com.example.mytourguide.PojoClasses.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragments extends Fragment {
    private EditText SignupName, SignupEmail,SignupPhone, SignupPassword;
    private Button SignupComplete;
    private FragmentTransectionService service;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public SignUpFragments() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        service= (FragmentTransectionService) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SignupName= view.findViewById(R.id.SignupNameET);
        SignupEmail=view.findViewById(R.id.SignupEmailET);
        SignupPhone=view.findViewById(R.id.SignupPhoneET);
        SignupPassword=view.findViewById(R.id.SignupPassET);
        SignupComplete =view.findViewById(R.id.SignupBtn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        SignupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });

    }

    private void userSignUp() {
        String email = SignupEmail.getText().toString();
        String pass = SignupPassword.getText().toString();
        final String name = SignupName.getText().toString();
        final String phone = SignupPhone.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Successful Create Account", Toast.LENGTH_SHORT).show();
                        reference = database.getReference("UserData");
                        UserModel model = new UserModel(auth.getUid(), name, phone);
                        reference.setValue(model);
                        service.signupSuccess();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
