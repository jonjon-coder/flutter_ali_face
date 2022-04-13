package com.example.flutter_ali_face;

import android.content.Context;

import androidx.annotation.NonNull;

import com.aliyun.aliyunface.api.ZIMFacade;
import com.aliyun.aliyunface.api.ZIMFacadeBuilder;

import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * FlutterAliFacePlugin
 */
public class FlutterAliFacePlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Context context;
    private ZIMFacade zimFacade;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_ali_face");
        channel.setMethodCallHandler(this);

        context = flutterPluginBinding.getApplicationContext();

        ZIMFacade.install(context);

        zimFacade = ZIMFacadeBuilder.create(context);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "verify":
                zimFacade.verify(call.argument("certifyId"), true, response -> {
                    result.success(
                            new HashMap<String, Object>() {
                                {
                                    put("code", response.code);
                                    put("msg", response.msg);
                                    put("reason", response.reason);
                                    put("deviceToken", response.deviceToken);
                                    put("videoFilePath", response.videoFilePath);
                                }
                            }
                    );

                    return true;
                });
                break;
            case "getSession":
                result.success(zimFacade.getSession());
                break;
            case "getMetaInfo":
                result.success(ZIMFacade.getMetaInfos(context));
                break;
            default:
                result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        zimFacade.destroy();
        channel.setMethodCallHandler(null);
    }
}
