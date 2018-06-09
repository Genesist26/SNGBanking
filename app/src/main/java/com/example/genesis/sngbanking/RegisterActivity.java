package com.example.genesis.sngbanking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private Button btSubmit;
    private EditText etFirstname, etSurname, etAccnumbe, etEmail, etPass, etPassCon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btSubmit    = (Button) findViewById(R.id.btSubmit);
        etFirstname = (EditText) findViewById(R.id.etFirstname);
        etSurname   = (EditText) findViewById(R.id.etSurname);
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

        if(fname.isEmpty() || lname.isEmpty() || email.isEmpty() || pass.isEmpty() || passConf.isEmpty()){
            Toast.makeText(this, "Please fill in all information", Toast.LENGTH_SHORT).show();
        }
        else if(!isEmailValid(email)){
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
        }
        else if(pass.length() < 8) {
            Toast.makeText(this, "Passwords must be at least 8 characters", Toast.LENGTH_SHORT).show();
        }
        else if(pass.equals(passConf)) {
            Database.MyDbHelper mHelper = new Database.MyDbHelper(this);
            anAcc = new BankAccount(fname, lname, email, pass, 0);
            Authen au = new Authen(anAcc.getAccountNumber(), email, pass);
            Log.i("Regis", anAcc.getAccountNumber() + " : " + anAcc.getFullName());
            Log.i("Regis", "authen : " + au.getAccountNumber());
            mHelper.addAcc(anAcc);
            mHelper.addAuthen(au);
            Toast.makeText(this, "Your account was created", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "Error: password not equals", Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
