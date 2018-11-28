package com.jacky.beedee.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.jacky.beedee.R;

/**
 * 2018/11/8.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
public class SearchView extends RelativeLayout {
    private View contentView;
    private RelativeLayout rlCloseSearch;
    private EditText etSearch;
    private OnTextChangedListener onTextChangedListener;
    private View.OnClickListener onCloseListener;
    private KeyDelegate mKeyDelegate;
    private boolean isAttached;

    public interface KeyDelegate {
        void onSearchKeyClick(EditText editText);
    }

    public interface OnTextChangedListener {
        void onTextChanged(String text);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttached = false;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        contentView = LayoutInflater.from(context).inflate(R.layout.search_view, this, true);

        rlCloseSearch = contentView.findViewById(R.id.rl_closeSearch);
        rlCloseSearch.setVisibility(View.INVISIBLE);
        rlCloseSearch.setOnClickListener(v -> {
            if (onCloseListener != null) {
                onCloseListener.onClick(v);
            }
            clearText();
        });
        etSearch = contentView.findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    rlCloseSearch.setVisibility(View.VISIBLE);
                } else {
                    rlCloseSearch.setVisibility(View.INVISIBLE);
                }

                if (onTextChangedListener != null) {
                    onTextChangedListener.onTextChanged(s.toString());
                }
            }
        });

        etSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (mKeyDelegate != null) {
                    mKeyDelegate.onSearchKeyClick(etSearch);
                }
            }
            return false;
        });
    }

    public void setSearchHint(String content) {
        etSearch.setHint(content);
    }

    private void clearText() {
        etSearch.setText("");
    }

    public String getText() {
        return etSearch.getText().toString().trim();
    }

    public String getHintText() {
        return etSearch.getHint().toString().trim();
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.onCloseListener = onClickListener;
    }

    public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
        this.onTextChangedListener = onTextChangedListener;
    }

    public KeyDelegate getKeyDelegate() {
        return mKeyDelegate;
    }

    public void setKeyDelegate(KeyDelegate keyDelegate) {
        this.mKeyDelegate = keyDelegate;
    }
}