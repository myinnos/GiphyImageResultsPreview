package in.myinnos.gifimages.model;

public class Gif {

    private String previewImageUrl;
    private String previewMp4Url;
    private String gifUrl;

    public Gif(String previewImageUrl, String previewMp4Url, String gifUrl) {
        this.previewImageUrl = previewImageUrl;
        this.previewMp4Url = previewMp4Url;
        this.gifUrl = gifUrl;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getPreviewMp4Url() {
        return previewMp4Url;
    }
}
