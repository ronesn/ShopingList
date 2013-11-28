package il.ac.shenkar.ronesn;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class AddItemActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final Button newTaskButton = (Button) findViewById(R.id.add);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=((EditText) findViewById(R.id.edit_message)).getText().toString();
                int quantity=Integer.parseInt(((EditText)findViewById(R.id.quantity)).getText().toString());
                long id=ItemListSingleton.getInstance().getCount();
                ItemDetails newItem = new ItemDetails(name,quantity,id);

                ItemListSingleton.getInstance().addItem(newItem);

                finish();
            }
        });
    }

}
