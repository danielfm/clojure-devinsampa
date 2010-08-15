package br.com.devinsampa.concorrencia.problema3;

import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class CoverArtDownloaderWrong {
    public void run() {
        for (final PlaylistEntry track : Playlist.DATA) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        fetchCoverArt(track);
                    } catch (Exception e) {
                        // What to do in case of errors? Retry? Skip? Pray?
                        // You are on your own, my friend :-)
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Downloads the cover art for the track and updates the given track.
     */
    private void fetchCoverArt(PlaylistEntry track) throws Exception {
        String fileName = track.getAlbum().replaceAll(" ", "") + ".jpg";
        String url = "http://destaquenet.com/media/devinsampa/" + fileName;

        System.out.println("Downloading cover art for " + track);

        URLConnection conn = new URL(url).openConnection();
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.connect();

        track.setCoverArt(writeToFile(conn.getInputStream(), fileName));
        System.out.println("--> " + track + " [Success]");
    }

    private File writeToFile(InputStream stream, String fileName) throws Exception {
        File dir = new File("build/covers/");
        dir.mkdirs();

        File file = new File("build/covers/" + fileName);
        FileOutputStream output = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int size = 0;

        while ((size = stream.read(buffer)) > 0) {
            output.write(buffer, 0, size);
        }

        output.close();
        return file;
    }

    public static void main(String[] args) {
        new CoverArtDownloaderWrong().run();
    }
}

