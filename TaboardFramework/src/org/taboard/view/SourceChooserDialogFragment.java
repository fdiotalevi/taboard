package org.taboard.view;

import org.taboard.base.DashBoardActivity;
import org.taboard.base.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;

public class SourceChooserDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// String text = getArguments().getString("text");

		return new AlertDialog.Builder(getActivity())
				.setTitle(R.string.source_chooser_dialog_title)
				.setItems(((DashBoardActivity)getActivity()).getArrayLabelSourceResourceId(), new OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {
						Log.v("D", "" + whichButton);
						((DashBoardActivity) getActivity()).addSource(whichButton);
					}
				}).create();
	}

}
