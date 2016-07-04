package austin.mysakuraapp.utils;

import android.content.Context;

import austin.mysakuraapp.R;
import austin.mysakuraapp.comm.ConstantValue;
import austin.mysakuraapp.comm.GlobalParams;

/**
 * Created by austin on 2016/6/29.
 * Desc: 业务相关工具类
 */
public class BusiUtils {

    /**
     * 通过给定的类别(从ConstanValue取值)，分析其对应的服务器端地址并返回，目前之针对单词中心使用
     * @param classify 依据类别，如ConstantValue.NOUN_TYPE_ANIMAL
     * @return 对应服务器的url
     */
    public static String parseUrlByClassfi(int classify) {
        switch (classify){
            case ConstantValue.NOUN_TYPE_ANIMAL:
            case ConstantValue.NOUN_TYPE_PLANT:
            case ConstantValue.NOUN_TYPE_VEHICLE:
            case ConstantValue.NOUN_TYPE_OTHER:
                return GlobalParams.URL_NOUNS;
            case ConstantValue.VERB_TYPE_ONE:
            case ConstantValue.VERB_TYPE_TWO:
            case ConstantValue.VERB_TYPE_THREE:
                return GlobalParams.URL_VERBS;
            case ConstantValue.ADJ_TYPE_ONE:
            case ConstantValue.ADJ_TYPE_TWO:
                return GlobalParams.URL_ADJS;
            case ConstantValue.OTHER_TYPE_ONE:
            case ConstantValue.OTHER_TYPE_TWO:
            case ConstantValue.OTHER_TYPE_ALL:
                return GlobalParams.URL_OTHER;
        }
        return null;
    }

    /**
     * 根据整型音型得到字符型 音型，如传入 5 返回 ⑤
     * @param tone
     * @return
     */
    public static String getToneEmoji( int tone){
        String toneStr = null;
        switch (tone) {
            case 0:
                toneStr = UIUtil.getContext().getResources().getString(R.string.zero_tone);
                break;
            case 1:
                toneStr = UIUtil.getContext().getResources().getString(R.string.one_tone);
                break;
            case 2:
                toneStr = UIUtil.getContext().getResources().getString(R.string.two_tone);
                break;
            case 3:
                toneStr = UIUtil.getContext().getResources().getString(R.string.three_tone);
                break;
            case 4:
                toneStr = UIUtil.getContext().getResources().getString(R.string.four_tone);
                break;
            case 5:
                toneStr = UIUtil.getContext().getResources().getString(R.string.five_tone);
                break;
            case 6:
                toneStr = UIUtil.getContext().getResources().getString(R.string.six_tone);
                break;
            case 7:
                toneStr = UIUtil.getContext().getResources().getString(R.string.seven_tone);
                break;
            case 8:
                toneStr = UIUtil.getContext().getResources().getString(R.string.eight_tone);
                break;
            case 9:
                toneStr = UIUtil.getContext().getResources().getString(R.string.nine_tone);
                break;
            case 10:
                toneStr = UIUtil.getContext().getResources().getString(R.string.ten_tone);
                break;
            case 100:
                toneStr = null;
                break;
        }
        return toneStr;
    }

    /**
     * 得到动词的全名称信息，比如传参“动词1类”返回“五段/1类”
     * @param
     * @return 得到动词的全名称信息
     */
    public static String getVerbTypeByInt(String wdTypeStr){
        String wdDuanString = "";
        if(!StringUtil.isEmpty(wdTypeStr)){
            if(wdTypeStr.equals("动词1类")){
                wdDuanString = "五段/1类";
            }else if(wdTypeStr.equals("动词2类")){
                wdDuanString = "一段/2类";
            }else {
                wdDuanString = wdTypeStr;
            }
        }
        return wdDuanString;
    }
}
