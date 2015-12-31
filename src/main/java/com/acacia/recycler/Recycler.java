package com.acacia.recycler;

/**
 *  Process:
 *   1. increment or set 'error_count' counter
 *   2. if error_count greater or equal to reject threshold, send to dead letter store
 *   3. send message to "original input" topic
 */

import com.acacia.sdk.AbstractTransform;
import com.acacia.sdk.AbstractTransformComposer;
import com.google.auto.service.AutoService;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import org.json.JSONObject;

@AutoService(AbstractTransform.class)
public class Recycler extends AbstractTransform  {
    private static int MAX_ERROR_COUNT = 5;
    
    @Override
    public String transform(String s) {
        JSONObject message = new JSONObject(s);
        
        int error_count = message.optInt("error_count") + 1;
        if (error_count >= MAX_ERROR_COUNT) {
            //  add a write somewhere later.  make sure the rest works first
            return null;
        }
        
        message.put("error_count", error_count);
        //  keep track of being here
        message.append("path", "recycler");
        
        return message.toString();
    }

    public Recycler() {
        super();
    }
}

