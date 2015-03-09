package be.howest.nmct.Week3ColorPicker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.tech.NfcBarcode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alisio on 23/02/2015.
 */
public class ColorView extends View {
    private String color = "#FFFFFF";
    private Paint paint;
    private Rect rect;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        invalidate();
    }

    public ColorView(Context context, AttributeSet attrs){
        //super(context, attrs);
        this(context, attrs, calcInitValue(), new Rect());
    }

    private static Paint calcInitValue() {
         Paint p = new Paint();
         return p;
     }

    public ColorView(Context context, AttributeSet attrs, Paint paint, Rect rect) {
        super(context, attrs);
        paint.setColor(Color.parseColor(color));
        this.paint = paint;
        this.rect = rect;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialogue();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        rect.set(0,0, getWidth(), getHeight());
        paint.setColor(Color.parseColor(color));
        canvas.drawRect(rect, paint);
    }

    protected void showColorDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Pick a Color")
                .setItems(R.array.holo_colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectColor(which);
                    }
                });
        builder.create().show();
    }

    protected void selectColor(int which){
        switch (which){
            case 0:
                setColor("#33B5E5");
                break;
            case 1:
                setColor("#AA66CC");
                break;
            case 2:
                setColor("#99CC00");
                break;
            case 3:
                setColor("#FFBB33");
                break;
            case 4:
                setColor("#FF4444");
                break;
            default:
                break;
        }
    }
}
