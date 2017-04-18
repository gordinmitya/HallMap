package ru.gordinmitya.hallmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import ru.gordinmitya.hallmap.models.Table;
import ru.gordinmitya.hallmap.views.HallView;

public class MainActivity extends AppCompatActivity {
    HallView hallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Table> tables = Arrays.asList(
                new Table(1, "Стол 1", 0, 0, 680, 200, 0, Table.TYPE_SQUARE),
                new Table(2, "Стол 2", 0, 200, 200, 480, 0, Table.TYPE_SQUARE),
                new Table(3, "Стол 3", 300, 300, 200, 100, -45, Table.TYPE_SQUARE),
                new Table(4, "Стол VIP", 500, 500, 150, 150, 0, Table.TYPE_CIRCLE)
        );

        hallView = (HallView) findViewById(R.id.content);
        hallView.setOnTableClickListener(tableClickListener);
        hallView.update(tables);
    }

    private HallView.OnTableClickListener tableClickListener = new HallView.OnTableClickListener() {
        @Override
        public void onClick(Table table) {
            hallView.setTableText(table, table.name + " clicked");
        }
    };
}
