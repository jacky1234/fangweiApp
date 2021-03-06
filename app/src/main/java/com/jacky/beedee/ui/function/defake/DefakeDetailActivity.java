package com.jacky.beedee.ui.function.defake;

import com.jacky.beedee.ui.inner.arch.BaseRootSupportActivity;

import org.jetbrains.annotations.NotNull;

public class DefakeDetailActivity extends BaseRootSupportActivity<DefakeDetailFragment> {
    @NotNull
    @Override
    protected Class<DefakeDetailFragment> createClazz() {
        return DefakeDetailFragment.class;
    }

    @NotNull
    @Override
    public DefakeDetailFragment createSupportFragment() {
        return new DefakeDetailFragment();
    }
}
