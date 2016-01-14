package com.acacia.recycler;

/**
 *  Process:
 *   1. increment or set 'error_count' counter
 *   2. if error_count greater or equal to reject threshold, send to dead letter store
 *   3. send message to "original input" topic
 */

import com.acacia.sdk.AbstractTransform;
import com.acacia.sdk.AbstractTransformComposer;
import com.acacia.sdk.GenericDataflowAppException;
import com.google.auto.service.AutoService;
import com.google.cloud.dataflow.sdk.transforms.DoFn;
import org.json.JSONObject;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@AutoService(AbstractTransform.class)
public class Recycler extends AbstractTransform  {
    private static int MAX_ERROR_COUNT = 5;
    private static int MIN_RETRY_SECONDS = 5; // 5 seconds
    
    @Override
    public String transform(String s) throws GenericDataflowAppException {
        JSONObject message;
        try {
            message = new JSONObject(s);
        } catch (Exception e){
            throw new GenericDataflowAppException(s + "<- message is not a json object, error is: " + e.getMessage());
        }
        
        // retry check
        int error_count = message.optInt("error_count") + 1;
        if (error_count >= MAX_ERROR_COUNT) {
            //  add a write somewhere later.  make sure the rest works first
            throw new GenericDataflowAppException(message.toString());
        }
        
        // wait check
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime last_seen;
        if (message.has("last_seen")) {
            last_seen = ZonedDateTime.parse(message.getString("last_seen"));
            long now_epoch = now.toEpochSecond();
            long last_seen_epoch = last_seen.toEpochSecond();
            if ((last_seen_epoch + MIN_RETRY_SECONDS) > now_epoch) {
                try {
                    TimeUnit.SECONDS.sleep(last_seen_epoch + MIN_RETRY_SECONDS - now_epoch);
                } catch (InterruptedException e){
                    // no-op
                }
            }
        }
        
        message.put("last_seen",  now.toString());
        
        message.put("error_count", error_count);
        //  keep track of being here
        message.append("path", "recycler");
        
        return message.toString();
    }

    public Recycler() {
        super();
    }
}

