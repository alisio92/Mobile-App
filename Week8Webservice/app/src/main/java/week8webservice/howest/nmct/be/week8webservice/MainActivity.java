package week8webservice.howest.nmct.be.week8webservice;

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
            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.list, new StudentehuizenFragment())
                    .addToBackStack(null).commit();*/

            FragmentManager fragment = getFragmentManager();
            FragmentTransaction transaction = fragment.beginTransaction();
            StudentehuizenFragment frag = new StudentehuizenFragment();
            transaction.add(R.id.container, frag, "mainfrag");//
            transaction.commit();
        }
    }
}
