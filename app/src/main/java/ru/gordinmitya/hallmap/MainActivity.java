package ru.gordinmitya.hallmap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        hallView = (HallView) findViewById(R.id.content);
        hallView.setOnTableClickListener(tableClickListener);
        hallView.update(generateData());
    }

    private HallView.OnTableClickListener tableClickListener = new HallView.OnTableClickListener() {
        @Override
        public void onClick(Table table) {
            hallView.setTableText(table, "Clicked!");
        }
    };

    private List<Table> generateData() {
        return Arrays.asList(
                new Table(1, "Стол 1", 0, 0, 680, 100, 0, Table.TYPE_SQUARE),
                new Table(2, "Стол 2", 0, 200, 100, 380, 0, Table.TYPE_SQUARE),
                new Table(3, "Стол 3", 200, 200, 300, 200, -45, Table.TYPE_SQUARE),
                new Table(4, "Стол VIP", 500, 500, 150, 150, 0, Table.TYPE_CIRCLE)
        );
    }
}
