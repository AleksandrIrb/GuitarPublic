package alex.aleksandr.ru.musicguitar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HelpAndAboutAppActivity extends AppCompatActivity {

    public static final String EXTRA_IS_HELP_ACTIVITY = "alex.aleksandr.ru.musicguitar.extra_is_help_activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        boolean isHelp = getIntent().getBooleanExtra(EXTRA_IS_HELP_ACTIVITY, false);
        TextView textView = (TextView) findViewById(R.id.text_view_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (isHelp) {
                setTitle(R.string.help_app);
            } else {
                setTitle(R.string.about_app);
            }
        }
        if (isHelp) {
            textView.setText(R.string.help_app_text);
        } else {
            textView.setText(R.string.about_app_text);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
