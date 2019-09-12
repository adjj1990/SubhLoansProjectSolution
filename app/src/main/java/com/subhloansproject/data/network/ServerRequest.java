package talks.foazo.com.subhloansproject.data.network;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import talks.foazo.com.subhloansproject.ui.onDemand.GenericTaskListener;
import talks.foazo.com.subhloansproject.utils.Constants;

public class ServerRequest {

    public void requestPageData(final String url, final HashMap<String, String> data, final Context context, Boolean showDialog, final String token) {

        if(Connection.checkConnection(context))
        {
            GenericNewAsynTask genericNewAsynTask = new GenericNewAsynTask("Fetching Data .. ", context, url, Constants.GET, data, showDialog, "", "");

            genericNewAsynTask.setOnTaskListener(new GenericNewAsynTask.TaskListener() {

                GenericTaskListener listener = (GenericTaskListener) context;

                public void onSuccess(String success) {
                    listener.updateResult(success, token);
                    Log.e("SERVER", success);
                }
                public void onFailure(String error) {

                }
            });
        }
        else
        {

        }
    }

}
