package il.ac.shenkar.ronesn;

/**
 * Created by Noam Rones on 11/28/13.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

   Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context=this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListAdapter(this));

        Button newTaskButton = (Button) findViewById(R.id.add_item);
        newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddItemActivity.class);
                startActivity(intent);
            }
        });

     }

    @Override
    public void onResume() {
        super.onResume();
        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        lv1.setAdapter(new ItemListAdapter(this));
    }
}
