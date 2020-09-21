package wengxiaoyang.personalfinanceassistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class PhotoDetailFragment extends DialogFragment {
    private static final String ARG_FILE = "file";
    private ImageView mPhotoView;

    // 照片细节
    public static PhotoDetailFragment newInstance(File file) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FILE, file);

        PhotoDetailFragment fragment =new PhotoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 创建对话框
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        File file = (File) getArguments().getSerializable(ARG_FILE);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_photo, null);
        mPhotoView = v.findViewById(R.id.manage_photo_detail);
        Bitmap bitmap = PictureUtils.getScaledBitmap(file.getPath(), getActivity());
        mPhotoView.setImageBitmap(bitmap);

        return new AlertDialog.Builder(getActivity()).setView(v)
                .setPositiveButton(android.R.string.ok, null).create();
    }
}
