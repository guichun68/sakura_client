package austin.mysakuraapp.fragments.SkrBunnpo;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.GlobalParams;
import austin.mysakuraapp.model.bean.SakuraResult;

public class SkrRecyclerViewAdapterB extends Adapter<ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER = 3;
    private Context context;
    private List<SakuraResult> data;

    public List<SakuraResult> getData() {
        return data;
    }

    public SkrRecyclerViewAdapterB(Context context, List<SakuraResult> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if(data == null)return 0;
        return data.size() == 0 ? 0 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_HEADER;
        }
        else{
            return TYPE_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            CardView view = new CardView(context);
            RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalParams.TAB_LAYOUT_HEIGHT);
            view.setLayoutParams(param);
            return new HeadViewHolder(view);
        }
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sentence, parent,
                    false);
            return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            SakuraResult skrResult = data.get(position-1);
            ((ItemViewHolder) holder).tvNo.setText(String.valueOf(position));

            String tmpString;
            if(!GlobalParams.showNihonngo)
            {
                tmpString = skrResult.getSkrSentenceCn().replace("|", "\n");
                ((ItemViewHolder) holder).tvSentence.setText(tmpString);
            }else{
                tmpString = skrResult.getSkrSentenceJP().replace("|", "\n");
                ((ItemViewHolder) holder).tvSentence.setText(tmpString);
            }

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }


    static class ItemViewHolder extends ViewHolder {

        TextView tvSentence;
        /**
         * 句子序号
         */
        TextView tvNo;

        public ItemViewHolder(View view) {
            super(view);
            tvSentence = (TextView) view.findViewById(R.id.tv_sentence);
            tvNo = (TextView) view.findViewById(R.id.tv_no);
        }
    }

    static class HeadViewHolder extends ViewHolder {

        public HeadViewHolder(View view) {
            super(view);
        }
    }

}