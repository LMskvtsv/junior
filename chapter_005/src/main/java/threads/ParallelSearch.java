package threads;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.nio.file.FileVisitResult.CONTINUE;

public class ParallelSearch extends SimpleFileVisitor<Path> {

    private final String root;
    private final String text;
    private final LinkedBlockingQueue<String> exts;

    private final LinkedBlockingQueue<String> files = new LinkedBlockingQueue<>();

    private final LinkedBlockingQueue<String> paths = new LinkedBlockingQueue<>();


    public ParallelSearch(String root, String text, LinkedBlockingQueue<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
        String[] split = filePath.toString().split("\\.");
        String ext = split[split.length - 1];
        for (String s : exts) {
            if (ext.equalsIgnoreCase(s)) {
                try {
                    paths.offer(filePath.toString(), 1_000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return CONTINUE;
    }

    public void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                try {
                    Files.walkFileTree(Paths.get(root), ParallelSearch.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread read = new Thread() {
            @Override
            public void run() {
                try {
                    String path;
                    while ((path = paths.poll(1_000, TimeUnit.MILLISECONDS)) != null) {
                        String content = null;
                        try {
                            content = new String(Files.readAllBytes(Paths.get(path)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (content.toLowerCase().contains(text.toLowerCase())) {
                            try {
                                files.offer(path, 1_000, TimeUnit.MILLISECONDS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        search.start();
        read.start();
        try {
            search.join();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

    synchronized LinkedBlockingQueue<String> result() {
        return this.files;
    }
}

