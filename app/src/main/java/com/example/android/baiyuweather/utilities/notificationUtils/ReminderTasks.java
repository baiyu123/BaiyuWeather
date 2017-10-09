package com.example.android.baiyuweather.utilities.notificationUtils;

import android.content.Context;

import com.example.android.baiyuweather.R;
import com.example.android.baiyuweather.utilities.weatherUtils.WeatherDataHolder;

import java.util.Random;

/**
 * Created by Baiyubest on 10/4/2017.
 */


public class ReminderTasks {
    public static final String ACTION_REMIND_USER_UMBRELLA = "remind-user-umbrella";

    public static void executeTask(Context context, String action, WeatherDataHolder data){
        if(ACTION_REMIND_USER_UMBRELLA.equals(action)){
            Random rand = new Random();
            int n = rand.nextInt(3);
            if(data.PrecipIntensity < 0.5){
                switch (n){
                    case 0:  NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_light1 );
                        break;
                    case 1:  NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_light2 );
                        break;
                    case 2:  NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_light3 );
                        break;
                    default:
                        NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_light2 );
                }

            }
            else if(data.PrecipIntensity > 0.5 && data.PrecipIntensity < 4){

                if(n == 0){
                    NotificationUtils.remindUser(context,R.string.notification_title,R.string.notification_content1);
                }
                else if(n == 1){
                    NotificationUtils.remindUser(context,R.string.notification_title,R.string.notification_content2);
                }
                else{
                    NotificationUtils.remindUser(context,R.string.notification_title,R.string.notification_content3);
                }


            }
            else{
                switch (n){
                    case 0:  NotificationUtils.remindUser(context, R.string.notification_title, R.string.notification_heavy1);
                        break;
                    case 1:  NotificationUtils.remindUser(context, R.string.notification_title, R.string.notification_heavy2);
                        break;
                    case 2:  NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_heavy3 );
                        break;
                    default:
                        NotificationUtils.remindUser(context,R.string.notification_title, R.string.notification_heavy1 );
                }

            }
        }
    }
}
