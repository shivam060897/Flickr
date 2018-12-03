package pandit.flickr;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadComplete {
    private static final String TAG = "GetFlickrJsonData";
    private final OnDataAvailable mCallBack;
    private List<Photo> mPhotoList = null;
    private String mBaseURL;
    private String mLanguage;
    private boolean mMatchAll;
    private boolean runningOnSameThread = false;

    public GetFlickrJsonData(OnDataAvailable callBack, String baseURL, String language, boolean matchAll) {
        mBaseURL = baseURL;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallBack = callBack;
    }

    void executeOnSameThread(String searchCriteria) {
        runningOnSameThread = true;
        String destinationUrl = createUrl(searchCriteria, mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUrl);
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        if (mCallBack != null) {
            mCallBack.onDataAvailable(mPhotoList, DownloadStatus.OK);
        }

    }

    @Override
    protected List<Photo> doInBackground(String... parans) {
        String destinationUri = createUrl(parans[0], mLanguage, mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return mPhotoList;
    }

    private String createUrl(String searchCriteria, String lang, boolean matchAll) {

        return Uri.parse(mBaseURL).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();

    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {

        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m.", "_b.");

                    Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photoObject);

                }
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing JSON data" + jsone.getMessage());
            }
        }

        if (mCallBack != null && runningOnSameThread) {
            //new inform the caller that processing is done-possibly returning null if there
            //was an error
            mCallBack.onDataAvailable(mPhotoList, status);
        }

    }

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }
}
