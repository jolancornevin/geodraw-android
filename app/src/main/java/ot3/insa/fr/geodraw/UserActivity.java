package ot3.insa.fr.geodraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import ot3.insa.fr.geodraw.communication.Client;

import static ot3.insa.fr.geodraw.communication.Client.theClient;

/**
 * A login screen that offers login via email/password.
 */
public class UserActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;
    @NotEmpty
    private EditText username;
    private Button save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        username = (EditText) findViewById(R.id.username);

        //TODO
        //username.setText(theClient.getNickname());

        validator = new Validator(this);
        validator.setValidationListener(this);

        save = (Button) findViewById(R.id.save_user);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //TODO set the username of the client
        Client.theClient.setUsername(username.toString());
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