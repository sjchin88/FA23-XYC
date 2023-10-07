import java.util.Objects;

public class Album {

    private ImageMetaData imageMetaData;

    private AlbumInfo albumInfo;

    public Album(ImageMetaData imageMetaData, AlbumInfo albumInfo) {
        this.imageMetaData = imageMetaData;
        this.albumInfo = albumInfo;
    }

    public ImageMetaData getImageMetaData() {
        return imageMetaData;
    }

    public void setImageMetaData(ImageMetaData imageMetaData) {
        this.imageMetaData = imageMetaData;
    }

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }

    public void setAlbumInfo(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Album)) {
            return false;
        }
        Album album = (Album) o;
        return Objects.equals(imageMetaData, album.imageMetaData) && Objects.equals(
            albumInfo, album.albumInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageMetaData, albumInfo);
    }

    @Override
    public String toString() {
        return "Album{" +
            "imageMetaData=" + imageMetaData +
            ", albumInfo=" + albumInfo +
            '}';
    }
}
