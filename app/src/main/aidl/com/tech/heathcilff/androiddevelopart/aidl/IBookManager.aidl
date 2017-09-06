// IBookManager.aidl
package com.tech.heathcilff.androiddevelopart.aidl;
import com.tech.heathcilff.androiddevelopart.aidl.Book;
import com.tech.heathcilff.androiddevelopart.aidl.IOnBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   List<Book> getBookList();
   void addBook(in Book book);
   void registerOnBookArrivedListener(in IOnBookArrivedListener onBookArrivedListener);
   void unregisterOnBookArrivedListener(in IOnBookArrivedListener onBookArrivedListener);
}
