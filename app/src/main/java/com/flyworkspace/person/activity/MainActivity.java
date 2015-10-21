package com.flyworkspace.person.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyworkspace.person.R;
import com.flyworkspace.person.adapter.PersonListAdapter;
import com.flyworkspace.person.dao.DaoMaster;
import com.flyworkspace.person.dao.DaoSession;
import com.flyworkspace.person.dao.PersonInfoDao;
import com.flyworkspace.person.database.DbManager;
import com.flyworkspace.person.model.PersonInfo;
import com.flyworkspace.person.view.OnRcvScrollListener;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends BaseActivity {
    private PersonInfoDao personInfoDao;
    private RecyclerView mRecyclerView;
    private PersonListAdapter personListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SearchView mSearchView;
    private boolean isSearchViewVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void findView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }

    @Override
    protected void setListener() {
        mRecyclerView.addOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                loadDataFromDb();
                personListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    private void initDatabase() {
        personInfoDao = DbManager.getPersonInfoDao(this);
        if (personInfoDao.count() < 1) {
            for (int i = 0; i < 100; i++) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setCompanyName("公司" + i);
                personInfo.setContactName("张三");
                personInfo.setNickname("保禄");
                personInfo.setSex("男");
                personInfo.setContactPhone("15012345678");
                personInfo.setAddress("XX省XX市XX路500号3号楼2层1205");
                personInfo.setRange("河南省、湖北省、山东省");
                personInfo.setCategory("售前、销售、售后");
                personInfoDao.insert(personInfo);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatabase();
        initList();
    }

    private void initList() {
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        loadDataFromDb();
        mRecyclerView.setAdapter(personListAdapter);
    }

    private void loadDataFromDb() {
        int offset;
        if (personListAdapter == null) {
            personListAdapter = new PersonListAdapter(this);
            offset = 0;
        } else {
            offset = personListAdapter.getItemCount();
        }
        personListAdapter.addList(personInfoDao.queryBuilder().limit(30).offset(offset).list());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);

        setSearchView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            startActivity(new Intent(MainActivity.this, AddActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSearchView() {
//        final float density = getResources().getDisplayMetrics().density;
//        mSearchView.setIconified(false);
//        mSearchView.setIconifiedByDefault(false);
//        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
//        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
//        if (closeImg != null) {
//            LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams) closeImg.getLayoutParams();
//            paramsImg.topMargin = (int) (-2 * density);
//            closeImg.setImageResource(R.mipmap.clear_img);
//            closeImg.setLayoutParams(paramsImg);
//        }
//        final int editViewId = context.getResources().getIdentifier("search_src_text", "id", getPackageName());
//        mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
//        if (mEdit != null) {
//            mEdit.setHintTextColor(getResources().getColor(R.color.color_hint));
//            mEdit.setTextColor(getResources().getColor(R.color.color_white));
//            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//            mEdit.setHint(String.format(getResources().getString(R.string.search_hint_tip), MemoryData.departmentList.get(mPosition).getMembers().size()));
//        }
//        LinearLayout rootView = (LinearLayout) mSearchView.findViewById(R.id.search_bar);
//        rootView.setBackgroundResource(R.drawable.edit_bg);
//        rootView.setClickable(true);
//        LinearLayout editLayout = (LinearLayout) mSearchView.findViewById(R.id.search_plate);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editLayout.getLayoutParams();
//        LinearLayout tipLayout = (LinearLayout) mSearchView.findViewById(R.id.search_edit_frame);
//        LinearLayout.LayoutParams tipParams = (LinearLayout.LayoutParams) tipLayout.getLayoutParams();
//        tipParams.leftMargin = 0;
//        tipParams.rightMargin = 0;
//        tipLayout.setLayoutParams(tipParams);
//        ImageView icTip = (ImageView) mSearchView.findViewById(R.id.search_mag_icon);
//        icTip.setImageResource(R.mipmap.ic_search_tip);
//        params.topMargin = (int) (4 * density);
//        editLayout.setLayoutParams(params);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSearchViewVisible = true;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                isSearchViewVisible = false;
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doSearch(newText);
                return true;
            }
        });
    }

    private void doSearch(String text) {
        QueryBuilder qb = personInfoDao.queryBuilder();
        qb.limit(30).offset(0).whereOr(PersonInfoDao.Properties.CompanyName.like("%" + text + "%"), PersonInfoDao.Properties.ContactName.like("%" + text + "%"));
        List<PersonInfo> personInfos = qb.list();
        personListAdapter.setList(personInfos);
        personListAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean showNavigation() {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isSearchViewVisible) {
            mSearchView.onActionViewCollapsed();
            mSearchView.setQuery("", false);
            mSearchView.clearFocus();
            isSearchViewVisible = false;
        } else {
            super.onBackPressed();
        }
    }
}
