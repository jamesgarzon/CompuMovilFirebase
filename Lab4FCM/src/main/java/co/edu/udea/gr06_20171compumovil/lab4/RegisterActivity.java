package co.edu.udea.gr06_20171compumovil.lab4;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by lis on 15/05/17.
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText nameLabel;
    private EditText emailLabel;
    private EditText passwordLabel;

    private Button createAccountButton;

    private FirebaseAuth auth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        nameLabel = (EditText) findViewById(R.id.input_name);
        emailLabel = (EditText) findViewById(R.id.input_email);
        passwordLabel = (EditText) findViewById(R.id.input_password);
        createAccountButton = (Button) findViewById(R.id.btn_signup);
        progress = new ProgressDialog(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameLabel.getText().toString().trim();
                String email = emailLabel.getText().toString().trim();
                String password = passwordLabel.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    progress.setMessage("Registering, please wait...");
                    progress.show();
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progress.dismiss();
                                    if (task.isSuccessful()){
                                        String user_id =auth.getCurrentUser().getUid();
                                        Toast.makeText(RegisterActivity.this, user_id, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


    }
}
