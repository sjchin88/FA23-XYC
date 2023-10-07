import java.util.Objects;

public class ImageMetaData {
    private String albumId;
    private String imageSize;


    public ImageMetaData(String albumId, String imageSize) {
        this.albumId = albumId;
        this.imageSize = imageSize;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImageMetaData)) {
            return false;
        }
        ImageMetaData that = (ImageMetaData) o;
        return Objects.equals(albumId, that.albumId) && Objects.equals(imageSize,
            that.imageSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumId, imageSize);
    }

    @Override
    public String toString() {
        return "ImageMetaData{" +
            "albumId='" + albumId + '\'' +
            ", imageSize='" + imageSize + '\'' +
            '}';
    }
}
