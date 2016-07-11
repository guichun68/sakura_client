package austin.mysakuraapp.fragments.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import austin.mysakuraapp.R;
import austin.mysakuraapp.utils.AppUtil;
import austin.mysakuraapp.utils.UIManager;


public class AboutMeFrag extends Fragment {
	private View view;

	private TextView tvVersionName;
	private ImageView ivBack;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_about_us,container,false);
		initView();
		return view;
	}

	private void initView() {
		tvVersionName = (TextView) view.findViewById(R.id.tv_version_name);
		ivBack = (ImageView) view.findViewById(R.id.iv_left);

		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIManager.getInstance().popBackStack(1);
			}
		});
		tvVersionName.setText(AppUtil.getAppVersionName(getContext()));
	}

}