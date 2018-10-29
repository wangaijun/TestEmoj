package com.waj.testemoj;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 实现不允许通过输入法输入表情的功能
 * */
public class NotEmojiWatcher implements TextWatcher {
	EditText editText; // 要被看的编辑框
	Context context;

	// 是否重置了EditText的内容，为了避免进入死循环
	private boolean resetText;
	// 输入表情前的光标位置，
	private int cursorPos;
	// 输入表情前EditText中的文本
	private String tmp;
	
	public NotEmojiWatcher(EditText editText, Context context) {
		super();
		this.editText = editText;
		this.context = context;
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!resetText) {
			if (count >= 2) {// 表情符号的字符长度最小为2
				// 提取输入的长度大于3的文本
				if (s.length() >= cursorPos + count && cursorPos>=0) {
					CharSequence input = s.subSequence(cursorPos, cursorPos
							+ count);
					// 正则匹配是否是表情符号
					if (StringUtil.filterCharToNormal(input.toString()).equals("")) {
						resetText = true;
						// 是表情符号就将文本还原为输入表情符号之前的内容
						editText.setText(tmp);
						editText.setSelection(editText.length());
						editText.invalidate();
//						WindowUtils.showToast(R.string.hint_text_illegally,
//								context);
						Toast.makeText(context,"不支持的字符",Toast.LENGTH_SHORT).show();
					}
				}
			}
		} else {
			resetText = false;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		if (!resetText) {
			cursorPos = editText.getSelectionEnd();
			if (after >= 2 && after < count) cursorPos = cursorPos - count;
			tmp = s.toString();
			// 这里用s.toString()而不直接用s是因为如果用s，那么，tmp和s在内存中指向的是同一个地址，s改变了，tmp也就改变了，
			// 那么表情过滤就失败了
		}

	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
