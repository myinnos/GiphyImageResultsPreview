package in.myinnos.gifimageresults;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.myinnos.gifimages.GiphyTask;
import in.myinnos.gifimages.builder.GiphyQueryBuilder;
import in.myinnos.gifimages.helper.Helper;
import in.myinnos.gifimages.model.Gif;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GifAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new GifAdapter(this, new ArrayList<Gif>());
        recyclerView.setAdapter(adapter);


        new GiphyTask(Helper.getGiphyQueryUrl("pawan kalyan",
                100, GiphyQueryBuilder.EndPoint.SEARCH, ""), new GiphyTask.Callback() {
            @Override
            public void onResponse(List<Gif> gifs) {
                adapter.setGifs(gifs);
            }
        }).execute();

        recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                GifAdapter.GifViewHolder gifHolder = (GifAdapter.GifViewHolder) holder;
                gifHolder.stopPlayback();
            }
        });
    }

}
