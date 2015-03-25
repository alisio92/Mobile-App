package be.howest.nmct.week4implicit;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ImplicitFragment extends Fragment {

    private Button uwBrowserView;
    private Button uwCallView;
    private Button uwDialView;
    private Button uwLocationView;
    private Button uwContactView;
    private Button uwEditView;
    private Button uwBmiView;

    public Cursor mCursor;
    public int mLookupKeyIndex;
    public int mIdIndex;
    public String mCurrentLookupKey;
    public long mCurrentId;
    Uri mSelectedContactUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_implicit, container, false);
        initVariables(v);
        this.uwBrowserView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.nmct.be")));
            }
        });
        this.uwCallView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:123456789")));
            }
        });
        this.uwDialView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:123456789")));
            }
        });
        this.uwLocationView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });
        this.uwContactView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI));
            }
        });
        this.uwEditView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent editIntent = new Intent(Intent.ACTION_EDIT);
                mLookupKeyIndex = mCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
                mCurrentLookupKey = mCursor.getString(mLookupKeyIndex);
                mIdIndex = mCursor.getColumnIndex(ContactsContract.Contacts._ID);
                mCurrentId = mCursor.getLong(mIdIndex);
                mSelectedContactUri = ContactsContract.Contacts.getLookupUri(mCurrentId, mCurrentLookupKey);
                editIntent.setDataAndType(mSelectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
                editIntent.putExtra("finishActivityOnSaveCompleted", true);
                startActivity(editIntent);
            }
        });
        this.uwBmiView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //startActivity(new Intent(Intent.ACTION_MAIN).setComponent(new ComponentName("be.howest.nmct.Week2BMI","be.howest.nmct.Week2BMI.MainActivity")));
                startActivity(new Intent("be.howest.nmct.Week2BMI"));
            }
        });
        return v;
    }

    public void initVariables(View v){
        this.uwBrowserView = (Button) v.findViewById(R.id.btnBrowser);
        this.uwCallView = (Button) v.findViewById(R.id.btnCall);
        this.uwDialView = (Button) v.findViewById(R.id.btnDial);
        this.uwLocationView = (Button) v.findViewById(R.id.btnLocation);
        this.uwContactView = (Button) v.findViewById(R.id.btnContacts);
        this.uwEditView = (Button) v.findViewById(R.id.btnEdit);
        this.uwBmiView = (Button) v.findViewById(R.id.btnBmi);
    }
}
