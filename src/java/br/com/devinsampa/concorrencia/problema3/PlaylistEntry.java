package br.com.devinsampa.concorrencia.problema3;

import java.io.File;

public class PlaylistEntry {

    private String artist;
    private String album;
    private String title;
    private File coverArt;

    public PlaylistEntry(String artist, String album, String title) {
        this(artist, album, title, null);
    }

    public PlaylistEntry(String artist, String album, String title, File coverArt) {
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.coverArt = coverArt;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public File getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(File coverArt) {
        this.coverArt = coverArt;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(artist).append(" - ")
            .append(title).append(" (").append(album).append(")");

        if (coverArt != null) {
            builder.append(" - cover art: ").append(coverArt);
        }

        return builder.toString();
    }
}

