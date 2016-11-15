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

import static ot3.insa.fr.geodraw.communication.Client.theClient;

/**
 * A login screen that offers login via email/password.
 */
public class SettingsActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;
    @NotEmpty
    private EditText server_address;
    @NotEmpty
    private EditText server_port;
    private Button save_settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        server_address = (EditText) findViewById(R.id.server_address);
        server_port = (EditText) findViewById(R.id.server_port);

        validator = new Validator(this);
        validator.setValidationListener(this);

        save_settings = (Button) findViewById(R.id.save_settings);
        save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        theClient.setIp(server_address.getText().toString());
        theClient.setPort(Integer.parseInt(server_port.getText().toString()));
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

//public class SettingsActivity extends Fragment {
//
//    private EditText server_address;
//    private EditText server_port;
//    private Button save_Settings;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View myView = inflater.inflate(R.layout.activity_settings, container, false);
//        server_address = (EditText) getView().findViewById(R.id.server_address);
//        server_port = (EditText) getView().findViewById(R.id.server_port);
//        save_Settings = (Button) getView().findViewById(R.id.save_settings);
//
//        save_Settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                theClient.setIp(server_address.getText().toString());
//                theClient.setPort(Integer.parseInt(server_port.getText().toString()));
//                startActivity(intent);
//            }
//        });
//        return myView;
//
//    }
//}

