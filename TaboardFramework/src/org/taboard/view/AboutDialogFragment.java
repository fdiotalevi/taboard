package org.taboard.view;

import org.taboard.base.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


public class AboutDialogFragment extends DialogFragment {

    public static AboutDialogFragment newInstance(String title) {
    	AboutDialogFragment frag = new AboutDialogFragment();
        Bundle args = new Bundle();
        args.putString("text", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String text = getArguments().getString("text");

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.about_dialog_title)
                .setMessage(text)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();
    }
}