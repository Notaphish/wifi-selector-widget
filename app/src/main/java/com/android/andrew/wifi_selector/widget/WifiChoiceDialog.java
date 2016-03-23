package com.android.andrew.wifi_selector.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.android.andrew.wifi_selector.R;


/**
 * Created by andre on 22/03/2016.
 */
public class WifiChoiceDialog extends DialogFragment {

    private OnClickListener listener;

    @Override
    public void onAttach(Activity source) {
        super.onAttach(source);
        listener = (OnClickListener) source;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setTitle(R.string.choice_dialog_title).setItems(R.array.choice_dialog_options, listener).create();
    }
}
