package com.sikefeng.chinaren.ui.fragment;

import android.os.Bundle;

import com.sikefeng.chinaren.R;
import com.sikefeng.chinaren.core.BaseFragment;
import com.sikefeng.chinaren.databinding.FragmentContactsBinding;
import com.sikefeng.chinaren.mvpvmlib.base.RBasePresenter;
import com.sikefeng.chinaren.presenter.ContactsPresenter;
import com.sikefeng.chinaren.presenter.vm.ContactsViewModel;

/**
 * Created by Richard on 24/9/17.
 */

public class ContactsFragment extends BaseFragment<FragmentContactsBinding> {


    private ContactsPresenter presenter;


    @Override
    protected RBasePresenter getPresenter() {
        if (null == presenter) {
            presenter = new ContactsPresenter(this, new ContactsViewModel());
        }
        return presenter;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getBinding().setPresenter(presenter);
        getBinding().setViewModel(presenter.getViewModel());

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        System.out.println("-----------------onCreateViewLazy");
    }
}

