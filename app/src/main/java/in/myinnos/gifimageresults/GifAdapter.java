package in.myinnos.gifimageresults;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.myinnos.gifimages.gif.GifView;
import in.myinnos.gifimages.model.Gif;
import in.myinnos.gifimages.task.ShareGif;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private final Activity context;
    private final List<Gif> gifs;

    public GifAdapter(Activity context, List<Gif> gifs) {
        this.context = context;
        this.gifs = gifs;
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }

    @Override
    public GifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_gif, parent, false);
        return new GifViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(GifViewHolder holder, int position) {
        holder.bind(gifs.get(position));
    }

    public void setGifs(List<Gif> gifs) {
        this.gifs.clear();
        this.gifs.addAll(gifs);

        notifyDataSetChanged();
    }

    public static class GifViewHolder extends RecyclerView.ViewHolder {

        private Activity context;

        private View clickZone;
        private ImageView previewImage;
        private ImageView shareButton;
        private GifView gifView;

        private GifViewHolder(View itemView, Activity context) {
            super(itemView);

            this.context = context;

            clickZone = itemView.findViewById(R.id.touch_effect);
            previewImage = (ImageView) itemView.findViewById(R.id.preview_image);
            shareButton = (ImageView) itemView.findViewById(R.id.share);
            gifView = (GifView) itemView.findViewById(R.id.gif_view);
        }

        private void bind(final Gif gif) {
            Glide.with(itemView.getContext()).load(gif.getPreviewImageUrl()).centerCrop().into(previewImage);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // With android Marshmallow, the user grants permissions at runtime.
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                0);
                    } else {
                        new ShareGif(shareButton.getContext(), gif.getGifUrl()).execute();
                    }
                }
            });

            clickZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gifView.getVisibility() == View.VISIBLE) {
                        gifView.release();
                        gifView.setVisibility(View.GONE);
                    } else {
                        gifView.setVisibility(View.VISIBLE);
                        gifView.start(gif.getPreviewMp4Url());
                    }
                }
            });
        }

        public void stopPlayback() {
            if (gifView.getVisibility() == View.VISIBLE) {
                clickZone.performClick();
            }
        }
    }
}