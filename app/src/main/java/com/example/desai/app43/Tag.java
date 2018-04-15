package com.example.desai.app43;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Tag implements Serializable {

    private String name;
    private String value;

    private ArrayList<Tag> taglist;

    /**
     *
     * @param tagname
     * @param tagValue
     */
    public Tag(String tagname,String tagValue){
        this.name = tagname;
        this.value = tagValue;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }


    /**
     *
     * @param tag
     * @return
     */

    public boolean equals(Tag tag){
        if(tag==null){
            return false;
        }
        return tag.getName().trim().equals(this.name) && tag.getValue().trim().equals(this.value);
    }

    @Override
    public String toString() {
        return  getName() + " : " + getValue();
    }
}
