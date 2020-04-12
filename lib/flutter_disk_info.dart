import 'dart:async';

import 'package:flutter/services.dart';

class FlutterDiskInfo {

  static const MethodChannel _channel = const MethodChannel('flutter_disk_info');

  static Future<DiskInfoModel> get getDiskInfo async {
    final Map<dynamic, dynamic> map = await _channel.invokeMethod('getDiskInfo');
    print(map.toString());
    return DiskInfoModel(map['total'], map['available']);
  }
}

class DiskInfoModel{
  int total;
  int available;

  DiskInfoModel(this.total, this.available);
}