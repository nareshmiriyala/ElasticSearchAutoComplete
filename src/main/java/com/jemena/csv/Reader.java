package com.jemena.csv;

import java.util.List;

/**
 * Created by nmiriyal on 27/07/2016.
 */
public interface Reader {
     List<String[]> read(String file);
}
