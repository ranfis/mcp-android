import android.util.Log;

import com.greengrowapps.ggarest.GgaRest;
import com.greengrowapps.ggarest.Response;
import com.greengrowapps.ggarest.listeners.OnListResponseListener;
import com.greengrowapps.ggarest.listeners.OnResponseListener;
import com.mcp.mycareerplan.MainActivity;

import java.util.List;

class Commit {
    public Commit() {

    }
}

public class Main {

    public static void main(String[] args){
        GgaRest.init(new MainActivity());
        GgaRest.useBasicAuth("ranfis", "gh19132008");

        GgaRest.ws()
                .get("https://api.github.com/repos/greengrowapps/ggarest/commits")
                .onSuccess(Commit.class, new OnListResponseListener<Commit>() {
                    @Override
                    public void onResponse(int code, List<Commit> object, Response fullResponse) {
                        Log.d("-----------------", fullResponse.getBody());
                    }
                })
                .onResponse(401, new OnResponseListener() {
                    @Override
                    public void onResponse(int code, Response fullResponse, Exception exception) {
                        Log.e("-----------","Error");
                    }
                })
                .execute();
    }
}
