package benji.chiwaya.samaya;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class NewNote extends AppCompatActivity {

    final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private EditText text;
    private String myText;
    private String currentTime;
    private int pookie_count;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        text = findViewById(R.id.newNote);
        currentTime = getCurrentTimeStamp();
        firestore.collection("Notes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                           pookie_count = task.getResult().size();
                        }
                    }
                });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 write_New_Data(text.getText().toString(), currentTime, pookie_count);
            }
        });

    }

    //TODO: verify that the timestamp is accurate and that the format
    // sent to the firebase cloud is compatible

    private void write_New_Data(String pookie_note, String date, int pookie_ID){
        Map<String, Object> postItem = new HashMap<>();
        postItem.put("Date",date);
        postItem.put("NoteID", pookie_ID);
        postItem.put("Pookie",pookie_note);
        //Add new document/collection. This ducement will be defined by XX parameter.
        //*Make sure that the names are uniform throughout the project, otherwise you have multiple documents/collections online
        firestore.collection("Notes").add(postItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(NewNote.this,"The post has been made",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(NewNote.this,MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NewNote.this,"The post failed",Toast.LENGTH_LONG).show();
                startActivity(new Intent(NewNote.this,MainActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getCurrentTimeStamp() {
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
