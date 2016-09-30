package ot3.insa.fr.geodraw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Date;

import ot3.insa.fr.geodraw.Utilities.InputFilterMinMax;
import ot3.insa.fr.geodraw.models.Game;


/**
 * A login screen that offers login via email/password.
 */
public class CreateGameActivity extends AppCompatActivity {
    public final static String EXTRA_NAME = "name";
    public final static String EXTRA_PRIVATE = "private";
    public final static String EXTRA_MAX_PLAYERS = "max_player";
    public final static String EXTRA_NB_DAYS = "nb_days";
    public final static String EXTRA_NB_HOURS = "nb_hours";
    public final static String EXTRA_NB_MINUTES = "nb_minutes";
    public final static String EXTRA_THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        EditText et = (EditText) findViewById(R.id.nb_days);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "999")});
        et = (EditText) findViewById(R.id.nb_hours);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "24")});
        et = (EditText) findViewById(R.id.nb_minutes);
        et.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "60")});

        Button btn = (Button) findViewById(R.id.btn_create_game);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TabActivity.class);

                try {
                    EditText game_name = (EditText) findViewById(R.id.game_name);
                    intent.putExtra(EXTRA_NAME, game_name.getText().toString());

                    CheckBox isPrivate = (CheckBox) findViewById(R.id.isPrivate);
                    intent.putExtra(EXTRA_PRIVATE, isPrivate.isChecked());

                    EditText max_nb_gamers = (EditText) findViewById(R.id.max_nb_gamers);
                    intent.putExtra(EXTRA_MAX_PLAYERS, Integer.parseInt(max_nb_gamers.getText().toString()));

                    EditText nb_days = (EditText) findViewById(R.id.nb_days);
                    intent.putExtra(EXTRA_NB_DAYS, Integer.parseInt(nb_days.getText().toString()));

                    EditText nb_hours = (EditText) findViewById(R.id.nb_hours);
                    intent.putExtra(EXTRA_NB_HOURS, Integer.parseInt(nb_hours.getText().toString()));

                    EditText nb_minutes = (EditText) findViewById(R.id.nb_minutes);
                    intent.putExtra(EXTRA_NB_MINUTES, Integer.parseInt(nb_minutes.getText().toString()));

                    EditText theme = (EditText) findViewById(R.id.theme);
                    intent.putExtra(EXTRA_THEME, theme.getText().toString());

                } catch (Exception e) {

                }

                startActivity(intent);
            }
        });
    }


}

