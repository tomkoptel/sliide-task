@file:JvmName("ViewExt")

package android.view

import android.util.TypedValue
import androidx.annotation.AttrRes

/**
 * The extension function that returns resolved resource from the theme applied to the current view.
 */
fun View.getValueFromAttr(
    @AttrRes attrRes: Int,
    typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = false
): Int {
    context.theme.resolveAttribute(attrRes, typedValue, resolveRefs)
    return typedValue.data
}
