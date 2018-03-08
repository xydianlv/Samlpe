package com.example.wyyu.gitsamlpe.test.file;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wyyu on 2018/3/8.
 **/

public class FileSorter {

    private enum LocalFileSorter {
        NAME("", new Comparator<LocalFileBean>() {
            @Override
            public int compare(LocalFileBean o1, LocalFileBean o2) {
                return o1.getFileName().compareTo(o2.getFileName()) > 0 ? 1 : -1;
            }
        }),
        TIME("", new Comparator<LocalFileBean>() {
            @Override
            public int compare(LocalFileBean o1, LocalFileBean o2) {
                return o1.getChangeTime() > o2.getChangeTime() ? 1 : -1;
            }
        }),
        SIZE("", new Comparator<LocalFileBean>() {
            @Override
            public int compare(LocalFileBean o1, LocalFileBean o2) {
                return o1.getFileSize() > o2.getFileSize() ? 1 : -1;
            }
        });

        private Comparator<LocalFileBean> comparator;
        private String typeName;

        LocalFileSorter(String typeName, Comparator<LocalFileBean> comparator) {
            this.comparator = comparator;
            this.typeName = typeName;
        }

        Comparator getComparator() {
            return comparator;
        }

        String getTypeName() {
            return typeName;
        }

    }

    static void sortLocalFileList(List<LocalFileBean> localFileBeanList, final LocalFileSorter fileSorter, final boolean isDesc) {

        Collections.sort(localFileBeanList, new Comparator<LocalFileBean>() {
            @Override
            public int compare(LocalFileBean o1, LocalFileBean o2) {
                if (o1.getFile().isDirectory() && o2.getFile().isFile()) {
                    return -1;
                } else if (o1.getFile().isFile() && o2.getFile().isDirectory()) {
                    return 1;
                } else {
                    return isDesc ? fileSorter.comparator.compare(o1, o2) : -fileSorter.comparator.compare(o1, o2);
                }
            }
        });
    }
}
