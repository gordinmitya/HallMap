package ru.gordinmitya.hallmap.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.gordinmitya.hallmap.models.Table;

public class HallView extends FrameLayout {
    private static final int HALL_SIZE = 680;

    private Paint wallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private double scale = 0;
    private int paddingTop = 0, paddingLeft = 0;
    private int squareSide = 0;

    private OnTableClickListener clickListener;
    private SparseArray<TableView> tablesViewMap;
    private List<TableView> tableViews;

    public HallView(@NonNull Context context) {
        super(context);
        init();
    }

    public HallView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        wallPaint.setStyle(Paint.Style.STROKE);
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(2);
    }

    public void update(List<Table> tables) {
        tableViews = new ArrayList<>(tables.size());
        tablesViewMap = new SparseArray<>(tables.size());
        for (Table table : tables) {
            TableView tableView = new TableView(getContext(), table, scale);
            tableView.setOnClickListener(tableClickListener);

            tableViews.add(tableView);
            tablesViewMap.append(table.id, tableView);
            this.addView(tableView);
        }
    }

    public void setOnTableClickListener(OnTableClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setTableText(Table table, String text) {
        setTableText(table.id, text);
    }

    @SuppressLint("DefaultLocale")
    public void setTableText(int tableId, String text) {
        TableView tableView = tablesViewMap.get(tableId);
        if (tableView == null)
            throw new IllegalArgumentException(String.format("Table with id=%d not found in HallView", tableId));
        tableView.setText(text);
    }

    private View.OnClickListener tableClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (clickListener == null) return;
            if (!(v.getParent() instanceof TableView)) return;
            TableView tableView = (TableView) v.getParent();
            clickListener.onClick(tableView.table);
        }
    };

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            if (!(getChildAt(i) instanceof TableView)) {
                super.onLayout(changed, left, top, right, bottom);
                continue;
            }
            TableView child = (TableView) getChildAt(i);

            int childLeft = (int) (child.table.x * scale) + paddingLeft;
            int childTop = (int) (child.table.y * scale) + paddingTop;
            child.layout(childLeft, childTop,
                    childLeft + child.getMeasuredWidth(),
                    childTop + child.getMeasuredHeight());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        squareSide = Math.min(w, h);
        double newScale = (double) squareSide / HALL_SIZE;
        if (newScale != scale) {
            scale = newScale;
            for (TableView tableView : tableViews) {
                tableView.setScale(newScale);
            }
        }
        if (h > w) {
            paddingTop = (h - w) / 2;
            paddingLeft = 0;
        } else {
            paddingTop = 0;
            paddingLeft = (w - h) / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(
                paddingLeft,
                paddingTop,
                paddingLeft + squareSide,
                paddingTop + squareSide,
                wallPaint
        );
    }

    public interface OnTableClickListener {
        void onClick(Table table);
    }
}
