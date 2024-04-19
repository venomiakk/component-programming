package org.gameoflife.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class BidirectionalBinding {
    public ObjectProperty<Object> propertyA = new SimpleObjectProperty<>();
    public ObjectProperty<Object> propertyB = new SimpleObjectProperty<>();

    public BidirectionalBinding(Object a, Object b)
    {
        propertyA.set(a);
        propertyB.set(b);
        Bindings.bindBidirectional(propertyA, propertyB);
    }


}
