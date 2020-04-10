package benji.chiwaya.samaya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;


public class Main2Activity extends AppCompatActivity {

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    final String TAG = "SAMAYA SAMPLE";
    private TextView text;
    private int randomID;
    int noteSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Random random = new Random();

        text = findViewById(R.id.maintext);
        firestore.collection("Notes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    noteSize = task.getResult().size();
                    randomID = random.nextInt(noteSize + 1);
                    //TODO work on making sure that a random int between 0 and collection.size is
                    // successfully created
                    loadData(randomID);
                }
            }
        });
    }
    private void loadData(int pookie_ID) {
        firestore.collection("Notes")
                .limit(1)
                .whereEqualTo("NoteID", pookie_ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //*********************************************************
                                //This is the point of the code where I retrieve the data for
                                // firestore and save a string object of the data snapshot
                                text.setText('"' + " " + document.getData().get("Pookie").toString() + " " + '"');
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Toast.makeText(Main2Activity.this, "Text not loaded", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
