package com.github.linfeng.view;

/**
 * 检验授权凭证（access_token）是否有效.
 *
 * @author 黄麟峰
 * @date 2020-12-03
 */
public class CheckAccessTokenResponseView extends BaseResponseView {

    public CheckAccessTokenResponseView(String json) {
        /*
         * 返回说明 正确的JSON返回结果：
         *
         * { "errcode":0,"errmsg":"ok"}
         */
        super(json);
    }
}
