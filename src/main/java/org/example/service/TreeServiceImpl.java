package org.example.service;

import java.io.File;
import java.util.*;

public class TreeServiceImpl implements TreeService {
    @Override
    public File setMainDirectory(File rootPath) {
        if (!rootPath.isDirectory()) {
            throw new RuntimeException("This is not directory");
        }
        return rootPath;
    }

    @Override
    public int setDepthOfSearching(int depth) {
        if (depth < 1) {
            throw new RuntimeException("Depth must be greater than 0");
        }
        return depth;
    }

    @Override
    public String setMask(String mask) {
        if (mask == null || mask.isEmpty()) {
            throw new RuntimeException("Mask must be not null");
        }
        return mask;
    }

    @Override
    public List<File> searchNeededDepth(File rootPath, int depth) {
        Queue<File> fileTree = new LinkedList<>();
        Collections.addAll(fileTree, rootPath.listFiles());
        for (int i = 0; i < depth; i++) {
            int length = fileTree.size();
            for (int j = 0; j < length; j++) {
                File currentFile = fileTree.remove();
                if (currentFile.isDirectory()) {
                    Collections.addAll(fileTree, currentFile.listFiles());
                }
            }
        }
        if (fileTree.isEmpty()) {
            throw new RuntimeException("Nothing found at this depth: " + depth);
        }
        return new ArrayList<>(fileTree);
    }

    @Override
    public List<File> searchNeededFiles(List<File> files, String mask) {
        List<File> filesThatContainsMask = new ArrayList<>();
        for (File file : files) {
            if (file.getName().contains(mask)) {
                filesThatContainsMask.add(file);
            }
        }
        if (filesThatContainsMask.isEmpty()) {
            throw new RuntimeException("No such files found! Mask: " + mask);
        }
        return filesThatContainsMask;
    }
}
