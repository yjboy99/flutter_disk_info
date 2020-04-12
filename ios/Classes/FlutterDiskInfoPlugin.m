#import "FlutterDiskInfoPlugin.h"


@implementation FlutterDiskInfoPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"flutter_disk_info"
            binaryMessenger:[registrar messenger]];
  FlutterDiskInfoPlugin* instance = [[FlutterDiskInfoPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"getDiskInfo" isEqualToString:call.method]) {
     [self getDiskInfo:call result:result];
  } else {
    result(FlutterMethodNotImplemented);
  }
}

- (void)getDiskInfo:(FlutterMethodCall *)call result:(FlutterResult)result {

    NSDictionary *fattributes = [[NSFileManager defaultManager] attributesOfFileSystemForPath:NSHomeDirectory() error:nil];

    NSNumber *freeber = [fattributes objectForKey:NSFileSystemFreeSize];
    NSNumber *maxber = [fattributes objectForKey:NSFileSystemSize];

    long long freespace = [freeber longLongValue];
    long long maxspace = [maxber longLongValue];

    NSString * sizeStr = [NSString stringWithFormat:@"剩余空间%0.1fG /共%0.1fG",(double)freespace/1024/1024/1024,(double)maxspace/1024/1024/1024];
    NSLog(@"空间信息:%@",sizeStr);
   //    return sizeStr;
    result(@{
        @"total":maxber,
        @"available":freeber,
    });

}

@end
