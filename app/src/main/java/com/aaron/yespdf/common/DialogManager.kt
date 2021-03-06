package com.aaron.yespdf.common

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aaron.yespdf.R
import com.aaron.yespdf.common.utils.DialogUtils
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * @author Aaron aaronzzxup@gmail.com
 */
object DialogManager {

    fun createLoadingDialog(context: Context): Dialog {
        return DialogUtils.createDialog(context, R.layout.app_dialog_loading)
    }

    fun createQrcodeDialog(context: Context): Dialog {
        return DialogUtils.createDialog(context, R.layout.app_dialog_qrcode)
    }

    fun createAlertDialog(context: Context, callback: (TextView, TextView, Button) -> Unit): Dialog {
        val view = LayoutInflater.from(context.applicationContext).inflate(R.layout.app_dialog_alert, null)
        val tvTitle = view.findViewById<TextView>(R.id.app_tv_title)
        val tvContent = view.findViewById<TextView>(R.id.app_tv_content)
        val btn = view.findViewById<Button>(R.id.app_btn)
        callback(tvTitle, tvContent, btn)
        val dialog = DialogUtils.createDialog(context, view)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun createInputDialog(context: Context, callback: (TextView, EditText, Button, Button) -> Unit): Dialog {
        val view = LayoutInflater.from(context.applicationContext).inflate(R.layout.app_dialog_input, null)
        val tvTitle = view.findViewById<TextView>(R.id.app_tv_title)
        val etInput = view.findViewById<EditText>(R.id.app_et_input)
        val btnCancel = view.findViewById<Button>(R.id.app_btn_cancel)
        val btnConfirm = view.findViewById<Button>(R.id.app_btn_confirm)
        callback(tvTitle, etInput, btnCancel, btnConfirm)
        val dialog = DialogUtils.createDialog(context, view)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun createDoubleBtnDialog(context: Context, callback: (TextView, TextView, Button, Button) -> Unit): Dialog {
        val inflater = LayoutInflater.from(context.applicationContext)
        val dialogView = inflater.inflate(R.layout.app_dialog_double_btn, null)
        val dialog = DialogUtils.createDialog(context, dialogView)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val tvTitle = dialogView.findViewById<TextView>(R.id.app_tv_title)
        val tvContent = dialogView.findViewById<TextView>(R.id.app_tv_content)
        val btnLeft = dialogView.findViewById<Button>(R.id.app_btn_left)
        val btnRight = dialogView.findViewById<Button>(R.id.app_btn_right)
        callback(tvTitle, tvContent, btnLeft, btnRight)
        return dialog
    }

    fun createDeleteDialog(context: Context, callback: (TextView, View, Button, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_delete, null)
        val tvDeleteDescription = view.findViewById<TextView>(R.id.app_tv_description)
        val localDelete = view.findViewById<View>(R.id.app_delete_local)
        val btnCancel = view.findViewById<Button>(R.id.app_btn_cancel)
        val btnDelete = view.findViewById<Button>(R.id.app_btn_delete)
        callback(tvDeleteDescription, localDelete, btnCancel, btnDelete)
        return DialogUtils.createBottomSheetDialog(context, view)
    }

    fun createGiftDialog(context: Context, callback: (Button, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_gift, null)
        val btnOpenQrcode = view.findViewById<Button>(R.id.app_btn_open_qrcode)
        val btnGoWechat = view.findViewById<Button>(R.id.app_btn_go_wechat)
        callback(btnOpenQrcode, btnGoWechat)
        return DialogUtils.createBottomSheetDialog(context, view, R.style.AppRegroupingAnim, true)
    }

    fun createImportDialog(context: Context, callback: (EditText, Button, Button, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_import, null)
        val etInput = view.findViewById<EditText>(R.id.app_et_input)
        val btnImportExist = view.findViewById<Button>(R.id.app_btn_import_exist)
        val btnBaseFolder = view.findViewById<Button>(R.id.app_btn_base_folder)
        val btnConfirm = view.findViewById<Button>(R.id.app_btn_confirm)
        callback(etInput, btnImportExist, btnBaseFolder, btnConfirm)
        return DialogUtils.createBottomSheetDialog(context, view)
    }

    fun createGroupingDialog(context: Context, enableAddNew: Boolean, callback: GroupingAdapter.Callback?): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_grouping, null)
        val rv: RecyclerView = view.findViewById(R.id.app_rv_group)
        rv.addItemDecoration(XGridDecoration())
        rv.addItemDecoration(YGridDecoration())
        val lm = GridLayoutManager(context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (Settings.linearLayout) 3 else 1
                }
            }
        }
        rv.layoutManager = lm
        val list = DataManager.getCoverList()
        val adapter: RecyclerView.Adapter<*> = GroupingAdapter(lm, list, callback, enableAddNew)
        rv.adapter = adapter
        val dialog = DialogUtils.createBottomSheetDialog(context, view, R.style.AppRegroupingAnim, true)
        dialog.setOnDismissListener { dialogTemp: DialogInterface? -> rv.scrollToPosition(0) }
        return dialog
    }

    fun createScanDialog(context: Context, callback: (TextView, TextView, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_scan, null)
        val tvScanCount = view.findViewById<TextView>(R.id.app_tv_scan_count)
        val tvPdfCount = view.findViewById<TextView>(R.id.app_tv_pdf_count)
        val btnStopScan = view.findViewById<Button>(R.id.app_btn_stop_scan)
        callback(tvScanCount, tvPdfCount, btnStopScan)
        val scanDialog = DialogUtils.createBottomSheetDialog(context, view)
        scanDialog.setCanceledOnTouchOutside(false)
        scanDialog.setCancelable(false)
        return scanDialog
    }

    fun createImportInfoDialog(context: Context, callback: (TextView, ProgressBar, TextView, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_bottomdialog_import_info, null)
        val tvTitle = view.findViewById<TextView>(R.id.app_tv_title)
        val progressBar = view.findViewById<ProgressBar>(R.id.app_progress_bar)
        val tvProgressInfo = view.findViewById<TextView>(R.id.app_tv_progress)
        val btnStopImport = view.findViewById<Button>(R.id.app_btn_stop_import)
        callback(tvTitle, progressBar, tvProgressInfo, btnStopImport)
        val scanDialog = DialogUtils.createBottomSheetDialog(context, view)
        scanDialog.setCanceledOnTouchOutside(false)
        scanDialog.setCancelable(false)
        return scanDialog
    }

    fun createBackupDialog(context: Context, callback: (Dialog, ViewGroup, CheckBox, RecyclerView, Button, Button) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.app_bottomdialog_backup, null)
        val selectAll = view.findViewById<ViewGroup>(R.id.app_all)
        val cb = view.findViewById<CheckBox>(R.id.app_cb)
        val rv = view.findViewById<RecyclerView>(R.id.app_rv)
        val backup = view.findViewById<Button>(R.id.app_backup)
        val recovery = view.findViewById<Button>(R.id.app_recovery)
        return DialogUtils.createBottomSheetDialog(context, view).apply {
            callback(this, selectAll, cb, rv, backup, recovery)
            setCanceledOnTouchOutside(true)
            setCancelable(true)
        }
    }

    fun createBackupUpdateDialog(context: Context, callback: (TextView, ImageView, TextView, View, Dialog) -> Unit): BottomSheetDialog {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.app_bottomdialog_backup_update, null)
        val title = view.findViewById<TextView>(R.id.app_title)
        val progress = view.findViewById<ImageView>(R.id.app_progress)
        val result = view.findViewById<TextView>(R.id.app_result)
        val ok = view.findViewById<View>(R.id.app_ok)
        return DialogUtils.createBottomSheetDialog(context, view).apply {
            callback(title, progress, result, ok, this)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }

    fun createAutoScrollTipsDialog(context: Context, callback: (View, SeekBar, Animatable?, Button, Dialog) -> Unit) {
        val view = LayoutInflater.from(context.applicationContext)
                .inflate(R.layout.app_dialog_auto_scroll_tips, null)
        val arrow = view.findViewById<View>(R.id.app_arrow)
        val drawable = view.findViewById<ImageView>(R.id.app_play_or_pause).drawable as Animatable
        val seekbar = view.findViewById<SeekBar>(R.id.app_scroll_level)
        val btn = view.findViewById<Button>(R.id.app_got_it)
        callback(arrow, seekbar, drawable, btn, DialogUtils.createDialog(context, view).apply {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        })
    }
}