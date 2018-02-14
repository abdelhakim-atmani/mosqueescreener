package screener.mosque.org.mosquescreener.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import screener.mosque.org.mosquescreener.model.Prayer;
import screener.mosque.org.mosquescreener.model.ScreenType;
import screener.mosque.org.mosquescreener.schedule.Chain;
import screener.mosque.org.mosquescreener.schedule.ScreenChain;
import screener.mosque.org.mosquescreener.utils.HttpHandler;

import static android.content.ContentValues.TAG;
import static screener.mosque.org.mosquescreener.model.FlowDefinitionField.LIMIT0;
import static screener.mosque.org.mosquescreener.model.FlowDefinitionField.LIMIT1;
import static screener.mosque.org.mosquescreener.model.FlowDefinitionField.TYPE;
import static screener.mosque.org.mosquescreener.utils.DateHelper.SCHEDULE_PREPARE_PRAYER_TIME;
import static screener.mosque.org.mosquescreener.utils.DateHelper.minutesToMilliseconds;

/**
 * Created by abdelhakim on 31/01/2018.
 */

public class AsyncFlowGenerator extends AsyncTask<Void, Void, Void> {

    private static final String PRAYER_FLOW_URL = "https://efmxeb1eoi.execute-api.eu-west-1.amazonaws.com/prod/advert/%s/%s";

    private final String dayOfWeek;
    private final Prayer prayer;

    private JSONArray flowDefinitionList;

    public AsyncFlowGenerator (Prayer prayer, String dayOfWeek) {
        this.prayer = prayer;
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {
        getFlowDetails();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        System.out.println(flowDefinitionList);
        ScreenChain.ScreenChainBuilder flowBuilder = new ScreenChain.ScreenChainBuilder();

        int previousEndPoint = 0;
        for (int i = 0; i < flowDefinitionList.length() ; i++) {
            try {
                JSONObject flowDefinition = flowDefinitionList.getJSONObject(i);
                int startPoint = -SCHEDULE_PREPARE_PRAYER_TIME + flowDefinition.getInt(LIMIT0.getLCName());
                int endPoint = -SCHEDULE_PREPARE_PRAYER_TIME + flowDefinition.getInt(LIMIT1.getLCName());
                if(startPoint > previousEndPoint) {
                    flowBuilder.nextScreen(prayer, ScreenType.RESET, minutesToMilliseconds(startPoint - previousEndPoint));
                }
                flowBuilder.nextScreen(prayer, ScreenType.getScreenTypeByName(flowDefinition.getString(TYPE.getLCName())), minutesToMilliseconds(endPoint - startPoint));
                previousEndPoint = endPoint;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        flowBuilder.nextScreen(prayer, ScreenType.RESET, (-SCHEDULE_PREPARE_PRAYER_TIME * 2) - previousEndPoint);
        System.out.println(flowBuilder.toString());
        Chain flow = flowBuilder.build();
        flow.proceed();
        System.out.println(new Date() + " > " + flow.toString());

    }

    private void getFlowDetails() {
        HttpHandler sh = new HttpHandler();
        String url = String.format(PRAYER_FLOW_URL, dayOfWeek, prayer.name().toLowerCase());
        System.out.println(url);
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url);

        Log.e(TAG, "Response from url: " + jsonStr);

        if (jsonStr != null) {
            try {
                flowDefinitionList = new JSONArray(jsonStr);

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }
}