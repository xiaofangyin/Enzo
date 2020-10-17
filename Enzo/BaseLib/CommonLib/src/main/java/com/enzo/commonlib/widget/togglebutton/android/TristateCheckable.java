package com.enzo.commonlib.widget.togglebutton.android;

/**
 * Created by Riccardo Moro on 19/08/2016.
 */
public interface TristateCheckable {
    void toggle();

    void setState(@RMTristateSwitch.State int state);

    @RMTristateSwitch.State
    int getState();
}
