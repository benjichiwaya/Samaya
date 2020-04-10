package benji.chiwaya.samaya;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class Pookie extends AppWidgetProvider {

    private static int randomID;
    private static String TAG = "CLASSNAME";
    private static String widgetText;
    private static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static int noteSize;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pookie);
        dbProcess();
        views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private static void dbProcess() {
        final Random random = new Random();
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

    private static void loadData(int pookie_ID) {
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
                                widgetText = '"' + " " + document.getData().get("Pookie").toString() + " " + '"';
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}

