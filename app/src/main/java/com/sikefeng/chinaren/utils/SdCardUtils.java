package com.sikefeng.chinaren.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.*;
import java.util.ArrayList;

import static com.sikefeng.chinaren.utils.Constants.VALUE_4;


public class SdCardUtils {

    /**
     * 文件夹深度
     */
    private static final int DEPTH = 5;

    /**
     * is sd card available.
     *
     * @return true if available
     */
    public boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Get {@link StatFs}.
     *
     * @param path 路径
     * @return StatFs
     */
    public static StatFs getStatFs(String path) {
        return new StatFs(path);
    }

    /**
     * Get phone data path.
     *
     * @return String
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getPath();

    }

    /**
     * Get SD card path.
     *
     * @return String
     */
    public static String getNormalSDCardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * Get SD card path by CMD.
     *
     * @return String
     */
    public static String getSDCardPath() {
        String cmd = "cat /proc/mounts";
        String sdcard = null;
        Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行时对象
        BufferedReader bufferedReader = null;
        try {
            Process p = run.exec(cmd);// 启动另一个进程来执行命令
            bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(p.getInputStream())));
            String lineStr;
            while ((lineStr = bufferedReader.readLine()) != null) {
                if (lineStr.contains("sdcard")
                        && lineStr.contains(".android_secure")) {
                    String[] strArray = lineStr.split(" ");
                    if (strArray.length >= DEPTH) {
                        sdcard = strArray[1].replace("/.android_secure", "");
                        return sdcard;
                    }
                }
//                if (p.waitFor() != 0 && p.exitValue() == 1) {
//                    // p.exitValue()==0表示正常结束，1：非正常结束
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sdcard = Environment.getExternalStorageDirectory().getPath();
        return sdcard;
    }

    /**
     * Get SD card path list.
     *
     * @return 集合
     */
    public static ArrayList<String> getSDCardPathEx() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure")) {
                    continue;
                }
                if (line.contains("asec")) {
                    continue;
                }

                if (line.contains("fat")) {
                    String[] columns = line.split(" ");
                    if (columns.length > 1) {
                        list.add("*" + columns[1]);
                    }
                } else if (line.contains("fuse")) {
                    String[] columns = line.split(" ");
                    if (columns.length > 1) {
                        list.add(columns[1]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Get available size of SD card.
     *
     * @param path 路径
     * @return long
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static long getAvailableSize(String path) {
        try {
            File base = new File(path);
            StatFs stat = new StatFs(base.getPath());
            return stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get SD card info detail.
     *
     * @return SDCardInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo() {
        SDCardInfo sd = new SDCardInfo();
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            sd.isExist = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());

                sd.totalBlocks = sf.getBlockCountLong();
                sd.blockByteSize = sf.getBlockSizeLong();

                sd.availableBlocks = sf.getAvailableBlocksLong();
                sd.availableBytes = sf.getAvailableBytes();

                sd.freeBlocks = sf.getFreeBlocksLong();
                sd.freeBytes = sf.getFreeBytes();

                sd.totalBytes = sf.getTotalBytes();
            }
        }
        return sd;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数(byte)
     *
     * @param filePath 文件路径
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - VALUE_4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return String
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }


    /**
     * see more {@link StatFs}
     */
    public static class SDCardInfo {
        /**
         * 是否存在
         */
        private boolean isExist;
        /**
         * 总块数量
         */
        private long totalBlocks;
        /**
         * 空闲块
         */
        private long freeBlocks;
        /**
         * 激活的块
         */
        private long availableBlocks;
        /**
         * 块大小
         */
        private long blockByteSize;
        /**
         * 总数
         */
        private long totalBytes;
        /**
         * 空闲
         */
        private long freeBytes;
        /**
         * 激活的
         */
        private long availableBytes;

        public boolean isExist() {
            return isExist;
        }

        public void setExist(boolean exist) {
            isExist = exist;
        }

        public long getTotalBlocks() {
            return totalBlocks;
        }

        public void setTotalBlocks(long totalBlocks) {
            this.totalBlocks = totalBlocks;
        }

        public long getFreeBlocks() {
            return freeBlocks;
        }

        public void setFreeBlocks(long freeBlocks) {
            this.freeBlocks = freeBlocks;
        }

        public long getAvailableBlocks() {
            return availableBlocks;
        }

        public void setAvailableBlocks(long availableBlocks) {
            this.availableBlocks = availableBlocks;
        }

        public long getBlockByteSize() {
            return blockByteSize;
        }

        public void setBlockByteSize(long blockByteSize) {
            this.blockByteSize = blockByteSize;
        }

        public long getTotalBytes() {
            return totalBytes;
        }

        public void setTotalBytes(long totalBytes) {
            this.totalBytes = totalBytes;
        }

        public long getFreeBytes() {
            return freeBytes;
        }

        public void setFreeBytes(long freeBytes) {
            this.freeBytes = freeBytes;
        }

        public long getAvailableBytes() {
            return availableBytes;
        }

        public void setAvailableBytes(long availableBytes) {
            this.availableBytes = availableBytes;
        }

        @Override
        public String toString() {
            return "SDCardInfo{"
                    + "isExist=" + isExist
                    + ", totalBlocks=" + totalBlocks
                    + ", freeBlocks=" + freeBlocks
                    + ", availableBlocks=" + availableBlocks
                    + ", blockByteSize=" + blockByteSize
                    + ", totalBytes=" + totalBytes
                    + ", freeBytes=" + freeBytes
                    + ", availableBytes=" + availableBytes
                    + '}';
        }
    }
}
