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
import austin.mysakuraapp.model.bean.WordResult;
import austin.mysakuraapp.utils.BusiUtils;
import austin.mysakuraapp.utils.StringUtil;

public class WordRecyclerViewAdapterB extends Adapter<ViewHolder> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_HEADER = 3;
    private Context context;
    private List<WordResult> data;

    public List<WordResult> getData() {
        return data;
    }

    public WordRecyclerViewAdapterB(Context context, List<WordResult> data) {
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
            HeadViewHolder holder = new HeadViewHolder(view);
            return holder;
        }
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_word, parent,
                    false);
            return new ItemViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));
            WordResult wordResult = data.get(position-1);
            ((ItemViewHolder) holder).tvWord.setText(wordResult.getWd_name());
            ((ItemViewHolder) holder).tvKanna.setText(wordResult.getWd_kana());
            int tone = wordResult.getWd_tone();
            ((ItemViewHolder) holder).tvTone.setText(BusiUtils.getToneEmoji(tone));
            //得到动词类型（他动词、自动词）
            String verbTypeStr = wordResult.getWd_verb_type_name();
            //得到动词属于几段（一段、五段）
            String wdTypeStr = wordResult.getWd_part_speech_name();
            boolean empty = StringUtil.isEmpty(verbTypeStr);
            String tempString = empty?wdTypeStr:(verbTypeStr+"·")+BusiUtils.getVerbTypeByInt(wdTypeStr);
            ((ItemViewHolder) holder).tvType.setText("【"+tempString+"】");
            ((ItemViewHolder) holder).tvCn.setText(wordResult.getWd_name_cn());

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

        TextView tvWord;
        TextView tvKanna;
        TextView tvType;
        TextView tvCn;
        TextView tvTone;

        public ItemViewHolder(View view) {
            super(view);
            tvTone = (TextView) view.findViewById(R.id.tv_tone);
            tvWord = (TextView) view.findViewById(R.id.tv_word);
            tvKanna = (TextView) view.findViewById(R.id.tv_kanna);
            tvType = (TextView) view.findViewById(R.id.tv_wordtype);;
            tvCn = (TextView) view.findViewById(R.id.tv_name_cn);
        }
    }

    static class HeadViewHolder extends ViewHolder {

        public HeadViewHolder(View view) {
            super(view);
        }
    }

}