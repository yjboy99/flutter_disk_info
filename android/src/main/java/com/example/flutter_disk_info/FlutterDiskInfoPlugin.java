package com.example.flutter_disk_info;

import android.os.Environment;
import android.os.StatFs;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterDiskInfoPlugin */
public class FlutterDiskInfoPlugin implements FlutterPlugin, MethodCallHandler {
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_disk_info");
    channel.setMethodCallHandler(new FlutterDiskInfoPlugin());
  }
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_disk_info");
    channel.setMethodCallHandler(new FlutterDiskInfoPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getDiskInfo")) {
      Map<String, Long> map = new HashMap<String, Long>();
      long total = getRomTotalSize();//获得机身内容总大小
      long available = getRomAvailableSize();//获得机身可用内存
      map.put("total", total);
      map.put("available", available);
      result.success(map);
    } else {
      result.notImplemented();
    }
  }

  /**
   * 获得SD卡总大小
   *
   * @return
   */
  private long getSDTotalSize() {
    File path = Environment.getExternalStorageDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSize();
    long totalBlocks = stat.getBlockCount();
    return blockSize * totalBlocks;
  }

  /**
   * 获得sd卡剩余容量，即可用大小
   *
   * @return
   */
  private long getSDAvailableSize() {
    File path = Environment.getExternalStorageDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSize();
    long availableBlocks = stat.getAvailableBlocks();
    return blockSize * availableBlocks;
  }

  /**
   * 获得机身内容总大小
   *
   * @return
   */
  private long getRomTotalSize() {
    File path = Environment.getDataDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSize();
    long totalBlocks = stat.getBlockCount();
    return blockSize * totalBlocks;
  }

  /**
   * 获得机身可用内存
   *
   * @return
   */
  private long getRomAvailableSize() {
    File path = Environment.getDataDirectory();
    StatFs stat = new StatFs(path.getPath());
    long blockSize = stat.getBlockSize();
    long availableBlocks = stat.getAvailableBlocks();
    return blockSize * availableBlocks;
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
  }
}
