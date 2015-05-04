package week8recyclerview.howest.nmct.be.week8recyclerview;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
        {
            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new StudentehuizenFragment())
                        .commit();
            }

            /*FragmentManager fragment = getFragmentManager();
            FragmentTransaction transaction = fragment.beginTransaction();
            StudentehuizenFragment frag = new StudentehuizenFragment();
            transaction.add(R.id.container, frag, "mainfrag");//
            transaction.commit();*/
        }
    }
}
