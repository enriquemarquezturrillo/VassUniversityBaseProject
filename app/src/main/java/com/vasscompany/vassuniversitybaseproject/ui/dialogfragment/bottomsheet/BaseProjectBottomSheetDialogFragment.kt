package com.vasscompany.vassuniversitybaseproject.ui.dialogfragment.bottomsheet

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vasscompany.vassuniversitybaseproject.R
import com.vasscompany.vassuniversitybaseproject.data.domain.model.error.ErrorModel
import com.vasscompany.vassuniversitybaseproject.databinding.FragmentDialogBottomSheetBaseprojectBinding
import com.vasscompany.vassuniversitybaseproject.ui.extensions.gone
import com.vasscompany.vassuniversitybaseproject.ui.extensions.visible

class BaseProjectBottomSheetDialogFragment : BottomSheetDialogFragment() {

    interface BaseProjectBottomSheetDialogFragmentListener {
        fun onBaseProjectBottomSheetDialogFragmentClickClose()
        fun onBaseProjectBottomSheetDialogFragmentClickButtonPositive()
        fun onBaseProjectBottomSheetDialogFragmentClickButtonNegative()
    }

    companion object {
        const val BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG: String = "BASE_PROJECT_BOTTOM_SHEET_DIALOG_FRAGMENT_TAG"
    }

    private lateinit var binding: FragmentDialogBottomSheetBaseprojectBinding

    private var title: String = ""
    private var message: String = ""
    private var baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragmentListener? = null

    private var drawableIcon: Drawable? = null
    private var textButtonPositive: String? = null
    private var textButtonNegative: String? = null
    private var showClose: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.backgroundTransparentBottomSheetDialogFragment);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDialogBottomSheetBaseprojectBinding.inflate(inflater)
        paintDialog()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        binding =
            FragmentDialogBottomSheetBaseprojectBinding.inflate(context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener(DialogInterface.OnKeyListener { _, keyCode, _ -> return@OnKeyListener (keyCode == KeyEvent.KEYCODE_BACK) })

        paintDialog()

        return dialog
    }

    private fun paintDialog() {
        if (showClose != null && showClose) {
            binding.ivClose.visible()
            binding.ivClose.setOnClickListener {
                baseProjectBottomSheetDialogFragmentListener?.onBaseProjectBottomSheetDialogFragmentClickClose()
                dismiss()
            }
        } else {
            binding.ivClose.gone()
        }

        if (!textButtonPositive.isNullOrBlank()) {
            binding.btPositive.visible()
            binding.btPositive.text = textButtonPositive
            binding.btPositive.setOnClickListener {
                baseProjectBottomSheetDialogFragmentListener?.onBaseProjectBottomSheetDialogFragmentClickButtonPositive()
                dismiss()
            }
        } else {
            binding.btPositive.gone()
        }

        if (!textButtonNegative.isNullOrBlank()) {
            binding.btNegative.visible()
            binding.btNegative.text = textButtonNegative
            binding.btNegative.setOnClickListener {
                baseProjectBottomSheetDialogFragmentListener?.onBaseProjectBottomSheetDialogFragmentClickButtonNegative()
                dismiss()
            }
        } else {
            binding.btNegative.gone()
        }

        if (!title.isNullOrBlank()) {
            binding.tvTitle.visible()
            binding.tvTitle.text = title
        } else {
            binding.tvTitle.gone()
        }

        if (!message.isNullOrBlank()) {
            binding.tvMessage.visible()
            binding.tvMessage.text = message
        } else {
            binding.tvMessage.gone()
        }

        if (drawableIcon != null) {
            binding.ivIcon.setImageDrawable(drawableIcon)
        } else {
            binding.ivIcon.gone()
        }
    }

    fun setValue(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragmentListener,
        title: String,
        message: String,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        this.baseProjectBottomSheetDialogFragmentListener = baseProjectBottomSheetDialogFragmentListener
        this.title = title
        this.message = message
        this.drawableIcon = drawableIcon
        this.textButtonPositive = textButtonPositive
        this.textButtonNegative = textButtonNegative
        this.showClose = showClose
    }

    fun setValue(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragmentListener,
        errorModel: ErrorModel,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        setValue(
            baseProjectBottomSheetDialogFragmentListener,
            errorModel.error,
            errorModel.message,
            drawableIcon,
            textButtonPositive,
            textButtonNegative,
            showClose
        )
    }

    fun refreshValue(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragmentListener,
        title: String,
        message: String,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        setValue(
            baseProjectBottomSheetDialogFragmentListener,
            title,
            message,
            drawableIcon,
            textButtonPositive,
            textButtonNegative,
            showClose
        )
        paintDialog()
    }

    fun refreshValue(
        baseProjectBottomSheetDialogFragmentListener: BaseProjectBottomSheetDialogFragmentListener,
        errorModel: ErrorModel,
        drawableIcon: Drawable? = null,
        textButtonPositive: String? = null,
        textButtonNegative: String? = null,
        showClose: Boolean
    ) {
        setValue(baseProjectBottomSheetDialogFragmentListener, errorModel, drawableIcon, textButtonPositive, textButtonNegative, showClose)
        paintDialog()
    }
}