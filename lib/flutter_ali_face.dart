import 'dart:async';

import 'package:flutter/services.dart';

class FlutterAliFace {
  static const MethodChannel _channel = MethodChannel('flutter_ali_face');

  static Future<String> get getSession async => await _channel.invokeMethod('getSession');

  static Future<String> get getMetaInfo async => await _channel.invokeMethod('getMetaInfo');

  static Future<Map<String, dynamic>> verify(String certifyId) async {
    return (await _channel.invokeMapMethod<String, dynamic>('verify', {'certifyId': certifyId}))!;
  }
}
