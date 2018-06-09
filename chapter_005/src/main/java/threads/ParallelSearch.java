package threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.nio.file.FileVisitResult.CONTINUE;

@ThreadSafe
public class ParallelSearch extends SimpleFileVisitor<Path> {

    private final String root;
    private final String text;
    private final List<String> exts;
    final Object lock = new Object();


    volatile boolean finish = false;

    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();


    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    @Override
    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
        String[] split = filePath.toString().split("\\.");
        String ext = split[split.length - 1];
        for (String s : exts) {
            if (ext.equalsIgnoreCase(s)) {
                paths.add(filePath.toString());
            }
        }
        return CONTINUE;
    }

    public void init() {
        Thread search = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        Files.walkFileTree(Paths.get(root), ParallelSearch.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finish = true;
                    lock.notifyAll();
                }
            }
        };
        Thread read = new Thread() {
            @Override
            public void run() {

                synchronized (lock) {
                    if (!finish) {

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }

                for (String s : paths) {
                    String content = null;
                    try {
                        content = new String(Files.readAllBytes(Paths.get(s)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (content.toLowerCase().contains(text.toLowerCase())) {
                        files.offer(s);
                    }
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

    synchronized Queue<String> result() {
        return this.files;
    }

}

