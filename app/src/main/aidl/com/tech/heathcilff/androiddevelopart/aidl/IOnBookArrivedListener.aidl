// IOnBookArrivedListener.aidl
package com.tech.heathcilff.androiddevelopart.aidl;
import com.tech.heathcilff.androiddevelopart.aidl.Book;
// Declare any non-default types here with import statements

interface IOnBookArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

	void onBookArrived(in Book book);
}
