package ru.gordinmitya.hallmap.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Arrays;

import ru.gordinmitya.hallmap.R;
import ru.gordinmitya.hallmap.models.Table;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class TableView extends FrameLayout {
    public final Table table;
    private double scale;

    private TextView nameText;
    private View backgroundView;

    public TableView(Context context, Table table, double scale) {
        super(context);
        this.table = table;
        this.scale = scale;

        // можно через инфлейт xml (где корневым элементом указан merge) в себя
        // LayoutInflater.from(context).inflate(R.layout.view_square_table, this);
        // или через код, ради интереса сделал кодом
        createBackgroundView(context);
        createNameText(context);
        initView(context);
    }

    void initView(Context context) {
        this.setLayoutParams(new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
    }

    void createNameText(Context context) {
        nameText = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT, Gravity.CENTER);
        nameText.setLayoutParams(layoutParams);
        nameText.setTextColor(ContextCompat.getColor(context, R.color.textColor));
        nameText.setText(table.name);
        this.addView(nameText);
    }

    void createBackgroundView(Context context) {
        backgroundView = new View(context);

        backgroundView.setLayoutParams(getLayoutParamsForBackground());
        backgroundView.setRotation(table.angle);

        backgroundView.setBackground(getSelectableItemBackground(context));
        backgroundView.setClickable(true);
        this.addView(backgroundView);
    }

    private LayoutParams getLayoutParamsForBackground() {
        LayoutParams layoutParams = new LayoutParams(
                (int) (table.width * scale),
                (int) (table.height * scale),
                Gravity.CENTER);
        if (table.type.equals(Table.TYPE_SQUARE)) {
            double angle = table.angle * Math.PI / 180;
            int verticalMargin = (int) (
                    (table.width / 2.0 * Math.sin(angle) + table.height / 2.0 * (Math.cos(angle) - 1)) * scale
            );
            int horizontalMargin = (int) (
                    (table.width / 2.0 * (1 - Math.cos(angle)) - table.height / 2.0 * Math.sin(angle)) * scale
            );
            verticalMargin = Math.abs(verticalMargin);
            horizontalMargin = Math.abs(horizontalMargin);
            layoutParams.setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
        }
        return layoutParams;
    }

    private Drawable getSelectableItemBackground(Context context) {
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.colorControlHighlight, outValue, true);
        int highlightColor = outValue.resourceId;
        Shape shape;
        if (table.type.equals(Table.TYPE_SQUARE)) {
            float[] outerRadii = new float[8];
            Arrays.fill(outerRadii, getResources().getDimension(R.dimen.square_table_corner_radius));
            shape = new RoundRectShape(outerRadii, null, null);
        } else {
            shape = new OvalShape();
        }
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        return new RippleDrawable(
                ColorStateList.valueOf(highlightColor),
                drawable,
                drawable
        );
    }

    public void setText(CharSequence text) {
        nameText.setText(text);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        backgroundView.setOnClickListener(onClickListener);
    }

    public void setScale(double scale) {
        this.scale = scale;
        backgroundView.setLayoutParams(getLayoutParamsForBackground());
        this.post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }
}
