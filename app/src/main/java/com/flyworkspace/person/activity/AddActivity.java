package com.flyworkspace.person.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.flyworkspace.person.R;
import com.flyworkspace.person.database.DbManager;
import com.flyworkspace.person.model.PersonInfo;

public class AddActivity extends BaseActivity {
    private EditText etCompany;
    private EditText etContact;
    private Spinner spinnerSex;
    private EditText etNickname;
    private EditText etContactPhone;
    private EditText etAddress;
    private EditText etRange;
    private EditText etCategory;
    private EditText etNote;

    private PersonInfo personInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }


    @Override
    protected void findView() {
        etCompany = (EditText) findViewById(R.id.et_company);
        etContact = (EditText) findViewById(R.id.et_contact);
        spinnerSex = (Spinner) findViewById(R.id.spinner_sex);
        etNickname = (EditText) findViewById(R.id.et_nickname);
        etContactPhone = (EditText) findViewById(R.id.et_phone);
        etAddress = (EditText) findViewById(R.id.et_address);
        etRange = (EditText) findViewById(R.id.et_range);
        etCategory = (EditText) findViewById(R.id.et_category);
        etNote = (EditText) findViewById(R.id.et_note);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {
        initSexSpinner();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            personInfo = (PersonInfo) bundle.getSerializable("person_info");
            if (personInfo != null) {
                etCompany.setText(personInfo.getCompanyName());
                etContact.setText(personInfo.getContactName());
                spinnerSex.setSelection(1);
//                spinnerSex.setText(personInfo.getSex());
                etNickname.setText(personInfo.getNickname());
                etContactPhone.setText(personInfo.getContactPhone());
                etAddress.setText(personInfo.getAddress());
                etRange.setText(personInfo.getRange());
                etCategory.setText(personInfo.getCategory());
                etNote.setText(personInfo.getNote());
            }
        }
    }

    private void initSexSpinner() {
        String[] mItems = getResources().getStringArray(R.array.spinner_sex);
        ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(_Adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (saveData() > 0) {
                finish();
                Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private long saveData() {
        if (personInfo == null) {
            personInfo = new PersonInfo();
        }
        personInfo.setCompanyName(etCompany.getText().toString());
        personInfo.setContactName(etContact.getText().toString());
        personInfo.setSex("");
        personInfo.setNickname(etNickname.getText().toString());
        personInfo.setContactPhone(etContactPhone.getText().toString());
        personInfo.setAddress(etAddress.getText().toString());
        personInfo.setRange(etRange.getText().toString());
        personInfo.setCategory(etCategory.getText().toString());
        personInfo.setNote(etNote.getText().toString());
        if (personInfo.getId() != null && personInfo.getId() > 0) {
            DbManager.getPersonInfoDao(this).update(personInfo);
            return personInfo.getId();
        } else {
            return DbManager.getPersonInfoDao(this).insert(personInfo);
        }
    }
}
