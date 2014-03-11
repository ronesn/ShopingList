package il.ac.shenkar.ronesn;

/**
 * Created by Noam Rones on 11/28/13.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
   Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListAdapter(this,null));

        final Button newListButton = (Button) findViewById(R.id.n_list_b);
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, new_list.class);
                Bundle b = new Bundle();
                String listName=((EditText) findViewById(R.id.list_name)).getText().toString();
                if(listName.equals("")){
                    showToastMessage("אנא הכנס שם רשימה");
                    return;
                }
                ((EditText) findViewById(R.id.list_name)).setText(null);
                b.putString("key", listName);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        final Button mbtSpeak = (Button) findViewById(R.id.btSpeak);
        mbtSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // Specify the calling package to identify your application
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, ((Object) this).getClass().getPackage().getName());
                startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
            }
            });
     }

    @Override
    public void onResume() {
        super.onResume();
        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListAdapter(this,null));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if(resultCode == RESULT_OK) {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ((EditText) findViewById(R.id.list_name)).setText(textMatchList.get(0));

            }
            //Result code for various error.
            else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
                showToastMessage("Audio Error");
            }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
                showToastMessage("Client Error");
            }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
                showToastMessage("Network Error");
            }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
                showToastMessage("No Match");
            }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
                showToastMessage("Server Error");
            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * Helper method to show the toast message
     **/
    void showToastMessage(String message){
        Toast toast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, 5, 100);
        toast.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        // The rest of your onStart() code.
     //   EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        // The rest of your onStop() code.
     //   EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
}
