package il.ac.shenkar.ronesn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class new_list extends ActionBarActivity {
    Context context;
    String listName="";
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

    protected void onCreate(Bundle savedInstanceState) {
        context=this;
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        listName = b.getString("key");
        setTitle(listName);
        setContentView(R.layout.activity_new_list);

        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListAdapter(this,listName));

        final Button newItemButton = (Button) findViewById(R.id.Add);
        newItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListSingleton list=ListSingleton.getInstance(context);

                String name=((EditText) findViewById(R.id.edit_message)).getText().toString();
                ((EditText) findViewById(R.id.edit_message)).setText("");
                String quantity=((EditText)findViewById(R.id.quantity)).getText().toString();
                ((EditText) findViewById(R.id.quantity)).setText("");
                if(name.equals("")){
                    showToastMessage("אנא הכנס שם מוצר");
                    return;
                }
                if(quantity.equals("")){
                    quantity="1";
                }
                int id=list.getNewId();
                ItemDetails newItem = new ItemDetails(name,Integer.parseInt(quantity),id,listName);

                list.pushItem(newItem);
                final ListView lv1 = (ListView) findViewById(R.id.listV_main);
                lv1.setAdapter(new ItemListAdapter(context,listName));
            }
        });
    }

    /**
     *  Inflate the menu, this adds items to the action bar if it is present.
     * @param menu
     * @return true
     */

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_list, menu);

        return true;
    }

    /**
     * Handle presses on the action bar items
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                setAlarm();
                return true;
            case R.id.menu_item_share:
                share();
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * open send intent and share the list
     */
    private void share() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String outStr=ListSingleton.getInstance(context).listToString(listName);
        sendIntent.putExtra(Intent.EXTRA_TEXT, outStr);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    /**
     * open alarm intent with the list name
     */
    private void setAlarm() {
        Intent intent = new Intent(context,TimePick.class);
        Bundle b = new Bundle();
        b.putString("key", getTitle().toString());
        intent.putExtras(b);
        startActivity(intent);
    }

    /**
     * Helper method to get voice input
     **/
    public void speak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, ((Object) this).getClass().getPackage().getName());
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * when getting input from the microphone
     **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

            //If Voice recognition is successful then it returns RESULT_OK
            if(resultCode == RESULT_OK) {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ((EditText) findViewById(R.id.edit_message)).setText(textMatchList.get(0));
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

}

