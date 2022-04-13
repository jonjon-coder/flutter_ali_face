import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_ali_face/flutter_ali_face.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _session = 'Unknown Session';
  String _metaInfo = 'Unknown MetaInfo';

  @override
  void initState() {
    super.initState();

    initSession();
    initMetaInfo();
  }

  Future<void> initSession() async {
    _session = await FlutterAliFace.getSession;

    if (mounted) setState(() {});
  }

  Future<void> initMetaInfo() async {
    _metaInfo = await FlutterAliFace.getMetaInfo;

    if (mounted) setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Text(_metaInfo),
            Text(_session),
          ],
        ),
      ),
    );
  }
}
