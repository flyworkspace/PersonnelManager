package com.flyworkspace.person.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Spinner;

import com.flyworkspace.person.R;
import com.flyworkspace.person.database.DbManager;
import com.flyworkspace.person.model.PersonInfo;

public class DetailedActivity extends BaseActivity {
    private TextView tvCompany;
    private TextView tvContact;
    private TextView tvSex;
    private TextView tvNickname;
    private TextView tvContactPhone;
    private TextView tvAddress;
    private TextView tvRange;
    private TextView tvCategory;
    private TextView tvNote;
    private PersonInfo personInfo;
    private long personInfoId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                share();
                break;
            case R.id.action_edit:
                Intent intent = new Intent(DetailedActivity.this, AddActivity.class);
                intent.putExtra("person_info_id", personInfo.getId());
                startActivity(intent);
                break;
            case R.id.action_delete:
                delete();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, personInfo.getShareStr());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.tip)
                .setMessage(R.string.delete_tip)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbManager.getPersonInfoDao(DetailedActivity.this).deleteByKey(personInfo.getId());
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    @Override
    protected void findView() {
        tvCompany = (TextView) findViewById(R.id.tv_company);
        tvContact = (TextView) findViewById(R.id.tv_contact);
        tvSex = (TextView) findViewById(R.id.textview_sex);
        tvNickname = (TextView) findViewById(R.id.tv_nickname);
        tvContactPhone = (TextView) findViewById(R.id.tv_phone);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvRange = (TextView) findViewById(R.id.tv_range);
        tvCategory = (TextView) findViewById(R.id.tv_category);
        tvNote = (TextView) findViewById(R.id.tv_note);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            personInfoId = bundle.getLong("person_info_id");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        personInfo = DbManager.getPersonInfoDao(DetailedActivity.this).loadByRowId(personInfoId);
        if (personInfo != null) {
            tvCompany.setText(personInfo.getCompanyName());
            tvContact.setText(personInfo.getContactName());
            tvSex.setText(getResources().getStringArray(R.array.spinner_sex)[personInfo.getSex()]);
            tvNickname.setText(personInfo.getNickname());
            tvContactPhone.setText(personInfo.getContactPhone());
            tvAddress.setText(personInfo.getAddress());
            tvRange.setText(personInfo.getRange());
            tvCategory.setText(personInfo.getCategory());
            tvNote.setText(personInfo.getNote());
        }
    }
}
