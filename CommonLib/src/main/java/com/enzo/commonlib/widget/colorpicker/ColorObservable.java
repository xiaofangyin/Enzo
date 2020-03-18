package com.enzo.commonlib.widget.colorpicker;

public interface ColorObservable {

    void subscribe(ColorObserver observer);

    void unsubscribe(ColorObserver observer);

    int getColor();
}
