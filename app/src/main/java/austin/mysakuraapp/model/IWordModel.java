package austin.mysakuraapp.model;

import java.util.HashMap;

import austin.mysakuraapp.engine.OnResultListener;

/**
 * Created by austin on 2016/6/29.
 * Desc: 单词model
 */
public interface IWordModel {
    /**抽出的通用方法，获得 词汇中心--》某一个大类下(如形容词)--》某一个单独类别的数据（如一类形容词）
     * @param url 值取自GlobalParams.URL_ADJ_...用来告诉接口你要获得那种词性的单词
     * @param param 相应的参数
     * @param listener 回调
     */
    void getWordData(String url, HashMap param, OnResultListener listener);
}
