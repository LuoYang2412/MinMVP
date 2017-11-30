package com.luoyang.minimvp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    private EditText editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new MainPresenter(this);
        presenter.start();
    }

    @Override
    public void initView() {
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        if (null == presenter) {
            throw new NullPointerException();
        } else {
            this.presenter = presenter;
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInfo(String info) {
        textView.setText(info);
    }

    @Override
    public void showInputError(String error) {
        editText.setError(error);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                presenter.searchInfo(editText.getText().toString());
                break;
        }
    }
}
