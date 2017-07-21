package louis.hookviewsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import louis.library.HookView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((HookView)findViewById(R.id.hook_view)).startTickAnim();
    }
}
