package com.flyworkspace.person.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.flyworkspace.person.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by jinpengfei on 15-10-13.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initActionbar();
        findView();
        setListener();
        loadData();
    }

    private void initActionbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_layout);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if (showNavigation()) {
                mToolbar.setNavigationIcon(R.drawable.actionbar_back);
            }
            mToolbar.setTitleTextColor(0xFFFFFFFF);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean showNavigation() {
        return true;
    }

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void loadData();
}
