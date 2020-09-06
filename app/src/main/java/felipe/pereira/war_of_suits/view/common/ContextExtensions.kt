package felipe.pereira.war_of_suits.view.common

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun Context.showDialog(
    message: Int,
    textPositiveButton: Int,
    actionPositiveButton: () -> Unit,
    textNegativeButton: Int,
    actionNegativeButton: () -> Unit,
    isCancelable: Boolean
) {
    AlertDialog.Builder(this)
        .setMessage(message)
        .setPositiveButton(textPositiveButton) { _, _ -> actionPositiveButton.invoke() }
        .setNegativeButton(textNegativeButton) { _, _ -> actionNegativeButton.invoke()}
        .setCancelable(isCancelable)
        .show()
}