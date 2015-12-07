package com.google.android.gms.example.bannerexample.com.example.parsing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sand on 11/25/15.
 */
public class InfoData {
    @SerializedName("id")
    public String id;
    @SerializedName("sora_order")
    public String soraOrder;
    @SerializedName("sora_name")
    public String soraName;
    @SerializedName("tarteb_elnzol")
    public String tartebElnzol;
    @SerializedName("ayat_number")
    public String ayatNumber;
    @SerializedName("words_number")
    public String wordsNumber;
    @SerializedName("characters_number")
    public String charNumber;
    @SerializedName("mkan_elnzol")
    public String mkanElnzol;


}
