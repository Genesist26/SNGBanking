package com.example.genesis.sngbanking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button btSubmit;
    private EditText etFirstname, etSurname, etAccnumbe, etEmail, etPass, etPassCon;
    private Database.MyDbHelper mHelper = new Database.MyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btSubmit    = (Button) findViewById(R.id.btSubmit);
        etFirstname = (EditText) findViewById(R.id.etFirstname);
        etSurname   = (EditText) findViewById(R.id.etSurname);
        etAccnumbe  = (EditText) findViewById(R.id.etAccNum);
        etEmail     = (EditText) findViewById(R.id.etEmail);
        etPass      = (EditText) findViewById(R.id.etPass);
        etPassCon   = (EditText) findViewById(R.id.etPassCon);
    }

    public void onClickSubmit(View v) {
        BankAccount anAcc;

        String fname = etFirstname.getText().toString();
        String lname = etSurname.getText().toString();
        String email = etEmail.getText().toString();
        String pass  = etPass.getText().toString();
        String passConf = etPassCon.getText().toString();

        if(pass.equals(passConf)) {
            //anAcc = new BankAccount(fname, lname, accNum, email, pass, 1000);
            mHelper.addAcc(fname, lname, email, pass);

            Toast errorToast = Toast.makeText(this, "Your account was created", Toast.LENGTH_SHORT);
            errorToast.show();
            finish();

        }
        else{
            Toast errorToast = Toast.makeText(this, "Error: password not equals", Toast.LENGTH_SHORT);
            errorToast.show();
        }

    }


}
