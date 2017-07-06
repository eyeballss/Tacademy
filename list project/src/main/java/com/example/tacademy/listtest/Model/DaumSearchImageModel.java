package com.example.tacademy.listtest.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tacademy on 2017-07-05.
 */

public class DaumSearchImageModel {
    public Channel channel;

    public class Channel{
        int result;
        int pageCount;
        String title;
        public int totalCount;
        String description;
        public ArrayList<Items> item;
        String lastBuildDate;
        String link;
        String generator;

        public class Items implements Serializable {
            public String pubDate;
            public String title;
            public String thumbnail;
            String cp;
            int height;
            public String link;
            int width;
            String image;
            public String cpname;

        }
    }

}
