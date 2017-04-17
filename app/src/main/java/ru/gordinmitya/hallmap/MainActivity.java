package ru.gordinmitya.hallmap;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import ru.gordinmitya.hallmap.models.Table;
import ru.gordinmitya.hallmap.views.TableView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout layout = (FrameLayout) findViewById(R.id.content);

        Table table = new Table("Стол 1", 0, 0, 25, 45, 25, Table.TYPE_SQUARE);
        TableView tableView = new TableView(this, table, 10);
        tableView.setOnClickListener(clickListener);
        layout.addView(tableView);

        Table table2 = new Table("Стол 2", 0, 0, 25, 25, 45, Table.TYPE_SQUARE);
        TableView tableView2 = new TableView(this, table2, 10);
        tableView2.setOnClickListener(clickListener);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(tableView2.getLayoutParams());
        params2.gravity = Gravity.CENTER;
        tableView2.setLayoutParams(params2);
        layout.addView(tableView2);

        Table table3 = new Table("Стол VIP", 0, 0, 35, 35, 0, Table.TYPE_CIRCLE);
        TableView tableView3 = new TableView(this, table3, 10);
        tableView3.setOnClickListener(clickListener);
        FrameLayout.LayoutParams params3 = new FrameLayout.LayoutParams(tableView3.getLayoutParams());
        params3.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        tableView3.setLayoutParams(params3);
        layout.addView(tableView3);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // ЗАЛИПУХА
            TableView tableView = (TableView) v.getParent();
            Toast.makeText(
                    MainActivity.this,
                    String.format("Нажатие по %s", tableView.table.name),
                    Toast.LENGTH_SHORT
            ).show();
        }
    };
}
