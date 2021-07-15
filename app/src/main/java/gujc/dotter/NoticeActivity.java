package gujc.dotter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import gujc.dotter.fragment.NoticeFragment;

public class NoticeActivity extends AppCompatActivity {
    private NoticeFragment noticeFragment;
    private FragmentManager fm;
    private FragmentTransaction tran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        noticeFragment = new NoticeFragment();
        fm = getSupportFragmentManager();

        tran = fm.beginTransaction();
        tran.replace(R.id.mainFragment, noticeFragment).commit();
    }
}