package com.somoplay.screenshow.thread;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
  User preferences, using a read-write lock. 
  
 <P>The context: preference information is read in upon startup.
 The config data is 'read-mostly': usually, a caller simply reads the 
 information. It gets updated only occasionally. 
 
 Using a read-write lock means that multiple readers can access the
 same data simultaneously. If a single writer gets the lock, however, then 
 all other callers (either reader or writer) will block until the lock is 
 released by the writer.
 
  <P>(In practice, Brian Goetz reports that the implementation of ConcurrentHashMap 
  is so good that it would likely suffice in many cases, instead of a read-write lock.)
*/
public final class ReadAndWriteLock {
  
  /** Fetch a setting - this is the more common operation.  */
  public String fetch(String aName){
    String result = "";

    fReadLock.lock();
    try {
      result = fPreferences.get(aName);
    }
    finally {
      fReadLock.unlock();
    }
    return result;
  }
 
  /** Change a setting - this is the less common operation. */
  public void update(String aName, String aValue){
    fWriteLock.lock();
    try {
      fPreferences.put(aName, aValue);
    }
    finally {
      fWriteLock.unlock();
    }
  }

  
  /** Holds the preferences as simple name-value pairs of Strings. */
  private final Map<String, String> fPreferences = new LinkedHashMap<>();
  private final ReentrantReadWriteLock fLock = new ReentrantReadWriteLock();
  private final Lock fReadLock = fLock.readLock();
  private final Lock fWriteLock = fLock.writeLock();


}