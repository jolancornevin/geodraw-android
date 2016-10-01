package ot3.insa.fr.geodraw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class CreateGameActivity extends AppCompatActivity implements Validator.ValidationListener{
    public final static String EXTRA_NAME = "name";
    public final static String EXTRA_PRIVATE = "private";
    public final static String EXTRA_MAX_PLAYERS = "max_player";
    public final static String EXTRA_NB_DAYS = "nb_days";
    public final static String EXTRA_NB_HOURS = "nb_hours";
    public final static String EXTRA_NB_MINUTES = "nb_minutes";
    public final static String EXTRA_THEME = "theme";

    Validator validator;

    @NotEmpty
    private EditText game_name;

    private CheckBox isPrivate;

    @NotEmpty
    @DecimalMax(value = 20, message = "Numbers of players must be less than 20")
    private EditText max_nb_gamers;

    @NotEmpty
    @DecimalMax(value = 20, message = "Numbers of days must be less than 20")
    @DecimalMin(value = 0, message = "Numbers of days cannot be less than 0")
    private EditText nb_days;

    @NotEmpty
    @DecimalMax(value = 24, message = "Numbers of hours must be less than 24")
    @DecimalMin(value = 0, message = "Numbers of hours cannot be less than 0")
    private EditText nb_hours;

    @NotEmpty
    @DecimalMax(value = 24, message = "Numbers of minutes must be less than 60")
    @DecimalMin(value = 0, message = "Numbers of minutes cannot be less than 0")
    private EditText nb_minutes;

    @NotEmpty
    private EditText theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        game_name = (EditText) findViewById(R.id.game_name);
        isPrivate = (CheckBox) findViewById(R.id.isPrivate);
        max_nb_gamers = (EditText) findViewById(R.id.max_nb_gamers);
        nb_days = (EditText) findViewById(R.id.nb_days);
        nb_hours = (EditText) findViewById(R.id.nb_hours);
        nb_minutes = (EditText) findViewById(R.id.nb_minutes);
        theme = (EditText) findViewById(R.id.theme);

        validator = new Validator(this);
        validator.setValidationListener(this);

        Button btn = (Button) findViewById(R.id.btn_create_game);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(getApplicationContext(), TabActivity.class);

        try {
            intent.putExtra(EXTRA_NAME, game_name.getText().toString());

            intent.putExtra(EXTRA_PRIVATE, isPrivate.isChecked());

            intent.putExtra(EXTRA_MAX_PLAYERS, Integer.parseInt(max_nb_gamers.getText().toString()));

            intent.putExtra(EXTRA_NB_DAYS, Integer.parseInt(nb_days.getText().toString()));

            intent.putExtra(EXTRA_NB_HOURS, Integer.parseInt(nb_hours.getText().toString()));

            intent.putExtra(EXTRA_NB_MINUTES, Integer.parseInt(nb_minutes.getText().toString()));

            intent.putExtra(EXTRA_THEME, theme.getText().toString());

        } catch (Exception e) {

        }

        startActivity(intent);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}

