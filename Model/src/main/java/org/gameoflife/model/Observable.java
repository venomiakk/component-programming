package org.gameoflife.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Observable<T> implements Serializable {
    private final List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> obs) {
        observers.add(obs);
    }

    public void notifyObservers() {
        for (Observer<T> obs : observers) {
            obs.update();
        }
    }

    /*getter na potrzby testow*/
    public List<Observer<T>> getObservers() {
        return this.observers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("observers", observers.toArray())
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Observable rhs = (Observable) obj;
        return new EqualsBuilder()
                .append(observers, rhs.observers)
                .isEquals();
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(3, 7)
                .append(observers)
                .toHashCode();
    }

}
