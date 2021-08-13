package com.enzo.commonlib.base;

/**
 * 文 件 名: BaseBean
 * 创 建 人: xiaofangyin
 * 创建日期: 2018/5/26
 * 邮   箱: xiaofangyinwork@163.com
 */
public class BaseBean {

    private EncourageBean encourage;

    public EncourageBean getEncourage() {
        return encourage;
    }

    public void setEncourage(EncourageBean encourage) {
        this.encourage = encourage;
    }

    public static class EncourageBean {
        /**
         * msg : 首次登录
         * score : 50
         */

        private String msg;
        private String score;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
