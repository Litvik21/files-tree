package org.example.service;

import java.io.File;
import java.util.List;

public interface TreeService {
    File setMainDirectory(File rootPath);

    int setDepthOfSearching(int depth);

    String setMask(String mask);

    List<File> searchNeededDepth(File rootPath, int depth);

    List<File> searchNeededFiles(List<File> files, String mask);
}
