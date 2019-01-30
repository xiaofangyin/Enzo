package com.enzo.commonlib.widget.timeclock;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化
 *
 * Created by zhaolei on 2017/4/22.
 */

public class SHScheduleBean implements Parcelable {

    public static final String SCHEDULE_NORMAL_NIGHTLIGHT = "normal_nightlight";
    public static final String SCHEDULE_INFRARED_NIGHTLIGHT = "infrared_nightlight";
    public static final String SCHEDULE_LIGHT_SENSOR_NIGHTLIGHT = "light_sensor_nightlight";
    public static final String SCHEDULE_HOME_MODE = "home_mode";
    public static final String SCHEDULE_OUTSIDE_MODE = "outside_mode";

    /**
     * type : normal_nightlight
     * display_on_watch : true/false 是否显示在表盘
     * item : [{"id":"xxxx","on":"xx:xx","off":"xx:xx","enable":true,"date":"2017-4-14","period":"1,2,3,4,5,6,7"}]
     */

    private String type;
    private boolean display_on_watch;
    private List<ItemEntity> item;

    public SHScheduleBean() {
    }

    protected SHScheduleBean(Parcel in) {
        type = in.readString();
        display_on_watch = in.readByte() != 0;
        item = in.createTypedArrayList(ItemEntity.CREATOR);
    }

    public static final Creator<SHScheduleBean> CREATOR = new Creator<SHScheduleBean>() {
        @Override
        public SHScheduleBean createFromParcel(Parcel in) {
            return new SHScheduleBean(in);
        }

        @Override
        public SHScheduleBean[] newArray(int size) {
            return new SHScheduleBean[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDisplayOnWatch() {
        return display_on_watch;
    }

    public void setDisplayOnWatch(boolean displayOnWatch) {
        this.display_on_watch = displayOnWatch;
    }

    public List<ItemEntity> getItem() {
        return item;
    }

    public void setItem(List<ItemEntity> item) {
        this.item = item;
    }

    public void updateItem(ItemEntity newItem) {
        for(ItemEntity i : item) {
            if(i.getId().equals(newItem.getId())) {
                item.set(item.indexOf(i), newItem);
            }
        }
    }

    public void addItem(ItemEntity newItem) {
        if(item == null) {
            item = new ArrayList<>();
        }
        item.add(newItem);
    }

    @Override
    public SHScheduleBean clone() {
        SHScheduleBean schedule = new SHScheduleBean();
        schedule.type = type;
        schedule.display_on_watch = display_on_watch;
        List<ItemEntity> itemList = new ArrayList<>();
        for(ItemEntity i : item) {
            itemList.add(i.clone());
        }
        schedule.item = itemList;
        return schedule;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeByte((byte) (display_on_watch ? 1 : 0));
        parcel.writeTypedList(item);
    }

    public static class ItemEntity implements Parcelable {
        /**
         * id : xxxx(用于唯一标示某个条目，可以由其余参数组合生成)
         * on : xx:xx
         * off : xx:xx
         * enable : 1/0
         * date : 2017-4-14
         * period : 1,2,3,4,5,6,7
         */

        private String id;
        private String on;
        private String off;
        private int enable;
        private String date;
        private String period;

        public ItemEntity() {
        }

        protected ItemEntity(Parcel in) {
            id = in.readString();
            on = in.readString();
            off = in.readString();
            enable = in.readInt();
            date = in.readString();
            period = in.readString();
        }

        public static final Creator<ItemEntity> CREATOR = new Creator<ItemEntity>() {
            @Override
            public ItemEntity createFromParcel(Parcel in) {
                return new ItemEntity(in);
            }

            @Override
            public ItemEntity[] newArray(int size) {
                return new ItemEntity[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOn() {
            return on;
        }

        public void setOn(String on) {
            this.on = on;
        }

        public String getOff() {
            return off;
        }

        public void setOff(String off) {
            this.off = off;
        }

        public int isEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public boolean isAfternoon() {
            return Integer.valueOf(getHourFromTime(on)) > 12;
        }

        public int getStartTime() {
            return Integer.valueOf(getHourFromTime(on)) * 60 + Integer.valueOf(getMinuteFromTime(on));
        }

        public static String getHourFromTime(String time) {
            if(time != null && time.contains(":")) {
                String[] HHmm = time.split(":");
                return HHmm[0];
            }
            return "";
        }

        public static String getMinuteFromTime(String time) {
            if(time.contains(":")) {
                String[] HHmm = time.split(":");
                return HHmm[1];
            }
            return "";
        }

        @Override
        public ItemEntity clone() {
            ItemEntity item = new ItemEntity();
            item.id = id;
            item.on = on;
            item.off = off;
            item.enable = enable;
            item.date = date;
            item.period = period;
            return item;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(on);
            parcel.writeString(off);
            parcel.writeInt(enable);
            parcel.writeString(date);
            parcel.writeString(period);
        }
    }
}
