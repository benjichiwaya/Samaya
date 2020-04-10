package benji.chiwaya.samaya;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailText);
        password =findViewById(R.id.passwordText);
        button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("benjaminmuyinda@gmail.com")&& password.getText().toString().equals("Tika1996@hear+"))
                {
                    startActivity(new Intent(Login.this, NewNote.class));
                }
                else if (email.getText().toString().equals("samanthakstewart1@gmail.com")&& password.getText().toString().equals("Carm2000@hear+")) {
                    Toast.makeText(Login.this, "Sorry BABE!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Main2Activity.class));
                }
                else
                {
                    button.setText("BYE!!");
                    email.setVisibility(View.INVISIBLE);
                    password.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



}
