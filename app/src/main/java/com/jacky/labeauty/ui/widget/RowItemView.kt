package com.jacky.labeauty.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.jacky.labeauty.R

/**
 * 2018/11/2.
 * GitHub:[https://github.com/jacky1234]
 *
 * @author jacky
 */
class RowItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private var flag = FLAG_RIGHT_SHOW_TEXT

    private val tv_title: TextView
    private val iv_arrow: View
    var imageView: ImageView? = null
        private set
    private val tv_right_desc: TextView
    val rightEditText: EditText
    private val checkBox: CheckBox

    val rightEditableContent: CharSequence
        get() = rightEditText.text

    init {
        LayoutInflater.from(context).inflate(R.layout.row_list_item, this, true)

        tv_title = findViewById(R.id.tv_title)
        iv_arrow = findViewById(R.id.iv_arrow)
        imageView = findViewById(R.id.tv_image)
        imageView = findViewById(R.id.tv_image)
        rightEditText = findViewById(R.id.et_edit)
        tv_right_desc = findViewById(R.id.tv_right_desc)
        checkBox = findViewById(R.id.checkBox)
    }

    fun setType(type: Int, clickable: Boolean = true) {
        tv_right_desc.visibility = View.GONE
        imageView!!.visibility = View.GONE
        rightEditText.visibility = View.GONE
        checkBox.visibility = View.GONE

        this.flag = type
        imageView!!.visibility = if (flag and FLAG_RIGHT_SHOW_IMAGE != 0) View.VISIBLE else View.GONE
        tv_right_desc.visibility = if (flag and FLAG_RIGHT_SHOW_TEXT != 0) View.VISIBLE else View.GONE
        if (flag and FLAG_RIGHT_EDITABLE != 0) {
            iv_arrow.visibility = View.GONE
            rightEditText.visibility = View.VISIBLE
        }

        if (flag and FLAG_RIGHT_CHECKABLE != 0) {
            iv_arrow.visibility = View.GONE
            checkBox.visibility = View.VISIBLE
        }

        if (!clickable) {
            iv_arrow.visibility = View.INVISIBLE
        }
    }

    fun setTitle(text: CharSequence) {
        tv_title.text = text
    }

    fun setRightDesc(text: String) {
        tv_right_desc.text = text
    }

    fun setRightEditableText(text: CharSequence) {
        rightEditText.setText(text)
    }

    fun setCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        checkBox.setOnCheckedChangeListener(listener)
    }

    fun setChecked(flag: Boolean) {
        checkBox.isChecked = flag
    }

    companion object {
        val FLAG_NONE = 0      //仅仅左边的title
        val FLAG_RIGHT_SHOW_IMAGE = 1
        val FLAG_RIGHT_SHOW_TEXT = 1 shl 1
        val FLAG_RIGHT_EDITABLE = 1 shl 2
        val FLAG_RIGHT_CHECKABLE = 1 shl 3
    }
}
