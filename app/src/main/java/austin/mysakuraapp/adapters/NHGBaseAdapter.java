package austin.mysakuraapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class NHGBaseAdapter<T,Q> extends BaseAdapter {
	public Context context;
	public List<T> list;
	//当前的Q圈定泛型，当前的Q是view的子类
	public View Q;

	public NHGBaseAdapter(Context context, List<T> list, View Q) {
		this.context = context;
		this.list = list;
		this.Q = Q;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent) ;
}
