package wengxiaoyang.personalfinanceassistant;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

public class ManageFragment extends Fragment {

    private static final String ARG_MANAGE_ID = "manage_id";
    private static final String DIALOG_DATE = "dialogDate";
    private static final String DIALOG_TIME = "dialogTime";
    private static final String DIALOG_PHOTO = "dialogPhoto";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 0;
    private static final int REQUEST_PHOTO = 3;

    private Manage mManage;
    private File mPhotoFile;
    private EditText mTitleField;
    private EditText mMoneyField;
    private Button mDateButton;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioButton_cash;
    private RadioButton mRadioButton_card;
    private EditText mRemarkField;
    private Button mTimeButton;
    private Button mReportButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;

    //根据ID创建实例
    public static ManageFragment newInstance(UUID manageId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MANAGE_ID, manageId);

        ManageFragment fragment = new ManageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // 保存部分数据
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID manageId = (UUID) getArguments().getSerializable(ARG_MANAGE_ID);
        mManage = ManageLab.get(getActivity()).getManage(manageId);
        setHasOptionsMenu(true);
        mPhotoFile = ManageLab.get(getActivity()).getPhotoFile(mManage);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 更新数据库数据
        ManageLab.get(getActivity()).updateManage(mManage);
    }

    // UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manage, container, false);

        // 标题
        mTitleField = v.findViewById(R.id.manage_title);
        mTitleField.setText(mManage.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mManage.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 金额
        mMoneyField = v.findViewById(R.id.manage_money);
        mMoneyField.setText(mManage.getMoney());
        mMoneyField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mManage.setMoney(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 日期
        mDateButton = v.findViewById(R.id.manage_date);
        Date date = mManage.getDate();
       /* DateFormat format1 = new SimpleDateFormat("EEE, MMM dd, YYYY");//星期,月份 ,几号,年份
        String dateFormat1 = format1.format(date);*/
        //String dateFormat1 = "EEE, MMM dd, YYYY";
        //String dateString1 = (String) DateFormat.format("EEE, MMM dd, yyyy", date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mManage.getDate());
                dialog.setTargetFragment(ManageFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // 时间
        mTimeButton = v.findViewById(R.id.manage_time);
        /*DateFormat format2 = new SimpleDateFormat("h:mm a");//
        String dateFormat2 = format2.format(date);*/
        String dateString2 = (String) DateFormat.format("h:mm a", date);
        updateTime(dateString2);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mManage.getDate());
                dialog.setTargetFragment(ManageFragment.this, REQUEST_TIME);
                dialog.show(Objects.requireNonNull(manager), DIALOG_TIME);  //第二个参数是tag
            }
        });

        // 支付方式
        mRadioGroup = v.findViewById(R.id.manage_pay_method);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Manage manage = new Manage();
                switch (checkedId) {
                    case R.id.manage_cash:
                        manage.setPayMethod(1);
                        break;
                    case R.id.manage_card:
                        manage.setPayMethod(2);
                        break;
                    default:
                        break;
                }
            }
        });

        System.out.println(mManage.getPayMethod());

        mRadioButton_cash = v.findViewById(R.id.manage_cash);
        mRadioButton_card = v.findViewById(R.id.manage_card);
        //mRadioGroup.clearCheck();
        int PayMethod = mManage.getPayMethod();
        if (PayMethod == 1) {
            mRadioButton_cash.setChecked(true);
        } else if (PayMethod == 2) {
            mRadioButton_card.setChecked(true);
        }
        //mRadioButton_card.setChecked(true);

        // 备注
        mRemarkField = v.findViewById(R.id.manage_remark);
        mRemarkField.setText(mManage.getRemark());
        mRemarkField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mManage.setRemark(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 发送报告
        mReportButton = v.findViewById(R.id.manage_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareCompat.IntentBuilder i = ShareCompat.IntentBuilder.from(getActivity());
                i.setType("text/plain");
                i.setText(getManageReport());
                i.setSubject(getString(R.string.manage_report_subject));
                i.createChooserIntent();
                i.startChooser();
            }
        });

        PackageManager packageManager = getActivity().getPackageManager();

        // 拍照
        mPhotoButton = v.findViewById(R.id.manage_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 如果不能拍照就禁用按钮
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        // 跳转到相机
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "wengxiaoyang.personalfinanceassistant.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                        .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        // 显示照片
        mPhotoView = v.findViewById(R.id.manage_photo);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoFile == null || !mPhotoFile.exists()) {
                    mPhotoView.setImageDrawable(null);
                } else {
                    FragmentManager manager = getFragmentManager();
                    PhotoDetailFragment dialog = PhotoDetailFragment.newInstance(mPhotoFile);
                    dialog.setTargetFragment(ManageFragment.this, REQUEST_PHOTO);
                    dialog.show(manager, DIALOG_PHOTO);
                }
            }
        });

        ViewTreeObserver mPhotoObserver = mPhotoView.getViewTreeObserver();
        mPhotoObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                updatePhotoView(mPhotoView.getWidth(), mPhotoView.getHeight());
            }
        });

        return v;
    }

    // 根据需求更新视图
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        /*Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
        String dateFormat1 = (String) DateFormat.format("EEE, MMM dd, yyyy", date);
        String dateFormat2 = (String)DateFormat.format("h:mm a", date);
        mCrime.setDate(mCrime.getDate());*/
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mManage.setDate(date);
            updateDate();
        }
        if (requestCode == REQUEST_TIME) {
            Date date = mManage.getDate();
            String dateString2 = (String) DateFormat.format("h:mm a", date);
            updateTime(dateString2);
        }
        else if (requestCode == REQUEST_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "wengxiaoyang.personalfinanceassistant.fileprovider", mPhotoFile);
            getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView(mPhotoView.getWidth(), mPhotoView.getHeight());
        }
    }

    // 创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_manage, menu);
    }

    // 根据选择响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_manage:
                ManageLab.get(getActivity()).removeManage(mManage);
                getActivity().finish();
                return true;
            case android.R.id.home:     //向上导航时保证子标题可见状态
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateDate() {
        mDateButton.setText(mManage.getDate().toString());
    }

    private void updateTime(String s) {

        mTimeButton.setText(s);
    }

    // 发送报告
    private String getManageReport() {
        String payString = null;
        if (mManage.getPayMethod() == 1) {
            payString = getString(R.string.manage_report_cash);
        } else if (mManage.getPayMethod() == 2){
            payString = getString(R.string.manage_report_card);
        }


        /*DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, YYYY");
        String dateString = dateFormat.format(mCrime.getDate().toString());*/

        /*String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();*/

        String dateString = (String) DateFormat.format("EEE, MMM dd, yyyy", mManage.getDate());


        String report = getString(R.string.manage_report,
                mManage.getTitle(), dateString, payString, mManage.getRemark());

        return report;
    }

    // 更新照片视图
    private void updatePhotoView(int width, int height) {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getPath(), width, height);
            mPhotoView.setImageBitmap(bitmap);
        }
    }

}
