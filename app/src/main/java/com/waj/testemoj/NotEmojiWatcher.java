package com.waj.testemoj;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 实现不允许通过输入法输入表情的功能
 * */
public class NotEmojiWatcher implements TextWatcher {
	EditText editText; // 要被看的编辑框
	Context context;

	InputFilter filter = null;
	
	public NotEmojiWatcher(EditText editText, final Context context) {
		super();
		this.editText = editText;
		this.context = context;

		filter = new InputFilter(){
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				if (StringUtil.filterCharToNormal(source.toString()).equals("")){
					Toast.makeText(context,"不支持的字符",Toast.LENGTH_SHORT).show();
					return "";
				}
				return source;
			}
		};
		editText.setFilters(new InputFilter[]{filter});
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
