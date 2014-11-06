package idv.chauyan.xploit.androidmagnifier;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String currentStr = getString(R.string.hello_world);
        final int currentVersion = Build.VERSION.SDK_INT;
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int index = -1;
                if (currentVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    index = textView.getOffsetForPosition(event.getX(), event.getY());
                    getCurrentWord(currentStr.split(" "), index);
                } else {
                    index = getOffsetForPosition(textView, event.getX(), event.getY());
                    getCurrentWord(currentStr.split(" "), index);
                }
                return false;
            }
        });
    }

    private void getCurrentWord (String[] wordArray, int index ) {

        if(index == -1) return;
        int length = 0;
        for(String str : wordArray) {
            if(length + str.length() > index) {
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                break;
            } else  length += str.length() + 1;
        }
    }

    private int getOffsetForPosition(TextView textView, float x , float y) {
        if(textView.getLayout() == null) return -1;
        final int line = getLineAtCoordinate(textView, y);
        return getOffsetAtCoordinate(textView, line, x);
    }

    private int getOffsetAtCoordinate(TextView textView, int line, float x) {
        x = convertToLocalHorizontalCoordinate(textView, x);
        return textView.getLayout().getOffsetForHorizontal(line, x);
    }

    private float convertToLocalHorizontalCoordinate(TextView textView, float x) {
        x -= textView.getTotalPaddingLeft();
        x = Math.max(0.0f, x);
        x = Math.min(textView.getWidth() - textView.getTotalPaddingRight() - 1, x);
        x += textView.getScrollX();
        return x;
    }

    private int getLineAtCoordinate(TextView textView, float y) {
        y -= textView.getTotalPaddingTop();
        y = Math.max(0.0f, y);
        y = Math.min(textView.getHeight() - textView.getTotalPaddingBottom() - 1, y);
        y += textView.getScrollY();
        return textView.getLayout().getLineForVertical((int)y);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
