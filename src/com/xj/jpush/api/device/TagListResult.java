package com.xj.jpush.api.device;

import java.util.ArrayList;
import java.util.List;

import com.xj.jpush.api.common.resp.BaseResult;

import com.google.gson.annotations.Expose;

public class TagListResult extends BaseResult {
    
    @Expose public List<String> tags = new ArrayList<String>();
    
}

